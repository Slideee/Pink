package com.bench.app.ch.user.wap.base.web.home.api.annotation;

import com.bench.app.ch.user.wap.base.core.model.api.WebProjectServiceEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述json中的方法
 * 
 * @author Pink
 *
 * @version $Id: ApiJsonMethod, v 0.1 2019年05月15日 10:12:17 Pink Exp $
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonMethod {

	/**
	 * json名称
	 */
	String key();

	/**
	 * json地址
	 */
	String value();

	/**
	 * 请求方法
	 */
	RequestMethod[] method();

	/**
	 * json说明
	 */
	String explain() default "";

	/**
	 * 所属工程
	 */
	WebProjectServiceEnum service();
}