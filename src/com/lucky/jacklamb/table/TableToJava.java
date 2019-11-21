package com.lucky.jacklamb.table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.lucky.jacklamb.exception.NoDataSourceException;
import com.lucky.jacklamb.ioc.config.DataSource;

/**
 * �������ݿ��еı�ת��Ϊ��Ӧ��JavaBean
 * @author fk-7075
 *
 */
public class TableToJava {
	/**
	 * ����JavaBean(���÷�ʽurl)
	 */
	public static void generateJavaSrc() {
		List<GetJavaSrc> src=JavaFieldGetSet.getMoreJavaSrc();
		String srcpath=DataSource.getDataSource().getSrcPath();
		String packpath=DataSource.getDataSource().getReversePack().replaceAll("\\.", "/");
		String url=srcpath+"/"+packpath;
		writerToJava(src,url,false,true);
	}
	
	/**
	 * ����JavaBean(���÷�ʽurl,�ֶ��������)
	 * @param tables ����
	 */
	public static void b_generateJavaSrc(String...tables) {
		List<GetJavaSrc> src=JavaFieldGetSet.getAssignJavaSrc(tables);
		String srcpath=DataSource.getDataSource().getSrcPath();
		String packpath=DataSource.getDataSource().getReversePack().replaceAll("\\.", "/");
		String url=srcpath+"/"+packpath;
		writerToJava(src,url,false,false);
	}
	
	/**
	 * ����JavaBean(�ֶ�����url,�ֶ��������)
	 * @param srcPath src�ļ�����·��
	 * @param tables ��Ҫ���򹤳�����javabean�ı�
	 */
	public static void a_generateJavaSrc(String srcPath,String...tables) {
		List<GetJavaSrc> src=JavaFieldGetSet.getAssignJavaSrc(tables);
		String packpath=DataSource.getDataSource().getReversePack().replaceAll("\\.", "/");
		String url=srcPath+"/"+packpath;
		writerToJava(src,url,true,false);
	}
	/**
	 * ����JavaBean(�ֶ�����url)
	 * @param srcPath src�ļ��еľ���·��
	 */
	public static void generateJavaSrc(String srcPath) {
		List<GetJavaSrc> src=JavaFieldGetSet.getMoreJavaSrc();
		String packpath=DataSource.getDataSource().getReversePack().replaceAll("\\.", "/");
		String url=srcPath+"/"+packpath;
		writerToJava(src,url,true,true);
	}
	
	/**
	 * ����javaԴ�ļ�
	 * @param src Դ������
	 * @param path λ��
	 * @param isManual �Ƿ��ֶ�
	 * @param ispackBox �Ƿ�����PackBox
	 */
	private static void writerToJava(List<GetJavaSrc> src,String path,boolean isManual,boolean ispackBox) {
		File file=new File(path);
		if(!file.exists())
			file.mkdirs();
		BufferedWriter bw=null;
		for (GetJavaSrc getJavaSrc : src) {
			try {
				bw=new BufferedWriter(new FileWriter(new File(path+"/"+getJavaSrc.getClassName()+".java")));
				System.out.println(path+"/"+getJavaSrc.getClassName()+".java");
				bw.write(getJavaSrc.getPack());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getImpor());
				bw.write("\n");
				bw.write("\n");
				bw.write(getJavaSrc.getJavaSrc());
				bw.write(getJavaSrc.getToString());
				bw.close();
			} catch (IOException e) {
				if(isManual)
					throw new NoDataSourceException("����ȷ�����򹤳�������Ϣ���޷�ִ��JavaBean���ɳ�������classpath�µ�db.properties�����ļ��е�'reverse.package'���Ե�������Ϣ�����߼��appconfig����������[ApplicationConfig����]��setDataSource�����Ƿ���ȷ��");
				else
					throw new NoDataSourceException("����ȷ�����򹤳�������Ϣ���޷�ִ��JavaBean���ɳ�������classpath�µ�db.properties�����ļ��е�'reverse.package'��srcpath���Ե�������Ϣ�����߼��appconfig����������[ApplicationConfig����]��setDataSource�����Ƿ���ȷ��");
			}
		}
		if(ispackBox) {
			PackBoxSrc p=PackBoxSrc.getPackBoxSrc();
			try {
				bw=new BufferedWriter(new FileWriter(new File(path+"/"+p.getClassName()+".java")));
				System.out.println(path+"/"+p.getClassName()+".java");
				bw.write(p.getPack());
				bw.write(p.getImpor());
				bw.write(p.getField());
				bw.write(p.getGetset());
				bw.write(p.getEnd());
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}