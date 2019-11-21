package com.lucky.jacklamb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ��MVC�д����ڱ�ʶһ��Controller���
 * 	value������ʹ�ô�ע������������һ��IOC���
 * 	prefix��MVC�е���ͼ��λ��ǰ׺(eg: /WEB_INF/jsp/)
 * 	suffix:MVC�е���ͼ��λ�ĺ�׺(eg: .jsp)
 * -------------------------------------------
 * 	ʹ��"return String"�ķ�ʽ����ת�����ض����Ŀ�ĵ�(����ֵΪString�ķ���)
 * 	1.ת����ҳ�棺��ǰ׺ return page
 * 	2.ת����Controller����:return forward:method
 *	3.�ض���ҳ�棺return page:pageing
 *	4.�ض���Controller������return redirect:method
 * @author fk-7075
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
	String value() default "";
	String prefix() default "";
	String suffix() default "";
}