package com.lucky.jacklamb.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lucky.jacklamb.annotation.Download;
import com.lucky.jacklamb.annotation.RequestMapping;
import com.lucky.jacklamb.enums.RequestMethod;
import com.lucky.jacklamb.ioc.ApplicationBeans;
import com.lucky.jacklamb.ioc.ControllerAndMethod;
import com.lucky.jacklamb.ioc.config.WebConfig;
import com.lucky.jacklamb.mapping.AnnotationOperation;
import com.lucky.jacklamb.mapping.UrlParsMap;

@MultipartConfig
public class LuckyDispatherServlet extends HttpServlet {
	
	private RequestMethod method=RequestMethod.POST;
	private static final long serialVersionUID = 3808567874497317419L;
	private AnnotationOperation anop;
	private ApplicationBeans beans;
	private WebConfig webCfg;
	private UrlParsMap urlParsMap;
	private ResponseControl responseControl;

	public void init(ServletConfig config) {
		beans=ApplicationBeans.createApplicationBeans();
		anop = new AnnotationOperation();
		webCfg=WebConfig.getWebConfig();
		urlParsMap=new UrlParsMap();
		responseControl=new ResponseControl();
		beans.printBeans();
	}

	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.method=RequestMethod.DELETE;
		this.doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.method=RequestMethod.PUT;
		this.doPost(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.method=RequestMethod.GET;
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String encoding=webCfg.getEncoding();
			this.method=urlParsMap.chagenMethod(req,resp,this.method);
			String uri = req.getRequestURI();
			uri=java.net.URLDecoder.decode(new String(uri.getBytes(encoding), req.getCharacterEncoding()), req.getCharacterEncoding());
			Model model=new Model(req,resp,this.method,encoding);
			urlParsMap.setLuckyWebContext(model);
			String context = req.getContextPath();
			String path = uri.replace(context, "");
			if (path.endsWith(".do")||path.endsWith(".xfl")||path.endsWith("fk")||path.endsWith(".cad")||path.endsWith(".lcl")) {
				path = path.substring(0, path.lastIndexOf("."));
			}
			if(webCfg.getStaticHander().containsKey(path)) {
				String forwardurl=webCfg.getHanderPrefixAndSuffix().get(0)+webCfg.getStaticHander().get(path)+webCfg.getHanderPrefixAndSuffix().get(1);
				req.getRequestDispatcher(forwardurl).forward(req, resp);
			}else {
				ControllerAndMethod controllerAndMethod = beans.getCurrControllerAndMethod(path);
				if(controllerAndMethod==null) {
					resp.getWriter().print("<h3>找不与请求相匹配的映射资源,请检查您的URL是否正确[404:"+req.getRequestURL()+"]....</h3>");
					return;
				}
				model.setRestMap(controllerAndMethod.getRestKV());
				urlParsMap.setCross(req,resp, controllerAndMethod);
				Method method = controllerAndMethod.getMethod();
				if(!urlParsMap.isExistRequestMethod(method,this.method)&&method.isAnnotationPresent(RequestMapping.class)) {
					resp.getWriter().print("<h3>500:您的请求类型为"+this.method+",当前方法并不支持！</h3>");
				}else {
					boolean isDownload = method.isAnnotationPresent(Download.class);
					Object obj = controllerAndMethod.getController();
					beans.autowReqAdnResp(obj,model);
					Object[] args;
					Object obj1 = new Object();
					args = (Object[]) anop.getControllerMethodParam(model,method);
					obj1 = method.invoke(obj, args);
					if (isDownload == true)//下载操作
						anop.download(model, method);
					responseControl.jump(model,controllerAndMethod.getPreAndSuf(), method, obj1);
				}

			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			this.method=RequestMethod.POST;
			urlParsMap.closeLuckyWebContext();
		}
	}
}