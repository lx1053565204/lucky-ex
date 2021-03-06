package com.lucky.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lucky.sqldao.PojoManage;
import com.lucky.sqldao.SqlOperation;
import com.lucky.utils.LuckyManager;

public class DeleteKeySql {
	private SqlOperation sqlop = LuckyManager.getSqlOperation();
	private String databasename;
	private List<String> delkeysql = new ArrayList<String>();
	private List<String> classlist=LuckyManager.getPropCfg().getClaurl();

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public List<String> getDelkeysql() {
		return delkeysql;
	}

	public void setDelkeysql(List<String> delkeysql) {
		this.delkeysql = delkeysql;
	}

	/**
	 * 建表时删除
	 */
	public void deleteKey() {
		for (String str : classlist) {
			try {
				Class<?> clazz = Class.forName(str);
				String table = PojoManage.getTable(clazz);
				String sql = "SHOW CREATE TABLE " + table;
				ResultSet rs = sqlop.getResultSet(sql);
				List<String> keyList=new ArrayList<>();
				if (rs != null) {
					while (rs.next()) {
						String info = rs.getString(2);
						while (info.contains("CONSTRAINT")) {
							int index = info.indexOf("CONSTRAINT");
							int end = info.indexOf("FOREIGN");
							keyList.add(info.substring(index + 12, end - 2));
							info = info.replaceFirst("CONSTRAINT", "");
							info = info.replaceFirst("FOREIGN", "");
						}
					}
				}
				for (String wkey : keyList) {
					String sqlStr = "ALTER TABLE " + table + " DROP FOREIGN KEY " + wkey;
					sqlop.setSql(sqlStr);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到数据库的名字和删除数据库所有表外键的sql语句集合并封装到属性中
	 */
	public DeleteKeySql() {

	}

	/**
	 * 删除所有外键
	 */
	public void deleteKey1() {
		for (String sql : this.delkeysql) {
			sqlop.setSql(sql);
		}
	}

}
