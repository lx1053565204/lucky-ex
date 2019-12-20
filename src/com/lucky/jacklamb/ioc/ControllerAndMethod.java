package com.lucky.jacklamb.ioc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.utils.IpUtil;

/**
 * Controller
 * @author fk-7075
 *
 */
public class ControllerAndMethod {
	
	/**
	 * Controller对象
	 */
	private Object controller;
	
	/**
	 * 该方法支持的请求
	 */
	private RequestMethod[] requestMethods;
	
	/**
	 * 该请求支持的ip地址
	 */
	private Set<String> ips;
	
	/**
	 * 该请求支持的ip段范围
	 */
	private String ipSection;
	
	/**
	 * Rest风格参数名与值的Map
	 */
	private Map<String,String> restKV;
	
	/**
	 * 前后缀参数
	 */
	private List<String> preAndSuf;
	
	/**
	 * Controller方法
	 */
	private Method method;
	
	/**
	 * Url请求
	 */
	private String url;
	
	public void setPrefix(String presix) {
		preAndSuf.set(0, presix);
	}
	public void setSuffix(String suffix) {
		preAndSuf.set(1, suffix);
	}

	public List<String> getPreAndSuf() {
		return preAndSuf;
	}

	public void setPreAndSuf(List<String> preAndSuf) {
		this.preAndSuf = preAndSuf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ControllerAndMethod() {
		restKV=new HashMap<>();
		preAndSuf=new ArrayList<>();
		ips=new HashSet<>();
		preAndSuf.add("");
		preAndSuf.add("");
	}
	
	public void restPut(String key,String value) {
		restKV.put(key, value);
	}
	
	public String getVaule(String key) {
		return restKV.get(key);
	}
	public Map<String, String> getRestKV() {
		return restKV;
	}

	public void setRestKV(Map<String, String> restKV) {
		this.restKV = restKV;
	}

	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public RequestMethod[] getRequestMethods() {
		return requestMethods;
	}
	public void setRequestMethods(RequestMethod[] requestMethods) {
		this.requestMethods = requestMethods;
	}
	
	public boolean requestMethodISCorrect(RequestMethod method) {
		for(RequestMethod curr:requestMethods) {
			if(method.equals(curr))
				return true;
		}
		return false;
	}
	
	public boolean ipISCorrect(String currip) {
		if(ips.isEmpty())
			return true;
		if("localhost".equals(currip))
			currip="127.0.0.1";
		return ips.contains(currip);
	}
	
	public Set<String> getIps() {
		return ips;
	}
	public void setIps(Set<String> ips) {
		this.ips = ips;
	}
	
	public void addIp(String ip) {
		if("localhost".equals(ip))
			ip="127.0.0.1";
		ips.add(ip);
	}
	
	public boolean ipExistsInRange(String ip) {
		if(ipSection==null||"".equals(ipSection))
			return true;
		return IpUtil.ipExistsInRange(ip, ipSection);
	}
	public void addIds(String[] ips) {
		for(String ip:ips) {
			addIp(ip);
		}
	}
	public String getIpSection() {
		return ipSection;
	}
	public void setIpSection(String ipSection) {
		this.ipSection = ipSection;
	}
	
}
