package com.lucky.jacklamb.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.Bean;
import com.lucky.jacklamb.annotation.BeanFactory;
import com.lucky.jacklamb.annotation.Component;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * ��ͨIOC�������
 * 
 * @author DELL
 *
 */
public class ComponentIOC {

	private Map<String, Object> appMap;

	private List<String> appIDS;
	
	public ComponentIOC() {
		appMap=new HashMap<>();
		appIDS=new ArrayList<>();
	}

	public boolean containId(String id) {
		return appIDS.contains(id);
	}

	public Object getComponentBean(String id) {
		if (!containId(id))
			throw new NotFindBeanException("��Component(ioc)�������Ҳ���IDΪ--" + id + "--��Bean...");
		return appMap.get(id);

	}

	public Map<String, Object> getAppMap() {
		return appMap;
	}

	public void setAppMap(Map<String, Object> appMap) {
		this.appMap = appMap;
	}

	public void addAppMap(String id, Object object) {
		if(containId(id))
			throw new NotAddIOCComponent("Component(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ�����......");
		appMap.put(id, object);
		addAppIDS(id);
	}

	public List<String> getAppIDS() {
		return appIDS;
	}

	public void setAppIDS(List<String> appIDS) {
		this.appIDS = appIDS;
	}

	public void addAppIDS(String id) {
		appIDS.add(id);
	}

	/**
	 * ����Component���
	 * 
	 * @param componentClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public ComponentIOC initComponentIOC(List<String> componentClass)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (String clzz : componentClass) {
			Class<?> component = Class.forName(clzz);
			if (component.isAnnotationPresent(Component.class)) {
				Component com = component.getAnnotation(Component.class);
				if (!"".equals(com.id())) {
					addAppMap(com.id(), component.newInstance());
				} else if (!"".equals(com.value())) {
					addAppMap(com.value(), component.newInstance());
				} else {
					addAppMap(LuckyUtils.TableToClass1(component.getSimpleName()), component.newInstance());
				}
			} else if (component.isAnnotationPresent(BeanFactory.class)) {
				Object obj = component.newInstance();
				Method[] methods=component.getDeclaredMethods();
				for(Method met:methods) {
					if(met.isAnnotationPresent(Bean.class)) {
						Object invoke = met.invoke(obj);
						Bean bean=met.getAnnotation(Bean.class);
						if("".equals(bean.value())) {
							String Id=component.getSimpleName()+"."+met.getName();
							addAppMap(Id, invoke);
						}else {
							addAppMap(bean.value(),invoke);
						}
						
					}
				}
			}
		}
		return this;
	}

}