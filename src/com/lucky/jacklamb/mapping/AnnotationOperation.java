package com.lucky.jacklamb.mapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.lucky.jacklamb.annotation.Download;
import com.lucky.jacklamb.annotation.RequestParam;
import com.lucky.jacklamb.annotation.RestParam;
import com.lucky.jacklamb.annotation.Upload;
import com.lucky.jacklamb.exception.NotFindRequestException;
import com.lucky.jacklamb.file.MultipartFile;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.servlet.Model;
import com.lucky.jacklamb.utils.LuckyUtils;

@SuppressWarnings("all")
public class AnnotationOperation {
	

	/**
	 * ���ݲ����õ������MultipartFile
	 * @param model Model����
	 * @param formName formName ������<input type="file">��"name"����ֵ
	 * @return ����MultipartFile����
	 * @throws IOException
	 * @throws ServletException
	 */
	private MultipartFile uploadMutipar(Model model,String formName) throws IOException, ServletException {
		Part part = model.getRequest().getPart(formName);
		String projectPath=model.getRealPath("");
		return new MultipartFile(part,projectPath);
	}
	

	/**
	 * ����MultipartFile�Ķ��ļ��ϴ�
	 * @param model Model����
	 * @param method ��Ҫִ�е�Controller����
	 * @return ��Controller���������������Ӧ��ֵ����ɵ�Map(���MultipartFile)
	 * @throws IOException
	 * @throws ServletException
	 */
	private Map<String,MultipartFile> moreUploadMutipar(Model model, Method method) throws IOException, ServletException{
		Map<String,MultipartFile> map=new HashMap<>();
		Parameter[] parameters = method.getParameters();
		for(Parameter par:parameters) {
			if(MultipartFile.class.isAssignableFrom(par.getType())) {
				map.put(getParamName(par), uploadMutipar(model,getParamName(par)));
			}
		}
		return map;
	}
	
	/**
	 * ִ���ļ��ϴ�����(���ϴ����ļ�д��������ľ���λ��)
	 * @param model Model����
	 * @param formName ������<input type="file">��"name"����ֵ
	 * @param path Ҫ�ϴ������������ĸ��ļ��У�
	 * @param type �����ϴ����ļ�����
	 * @param maxSize �����ϴ��ļ�������С
	 * @return �ϴ���������ϵ��ļ���
	 * @throws IOException
	 * @throws ServletException
	 */
	private String upload(Model model,String formName,String path,String type,int maxSize) throws IOException, ServletException {
		Part part = model.getRequest().getPart(formName);
		String disposition = part.getHeader("Content-Disposition");
		if (disposition.indexOf(".") != -1) {
			// ����ļ���׺��
			String suffix = disposition.substring(disposition.lastIndexOf("."), disposition.length() - 1);
			if(!"".equals(type)) {
				if(!type.toLowerCase().contains(suffix.toLowerCase())) {
					throw new RuntimeException("�ϴ����ļ���ʽ"+suffix+"���Ϸ����Ϸ����ļ���ʽΪ��"+type);
				}
			}
			// ����������ļ�����ʱ��+�������
			long time = new Date().getTime();
			int ran=(int)(Math.random()*(9999-1000)+1000);
			String filename = time+""+ran+ suffix;
			// ��ȡ�ϴ����ļ���
			InputStream is = part.getInputStream();
			FileInputStream fis=(FileInputStream) is;
			if(maxSize!=0) {
				int size=fis.available();
				int filesize=size/1024;
				if(filesize>maxSize) {
					throw new RuntimeException("�ϴ��ļ��Ĵ�С("+filesize+"kb)�������õ����ֵ"+maxSize+"kb");
				}
			}
			// ��̬��ȡ��������·��
			String serverpath = model.getRealPath(path);
			File file = new File(serverpath);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(serverpath + "/" + filename);
			byte[] bty = new byte[1024];
			int length = 0;
			while ((length = is.read(bty)) != -1) {
				fos.write(bty, 0, length);
			}
			fos.close();
			is.close();
			return filename;
		} else {
			return null;
		}
	}
	
	
	/**
	 * �����ļ��ϴ�@Uploadע�ⷽʽ
	 * @param model Model����
	 * @param method ��Ҫִ�е�Controller����
	 * @return �ϴ�����ļ��������name��������ɵ�Map
	 * @throws IOException
	 * @throws ServletException
	 */
	private Map<String,String> moreUpload(Model model, Method method) throws IOException, ServletException{
		Map<String,String> fileMap=new HashMap<String,String>();
		if(method.isAnnotationPresent(Upload.class)) {
			Upload upload=method.getAnnotation(Upload.class);
			String[] files=upload.names();
			String[] savePaths=upload.filePath();
			String types=upload.type();
			int maxSize=upload.maxSize();
			if(savePaths.length==1) {
				for (String str : files) {
					fileMap.put(str, upload(model,str,savePaths[0],types,maxSize));
				}
			}else {
				int x=0;
				for (String str : files) {
					fileMap.put(str, upload(model,str,savePaths[x++],types,maxSize));
				}
			}
		}
		return fileMap;
	}
	
	/**
	 * ����Controller���������������ֵ����ɵ�Map(���Pojo���͵Ĳ���)
	 * @param model Model����
	 * @param method ��Ҫִ�е�Controller����
	 * @param uploadMap �ϴ�����ļ��������name��������ɵ�Map
	 * @return Controller���������������ֵ����ɵ�Map(���Pojo���͵Ĳ���)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws ServletException
	 */
	private Map<String,Object> pojoParam(Model model,Method method, Map<String, String> uploadMap) throws InstantiationException, IllegalAccessException, IOException, ServletException{
		Map<String,Object> map=new HashMap<>();
		Parameter[] parameters = method.getParameters();
		for(Parameter param:parameters) {
			if(!MultipartFile.class.isAssignableFrom(param.getType())
					&&param.getType().getClassLoader()!=null
					&&!ServletRequest.class.isAssignableFrom(param.getType())
					&&!ServletResponse.class.isAssignableFrom(param.getType())
					&&!HttpSession.class.isAssignableFrom(param.getType())
					&&!Model.class.isAssignableFrom(param.getType())
					&&getRequeatParamDefValue(param)==null) {
				Class<?> pojoclzz=param.getType();
				Object pojo=pojoclzz.newInstance();
				Field[] fields=pojoclzz.getDeclaredFields();
				createObject(model,pojo);
				for(Field fi:fields) {
					fi.setAccessible(true);
					Object fi_obj=fi.get(pojo);
					//pojo�к���@Upload���ص��ļ���
					if(uploadMap.containsKey(fi.getName())&&fi_obj==null) 
						fi.set(pojo, uploadMap.get(fi.getName()));
				}
				map.put(getParamName(param), pojo);
			}
			
		}
		return map;
		
	}

	/**
	 * �ļ����ز���@Download
	 * @param model Model����
	 * @param method ��Ҫִ�е�Controller����
	 * @throws IOException
	 */
	public void download(Model model, Method method) throws IOException {
		Download dl = method.getAnnotation(Download.class);
		String fileName = dl.name();
		String filePath = dl.filePath();
		String file = model.getRequestPrarmeter(fileName); // �ͻ��˴��ݵ���Ҫ���ص��ļ���
		String path = model.getRealPath(filePath) + file; // Ĭ����Ϊ�ļ��ڵ�ǰ��Ŀ�ĸ�Ŀ¼
		FileInputStream fis = new FileInputStream(path);
		model.getResponse().setCharacterEncoding("utf-8");
		model.getResponse().setHeader("Content-Disposition", "attachment; filename=" + file);
		ServletOutputStream out = model.getResponse().getOutputStream();
		byte[] bt = new byte[1024];
		int length = 0;
		while ((length = fis.read(bt)) != -1) {
			out.write(bt, 0, length);
			out.flush();
		}
		out.close();
	}
	

	/**
	 * �õ���Ҫִ�е�Controller�����Ĳ����б���ֵ
	 * @param model Model����
	 * @param method ��Ҫִ�е�Controller����
	 * @return ��Ҫִ�е�Controller�����Ĳ����б�
	 * @throws IOException
	 * @throws ServletException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object getControllerMethodParam(Model model, Method method) throws IOException, ServletException, InstantiationException, IllegalAccessException {
		Parameter[] parameters = method.getParameters();
		Object[] args = new Object[parameters.length];
		Map<String,String> uploadMap=moreUpload(model,method);
		Map<String,MultipartFile> multiUploadMap=moreUploadMutipar(model,method);
		Map<String,Object> pojoMap=pojoParam(model,method,uploadMap);
		for(int i=0;i<parameters.length;i++) {
			if(uploadMap.containsKey(getParamName(parameters[i]))&&String.class.isAssignableFrom(parameters[i].getType())) {
				args[i]=uploadMap.get(getParamName(parameters[i]));
			}else if(multiUploadMap.containsKey(getParamName(parameters[i]))&&MultipartFile.class.isAssignableFrom(parameters[i].getType())) {
				args[i]=multiUploadMap.get(getParamName(parameters[i]));
			}else if(pojoMap.containsKey(getParamName(parameters[i]))){
				args[i]=pojoMap.get(getParamName(parameters[i]));
			}else if(ServletRequest.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model.getRequest();
			}else if(HttpSession.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model.getSession();
			}else if(ServletResponse.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model.getResponse();
			}else if(Model.class.isAssignableFrom(parameters[i].getType())){
				args[i]=model;
			}else if(parameters[i].isAnnotationPresent(RestParam.class)){
				RestParam rp=parameters[i].getAnnotation(RestParam.class);
				String restKey=rp.value();
				args[i]=LuckyUtils.typeCast(model.getRestMap().get(restKey),parameters[i].getType().getSimpleName());
			}else {
				String reqParaName=getParamName(parameters[i]);
				String defparam=getRequeatParamDefValue(parameters[i]);
				if(parameters[i].getType().isArray()&&parameters[i].getType().getClassLoader()==null) {
					if(model.parameterMapContainsKey(reqParaName)) {
						args[i]=model.getArray(reqParaName, parameters[i].getType());
					}else {
						if(defparam==null)
							throw new NotFindRequestException("ȱ�����������"+reqParaName);
						args[i]=ApplicationBeans.createApplicationBeans().getBean(defparam);
					}
				}else {
					if(model.parameterMapContainsKey(reqParaName)) {
						args[i]=model.getArray(reqParaName, parameters[i].getType())[0];
					}else if(model.restMapContainsKey(reqParaName)){
						args[i]=model.getRestParam(getParamName(parameters[i]), parameters[i].getType());
					}else {
						if(defparam==null)
							throw new NotFindRequestException("ȱ�����������"+reqParaName);
						if(parameters[i].getType().getClassLoader()==null){
							args[i]=LuckyUtils.typeCast(defparam, parameters[i].getType().getSimpleName());
						}else {
							args[i]=ApplicationBeans.createApplicationBeans().getBean(defparam);
						}

					}
				}
			}
		}
		return args;
	}
	

	
	/**
	 * ΪController�����е�pojo����ע��request���RestMap�ж�Ӧ��ֵ
	 * @param request 
	 * @param pojo
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object createObject(Model model,Object pojo) throws InstantiationException, IllegalAccessException {
		Field[] fields=pojo.getClass().getDeclaredFields();
		for(Field fie:fields) {
			fie.setAccessible(true);
			if(LuckyUtils.isJavaClass(fie.getType())) {
				if(fie.getType().isArray()) {
					fie.set(pojo,model.getArray(fie.getName(), fie.getType()));
				}else {
					if(model.getArray(fie.getName(), fie.getType())!=null) {
						fie.set(pojo,model.getArray(fie.getName(), fie.getType())[0]);
					}
					if(model.getRestMap().containsKey(fie.getName())) {
						fie.set(pojo,model.getRestParam(fie.getName(), fie.getType()));
					}
				}
			}else {
				Object object=fie.getType().newInstance();
				object=createObject(model,object);
				fie.set(pojo, object);
			}
		}
		return pojo;
	}
	
	/**
	 * �õ�һ�������ı�ǲ�����
	 * @param param
	 * @return
	 */
	private String getParamName(Parameter param) {
		if(param.isAnnotationPresent(RequestParam.class)) {
			RequestParam rp=param.getAnnotation(RequestParam.class);
			return rp.value();
		}else {
			return param.getName();
		}
	}
	
	private String getRequeatParamDefValue(Parameter param) {
		if(param.isAnnotationPresent(RequestParam.class)) {
			RequestParam rp=param.getAnnotation(RequestParam.class);
			String defValue=rp.def();
			if("".equals(defValue))
				return null;
			return defValue;
		}else {
			return null;
		}
	}
}