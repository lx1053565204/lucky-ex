package com.lucky.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lucky.enums.Type;
import com.lucky.sqldao.PojoManage;
import com.lucky.sqldao.TypeChange;

/**
 * 生成建表语句的类
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class CreateTableSql {
	private static TypeChange tych = new TypeChange();

	/**
	 * 根据类的Class信息生成建表语句
	 * @param clzz 目标类的Class
	 * @return
	 */
	public static String getCreateTable(Class<?> clzz) {
		String sql = "CREATE TABLE IF NOT EXISTS " + PojoManage.getTable(clzz)+ " (";
		Field[] fields = clzz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i < fields.length - 1) {
				if (PojoManage.getIdField(clzz).equals(fields[i]))
					sql += PojoManage.getIdString(clzz) + " " + tych.toMysql(fields[i].getType().toString()) + "("+PojoManage.getLength(fields[i])+") "
							+ "NOT NULL "+isAutoInt(clzz)+" PRIMARY KEY,";
				else if (!("double".equals(tych.toMysql(fields[i].getType().toString()))
						|| "datetime".equals(tych.toMysql(fields[i].getType().toString()))
						|| "date".equals(tych.toMysql(fields[i].getType().toString())))) {
					sql += PojoManage.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+PojoManage.getLength(fields[i])+") "
							+ " DEFAULT NULL,";
				} else {
					sql += PojoManage.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "  DEFAULT NULL,";
				}
			} else {
				if (PojoManage.getTableField(fields[i]).equals(PojoManage.getIdString(clzz)))
					sql += PojoManage.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+PojoManage.getLength(fields[i])+") "
							+ "NOT NULL AUTO_INCREMENT PRIMARY KEY";
				else if (!("double".equals(tych.toMysql(fields[i].getType().toString()))
						|| "datetime".equals(tych.toMysql(fields[i].getType().toString()))
						|| "date".equals(tych.toMysql(fields[i].getType().toString())))) {
					sql += PojoManage.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "("+PojoManage.getLength(fields[i])+") "
							+ " DEFAULT NULL";
				} else {
					sql += PojoManage.getTableField(fields[i]) + " " + tych.toMysql(fields[i].getType().toString()) + "  DEFAULT NULL";
				}
			}
		}
		sql += ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		return sql;
	}

	/**
	 * 生成添加外键的sql语句集合
	 * @param clzz
	 * 目标类的Class
	 * @return
	 */
	public static List<String> getForeignKey(Class<?> clzz) {
		List<String> stlist = new ArrayList<String>();
		List<String> keys = (List<String>) PojoManage.getKeyFields(clzz, true);
		if (keys.isEmpty()) {
			return null;
		} else {
			List<Class<?>> cs = (List<Class<?>>) PojoManage.getKeyFields(clzz, false);
			for (int i = 0; i < cs.size(); i++) {
				String sql = "ALTER TABLE " + PojoManage.getTable(clzz) + " ADD CONSTRAINT " + getRandomStr()
						+ " FOREIGN KEY (" + keys.get(i) + ") REFERENCES " + PojoManage.getTable(cs.get(i)) + "("
						+ PojoManage.getIdString(cs.get(i)) + ")"+isCascadeDel(cs.get(i))+isCascadeUpd(cs.get(i));
				stlist.add(sql);
			}
			return stlist;
		}
	}

	/**
	 * 生成外键名
	 * @return
	 */
	private static String getRandomStr() {
		String[] st = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z" };
		int[] i = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, };
		int a = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int b = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int c = (int) (Math.random() * ((i.length - 1) - 0 + 1));
		int x = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int y = (int) (Math.random() * ((st.length - 1) - 0 + 1));
		int z = (int) (Math.random() * ((i.length - 1) - 0 + 1));
		String m = st[a] + st[b] + st[x] + st[y] + i[z] + i[c];
		return m;
	}
	
	/**
	 * 设置主键类型
	 * @param clzz
	 * @return
	 */
	private static String isAutoInt(Class<?> clzz) {
		Type idType = PojoManage.getIdType(clzz);
		if(idType==Type.AUTO_INT)
			return "AUTO_INCREMENT";
		return "";
	}
	
	/**
	 * 设置级联删除
	 * @param clzz
	 * @return
	 */
	private static String isCascadeDel(Class<?> clzz) {
		if(PojoManage.cascadeDelete(clzz))
			return " ON DELETE CASCADE";
		return "";
	}
	
	/**
	 * 设置级联更新
	 * @param clzz
	 * @return
	 */
	private static String isCascadeUpd(Class<?> clzz) {
		if(PojoManage.cascadeUpdate(clzz))
			return " ON UPDATE CASCADE";
		return "";
	}
}
