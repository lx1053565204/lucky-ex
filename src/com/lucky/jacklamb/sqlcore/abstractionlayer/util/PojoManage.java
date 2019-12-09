package com.lucky.jacklamb.sqlcore.abstractionlayer.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lucky.jacklamb.annotation.orm.Column;
import com.lucky.jacklamb.annotation.orm.Id;
import com.lucky.jacklamb.annotation.orm.Key;
import com.lucky.jacklamb.annotation.orm.Table;
import com.lucky.jacklamb.enums.PrimaryType;
import com.lucky.jacklamb.exception.NotFindFlieException;
import com.lucky.jacklamb.sqlcore.c3p0.ReadProperties;

/**
 * ʵ�����������
 * @author fk-7075
 *
 */
public class PojoManage {
	
	/**
	 * ��ȡ��ǰ����Դ��Ӧ���ݿ������
	 * @param dbname
	 * @return
	 */
	public static String getDatabaseType(String dbname) {
		String jdbcDriver=ReadProperties.getDataSource(dbname).getDriverClass();
		if(jdbcDriver.contains("mysql"))
			return "MySql";
		if(jdbcDriver.contains("db2"))
			return "DB2";
		if(jdbcDriver.contains("oracle"))
			return "Oracle";
		if(jdbcDriver.contains("postgresql"))
			return "PostgreSql";
		if(jdbcDriver.contains("sqlserver"))
			return "Sql Server";
		if(jdbcDriver.contains("sybase"))
			return "Sybase";
		if(jdbcDriver.contains("access"))
			return "Access";
		return null;
	}
	
	/**
	 * ��ȡ��ǰ����Դ��Ӧ���ݿ������
	 * @param dbname
	 * @return
	 */
	public static String getDatabaseName(String dbname) {
		String url = ReadProperties.getDataSource(dbname).getJdbcUrl();
		String databasename=url.substring((url.lastIndexOf("/")+1),url.length());
		if(databasename.contains("?")) {
			databasename=databasename.substring(0, databasename.indexOf("?"));
		}
		return databasename;
	}
	
	/**
	 * �õ���ʵ�������Զ�Ӧ�����ݿ�ӳ��
	 * @param field
	 * @return
	 */
	public static String getTableField(Field field) {
		if(field.isAnnotationPresent(Column.class)) {
			Column coumn=field.getAnnotation(Column.class);
			if("".equals(coumn.value()))
				return field.getName().toLowerCase();
			return coumn.value().toLowerCase();
		}else if(field.isAnnotationPresent(Id.class)) {
			Id id=field.getAnnotation(Id.class);
			if("".equals(id.value()))
				return field.getName().toLowerCase();
			return id.value().toLowerCase();
		}else if(field.isAnnotationPresent(Key.class)) {
			Key key=field.getAnnotation(Key.class);
			if("".equals(key.value()))
				return field.getName().toLowerCase();
			return key.value().toLowerCase();
		}else {
			return field.getName().toLowerCase();
		}
	}
	
	/**
	 * �õ����ֶ��Ƿ����Ϊnull������
	 * @param field
	 * @return
	 */
	public static boolean allownull(Field field) {
		if(field.isAnnotationPresent(Column.class)) {
			return field.getAnnotation(Column.class).allownull();
		}else if(field.isAnnotationPresent(Key.class)) {
			return field.getAnnotation(Key.class).allownull();
		}else {
			return true;
		}
	}
	
	/**
	 * �õ����Եĳ�������
	 * @param field
	 * @return
	 */
	public static int getLength(Field field) {
		if(field.isAnnotationPresent(Id.class)) {
			return field.getAnnotation(Id.class).length();
		}else if(field.isAnnotationPresent(Key.class)) {
			return field.getAnnotation(Key.class).length();
		}else if(field.isAnnotationPresent(Column.class)) {
			return field.getAnnotation(Column.class).length();
		}else {
			return 35;
		}
	}
	
	/**
	 * �õ���ʵ�����Id����
	 * @param pojoClass
	 * @return
	 */
	public static Field getIdField(Class<?> pojoClass) {
		Field[] pojoFields=pojoClass.getDeclaredFields();
		for(Field field:pojoFields) {
			if(field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		throw new NotFindFlieException("û���ҵ�"+pojoClass.getName()+"��Id���ԣ���������ID�������Ƿ�������@Idע��.");
	}
	
	/**
	 * �õ���ʵ�����ӳ�����
	 * @param pojoClass
	 * @return
	 */
	public static String getTable(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			if("".equals(table.value()))
				return pojoClass.getSimpleName().toLowerCase();
			return table.value().toLowerCase();
		}else {
			return pojoClass.getSimpleName().toLowerCase();
		}
	}
	
	/**
	 * �õ���ʵ���Ӧ���ļ���ɾ����Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static boolean cascadeDelete(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.cascadeDelete();
		}
		return false;
	}
	
	/**
	 * �õ���ʵ���Ӧ���ļ����³���Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static boolean cascadeUpdate(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.cascadeUpdate();
		}
		return false;
	}
	
	/**
	 * �õ���ʵ�����ӳ��������
	 * @param pojoClass
	 * @return
	 */
	public static String getIdString(Class<?> pojoClass) {
		Field idField = getIdField(pojoClass);
		Id id = idField.getAnnotation(Id.class);
		if("".equals(id.value()))
			return idField.getName();
		return id.value();
	}
	

	/**
	 * �õ���ʵ���������ӳ���������������ɵ�Map
	 * @param pojoClass
	 * @return
	 */
	public static Map<Field,Class<?>> getKeyFieldMap(Class<?> pojoClass){
		Map<Field,Class<?>> keys=new HashMap<>();
		Field[] pojoFields=pojoClass.getDeclaredFields();
		for(Field field:pojoFields) {
			if(field.isAnnotationPresent(Key.class)) {
				Key key=field.getAnnotation(Key.class);
				keys.put(field, key.pojo());
			}
		}
		return keys;
	}
	
	/**
	 * �����Ӧ�෴���������
	 * @param clap ������
	 * @param clak �������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Field classToField(Class<?> clap,Class<?> clak) {
		List<Field> clapKeyFields = (List<Field>) getKeyFields(clap, true);
		for(Field field: clapKeyFields) {
			Key key=field.getAnnotation(Key.class);
			if(key.pojo().equals(clak))
				return field;
		}
		return null;
	}
	
	/**
	 * �õ���ʵ���Ӧ���������Ϣ
	 * @param pojoClass
	 * @param iskey true(����������Լ���)/false(���������Ӧ�ĵ�ʵ��Class)
	 * @return
	 */
	public static List<?> getKeyFields(Class<?> pojoClass,boolean iskey){
		Map<Field,Class<?>> keyAdnField=getKeyFieldMap(pojoClass);
		List<Field> keys=new ArrayList<>();
		List<Class<?>> clzzs=new ArrayList<>();
		for(Entry<Field,Class<?>> entry:keyAdnField.entrySet()) {
			keys.add(entry.getKey());
			clzzs.add(entry.getValue());
		}
		if(iskey)
			return keys;
		else
			return clzzs;
	}
	
	/**
	 * �жϸ�ʵ���Ӧ������������(����int����/UUID����/��ͨ����)
	 * @param pojoClass
	 * @return
	 */
	public static PrimaryType getIdType(Class<?> pojoClass) {
		Field idF=getIdField(pojoClass);
		Id id=idF.getAnnotation(Id.class);
		return id.type();
	}
	
	/**
	 * �õ�����������������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String primary(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.primary();
		}else {
			return "";
		}
	}
	
	/**
	 * �õ�������ͨ��������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String[] index(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.index();
		}else {
			return new String[0];
		}
	}
	
	/**
	 * �õ�����Ψһֵ��������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String[] unique(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.unique();
		}else {
			return new String[0];
		}
	}
	
	/**
	 * �õ�����ȫ����������Ϣ
	 * @param pojoClass
	 * @return
	 */
	public static String[] fulltext(Class<?> pojoClass) {
		if(pojoClass.isAnnotationPresent(Table.class)) {
			Table table=pojoClass.getAnnotation(Table.class);
			return table.fulltext();
		}else {
			return new String[0];
		}
	}

	/**
	 * �õ����ı����������Ӳ���ʱʹ��
	 * @param pojoClass
	 * @return
	 */
	public static String tableAlias(Class<?> pojoClass){
		if(pojoClass.isAnnotationPresent(Table.class)){
			String alias=pojoClass.getAnnotation(Table.class).alias();
			if(!"".equals(alias))
				return alias;
			return getTable(pojoClass);
		}
		return getTable(pojoClass);
	}

	/**
	 * ������From����ʹ��
	 * @param pojoClass
	 * @return
	 */
	public static String selectFromTableAlias(Class<?> pojoClass){
		if(tableAlias(pojoClass).equals(getTable(pojoClass)))
			return getTable(pojoClass);
		return getTable(pojoClass)+" "+tableAlias(pojoClass);
	}
}