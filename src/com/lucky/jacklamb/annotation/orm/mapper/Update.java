package com.lucky.jacklamb.annotation.orm.mapper;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ORM��Mapper�ӿ���ʹ�ã�����һ�����µ����ݿ����
 * 	value������Ԥ�����SQL (eg: UPDATE SET price=?,bname=? WHERE bid=?)
 *  ָ�����²��������������²���������������Ҫ�ֶ����룬���������޶�ΪString��List[Strirng],����ʹ��ʱ��Ҫ@X�ı��
 * @author fk-7075
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Update {
	String value() default "";

}