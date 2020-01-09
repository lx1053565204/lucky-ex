package com.lucky.jacklamb.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lucky.jacklamb.annotation.aop.After;
import com.lucky.jacklamb.annotation.aop.Aspect;
import com.lucky.jacklamb.annotation.aop.Before;
import com.lucky.jacklamb.aop.proxy.Point;
import com.lucky.jacklamb.aop.proxy.PointRun;
import com.lucky.jacklamb.exception.NotAddIOCComponent;
import com.lucky.jacklamb.exception.NotFindBeanException;
import com.lucky.jacklamb.ioc.config.Configuration;
import com.lucky.jacklamb.utils.LuckyUtils;

/**
 * �������󼯺�
 * @author DELL
 *
 */
public class AspectAOP {
	
	private static AspectAOP aspectAop;
	
	private Map<String,PointRun> aspectMap;
	
	private List<String> aspectIDS;
	
	private AspectAOP() {
		try {
			aspectMap=new HashMap<>();
			aspectIDS=new ArrayList<>();
			initAspectIOC(ScacFactory.createScan().loadComponent(Configuration.getConfiguration().getScanConfig().getAspectPackSuffix()));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static AspectAOP getAspectIOC() {
		if(aspectAop==null)
			aspectAop=new AspectAOP();
		return aspectAop;
	}

	public boolean containId(String id) {
		return aspectIDS.contains(id);
	}
	
	public PointRun getAspectBean(String id) {
		if(!containId(id))
			throw new NotFindBeanException("��Aspect(ioc)�������Ҳ���IDΪ--"+id+"--��Bean...");
		return aspectMap.get(id);
	}

	public Map<String, PointRun> getAspectMap() {
		return aspectMap;
	}

	public void setAspectMap(Map<String, PointRun> AspectMap) {
		this.aspectMap = AspectMap;
	}
	
	public void addAspectMap(String id,PointRun object) {
		if(containId(id))
			throw new NotAddIOCComponent("Aspect(ioc)�������Ѵ���IDΪ--"+id+"--��������޷��ظ����ӣ�������������ͬ����@Aspect������⽫�ᵼ���쳣�ķ�������......");
		aspectMap.put(id, object);
		addAspectID(id);
	}

	public List<String> getAspectIDS() {
		return aspectIDS;
	}

	public void setAspectIDS(List<String> AspectIDS) {
		this.aspectIDS = AspectIDS;
	}
	
	public void addAspectID(String id) {
		aspectIDS.add(id);
	}
	
	/**
	 * ����Aspect���
	 * @param AspectClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void initAspectIOC(List<String> AspectClass) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Aspect agann;
		Before before;
		After after;
		PointRun pointRun;
		Constructor<?> constructor;
		for(String clzz:AspectClass) {
			Class<?> Aspect=Class.forName(clzz);
			if(Aspect.isAnnotationPresent(Aspect.class)) {
				String name;
				agann=Aspect.getAnnotation(Aspect.class);
				if("".equals(agann.value())) {
					name=LuckyUtils.TableToClass1(Aspect.getSimpleName());
				}else {
					name=agann.value();
				}
				Method[] enhanceMethods=Aspect.getDeclaredMethods();
				for(Method method:enhanceMethods) {
					String Aspectid;
					if(method.isAnnotationPresent(Before.class)) {
						before=method.getAnnotation(Before.class);
						if("".equals(before.value())) {
							Aspectid=name+("."+LuckyUtils.TableToClass1(method.getName()));
						}else {
							Aspectid=name+("."+before.value());
						}
						constructor = Aspect.getConstructor();
						constructor.setAccessible(true);
						pointRun=new PointRun(constructor.newInstance(),method);
						addAspectMap(Aspectid, pointRun);
					}else if(method.isAnnotationPresent(After.class)){
						after=method.getAnnotation(After.class);
						if("".equals(after.value())) {
							Aspectid=name+("."+LuckyUtils.TableToClass1(method.getName()));
						}else {
							Aspectid=name+("."+after.value());
						}
						constructor = Aspect.getConstructor();
						constructor.setAccessible(true);
						pointRun=new PointRun(constructor.newInstance(),method);
						addAspectMap(Aspectid, pointRun);
					}else {
						continue;
					}
				}
			}else if(Point.class.isAssignableFrom(Aspect)) {
				String name;
				name=LuckyUtils.TableToClass1(Aspect.getSimpleName())+".proceed";
				constructor = Aspect.getConstructor();
				constructor.setAccessible(true);
				pointRun=new PointRun((Point)constructor.newInstance());
				addAspectMap(name,pointRun);
			}
		}
	}

}