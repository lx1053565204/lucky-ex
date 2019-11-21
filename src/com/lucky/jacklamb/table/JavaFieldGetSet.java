package com.lucky.jacklamb.table;

import java.util.ArrayList;
import java.util.List;

import com.lucky.jacklamb.ioc.config.DataSource;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * �õ�����ĳ�����Ե�Դ���루����������get/set������Դ�룩
 * @author fk-7075
 *
 */
public class JavaFieldGetSet {
	private String field;
	private String getField;
	private String setField;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getGetField() {
		return getField;
	}
	public void setGetField(String getField) {
		this.getField = getField;
	}
	public String getSetField() {
		return setField;
	}
	public void setSetField(String setField) {
		this.setField = setField;
	}
	public void to_String() {
		System.out.println(field);
		System.out.println(getField);
		System.out.println(setField);
	}
	
	public JavaFieldGetSet(String field,String type,String id,List<String> keys) {
		String Id="";
		String key="";
		if(id.equals(field)) {
			Id="\t@Id\n";
		}
		if(keys.contains(field)) {
			key="\t@Key\n";
		}
		this.field=Id+key+"\tprivate "+type+" "+field+";\n\n";
		this.getField="\tpublic "+type+" get"+LuckyUtils.TableToClass(field)+"(){\n\t\treturn this."+field+";\n\t}";
		this.setField="\tpublic void set"+LuckyUtils.TableToClass(field)+"("+type+" "+field+"){\n\t\tthis."+field+"="+field+";\n\t}";
	}
	
	/**
	 * ����TableStructure��Ӧ�������Դ����
	 * @param ts TableStructure����
	 * @return ��Ӧ����javaԴ����
	 */
	public static GetJavaSrc getOneJavaSrc(TableStructure ts){
		GetJavaSrc javasrc=new GetJavaSrc();
		List<JavaFieldGetSet> list=new ArrayList<JavaFieldGetSet>();
		javasrc.setClassName(ts.getTableName());
		javasrc.setPack("package "+DataSource.getDataSource().getReversePack()+";");
		javasrc.setImpor("import java.util.Date;\nimport java.sql.*;\nimport java.util.*;\nimport com.lucky.annotation.Id;\nimport com.lucky.annotation.Key;");
		javasrc.setToString(ts.getToString());
		javasrc.setConstructor(ts.getConstructor());
		javasrc.setParameterConstructor(ts.getParameterConstructor());
		String src="@SuppressWarnings(\"all\")\npublic class "+ts.getTableName()+"{\n";
		for(int i=0;i<ts.getFields().size();i++) {
			JavaFieldGetSet jf=new JavaFieldGetSet(ts.getFields().get(i), ts.getTypes().get(i),ts.getPri(),ts.getMuls());
			list.add(jf);
		}
		for (JavaFieldGetSet jf : list) {
			src+=jf.getField()+"";
		}
		src+="\n"+ts.getConstructor()+"\n\n"+ts.getParameterConstructor()+"\n";
		for (JavaFieldGetSet jf : list) {
			src+="\n"+jf.getGetField()+"\n";
			src+=jf.getSetField()+"\n";
		}
		javasrc.setJavaSrc(src);
		return javasrc;
	}
	
	
	/**
	 * �õ�ָ������java����
	 * @param tables ����
	 * @return
	 */
	public static List<GetJavaSrc> getAssignJavaSrc(String...tables){
		List<GetJavaSrc> javasrclist=new ArrayList<GetJavaSrc>();
		List<TableStructure> list=TableStructure.getAssignTableStructure(tables);
		for (TableStructure tableStructure : list) {
			GetJavaSrc java=JavaFieldGetSet.getOneJavaSrc(tableStructure);
			javasrclist.add(java);
		}
		return javasrclist;
	}
	
	/**
	 * �õ����ݿ������б���Ӧ��javaԴ�����GetJavaSrc�ļ���
	 * @return
	 */
	public static List<GetJavaSrc> getMoreJavaSrc(){
		List<GetJavaSrc> javasrclist=new ArrayList<GetJavaSrc>();
		List<TableStructure> list=TableStructure.getMoreTableStructure(new Tables());
		for (TableStructure tableStructure : list) {
			GetJavaSrc java=JavaFieldGetSet.getOneJavaSrc(tableStructure);
			javasrclist.add(java);
		}
		return javasrclist;
	}

}