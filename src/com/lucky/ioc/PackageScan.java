package com.lucky.ioc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ��������ʱ��Ĭ�ϰ�ɨ��
 * @author fk-7075
 *
 */
public class PackageScan {

	private String projectPath;
	
	private static PackageScan packageScan;
	
	private PackageScan() {
		projectPath=this.getClass().getClassLoader().getResource("").getPath();
		projectPath=projectPath.replaceAll("\\\\", "/").substring(1,projectPath.length()-1);
	}
	
	public static PackageScan getPackageScan() {
		if(packageScan==null)
			packageScan=new PackageScan();
		return packageScan;
	}
	
	/**
	 * �ҵ�����Ioc��������ļ��еİ�·���������뵽�ļ�����
	 * @param components ��ͨIoc���
	 */
	/**
	 * �ҵ�����Ioc��������ļ��еİ�·���������뵽�ļ�����
	 * @param components
	 * @param suffix
	 */
	public void loadComponent(List<String> components,String...suffix) {
		List<String> clist=new ArrayList<>();
		findDafaultFolder(clist,projectPath,suffix);
		addClassPath(components,clist);
	}
	
	public static void main(String[] args) {
		PackageScan ps=new PackageScan();
		System.out.println(ps.loadComponent("aopp"));
	}

	/**
	 * �ҵ�����Ioc��������ļ��еİ�·���������뵽�ļ�����
	 * @param suffix
	 * @return
	 */
	public List<String> loadComponent(String...suffix) {
		List<String> components=new ArrayList<>();
		List<String> clist=new ArrayList<>();
		findDafaultFolder(clist,projectPath,suffix);
		addClassPath(components,clist);
		return components;
	}
	
	/**
	 * �ҵ�����Mapper��������ļ��еľ���·���������뵽�ļ�����
	 * @param mappers mapper�ӿ����
	 */
	public void loadMapper(List<String> mappers,String...suffix) {
		List<String> mlist=new ArrayList<>();
		findDafaultFolder(mlist,projectPath,suffix);
		addClassPath(mappers,mlist);
	}
	
	/**
	 * �õ���������İ�·��,����������
	 * @param components ����
	 * @param paths ��������ļ��еľ���·��
	 */
	private void addClassPath(List<String> components,List<String> paths) {
		for(String path:paths) {
			File file=new File(path);
			File[] listFiles = file.listFiles();
			for(File f:listFiles) {
				if(f.getName().endsWith(".class")) {
					String clzzpath=f.getAbsolutePath();
					clzzpath.substring(0, clzzpath.length()-6);
					String u=(projectPath+"/").replaceAll("/", "\\\\");
					clzzpath=clzzpath.replace(u, "");
					clzzpath=clzzpath.replaceAll("\\\\", "\\.");
					components.add(clzzpath);
				}
			}
				
		}
	}
	
	
	/**
	 * �ҵ��ļ���Ϊsuffix���ļ���
	 * @param defaultPaths ���ڴ洢Ŀ���ļ��о���·���ļ���
	 * @param path ��ʼ�ļ��еľ���·��
	 * @param suffix ��Ҫ�õ����ļ�����
	 */
	private void findDafaultFolder(List<String> defaultPaths,String path,String...suffixs){
		List<String> suffixList=Arrays.asList(suffixs);
		File file=new File(path);
		File[] listFiles = file.listFiles();
		for(File f:listFiles) {
			if(f.isDirectory()) {
				if(isSuffix(suffixList,f.getName().toLowerCase()))
					defaultPaths.add(f.getAbsolutePath());
				else
					findDafaultFolder(defaultPaths,path+"/"+f.getName(),suffixs);
			}
		}
	}
	
	/**
	 * �ж�str�Ƿ���suffixs�е�ĳ��Ԫ�ؽ�β
	 * @param suffixs
	 * @param str
	 * @return
	 */
	private boolean isSuffix(List<String> suffixs,String str) {
		for(String suffix:suffixs) {
			if(str.endsWith(suffix))
				return true;
		}
		return false;
	}
}

