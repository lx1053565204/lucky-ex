package com.lucky.jacklamb.sqldao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.exception.AutoPackageException;
import com.lucky.jacklamb.utils.LuckyManager;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * ������Զ���װ��
 * 
 * @author fk-7075
 *
 */
public class AutoPackage {
	private ResultSet rs = null;
	private SqlOperation sqloperation = LuckyManager.getSqlOperation();

	/**
	 * �Զ���������е����ݷ�װ����
	 * 
	 * @param c ��װ���Class����
	 * @param sql Ԥ�����sql���
	 * @param obj �滻ռλ��������
	 * @return ����һ�����ͼ���
	 */
	public List<?> getTable(Class<?> c, String sql, Object... obj) {
		List<Object> list = new ArrayList<Object>();
		rs = sqloperation.getResultSet(sql, obj);
		Object object = null;
		if(c.getClassLoader()!=null) {
			Field[] fields = c.getDeclaredFields();
			try {
				while (rs.next()) {
					object = c.newInstance();
					for (Field f : fields) {
						if (f.getType().getClassLoader()!=null) {
							Class<?> cl=f.getType();
							Field[] fils=cl.getDeclaredFields();
							Object onfk=cl.newInstance();
							for (Field ff : fils) {
								String field_tab=PojoManage.getTableField(ff);
								if (isExistColumn(rs, field_tab)) {
									ff.setAccessible(true);
									ff.set(onfk, rs.getObject(field_tab));
								}
							}
							f.setAccessible(true);
							f.set(object, onfk);
						} else {
							String field_tab=PojoManage.getTableField(f);
							if (isExistColumn(rs, field_tab)) {
								f.setAccessible(true);
								f.set(object, rs.getObject(field_tab));
							}
						}
					}
					list.add(object);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new AutoPackageException("����ӳ������޷��Զ���װ��ѯ�����������ӳ�����á�����λ�ã�Class:"+c.getName()+"SQl:"+sql);
			}finally {
				sqloperation.close();
			}
		}else {
			try {
				while(rs.next()) {
					list.add(LuckyUtils.typeCast(rs.getObject(1)+"", c.getSimpleName()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public boolean update(String sql,Object...obj) {
		return sqloperation.setSql(sql, obj);
	}

	/**
	 * �жϽ�������Ƿ���ָ������
	 * 
	 * @param rs
	 *            ���������
	 * @param columnName
	 *            ����
	 * @return ���������ָ��������true
	 */
	public boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			if (rs.findColumn(columnName) > 0) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

}