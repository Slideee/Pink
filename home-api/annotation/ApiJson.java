package com.bench.app.ch.user.wap.base.web.home.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * '@ApiJson'
 * 
 * @author Pink
 *
 * @version $Id: ApiJson, v 0.1 2019年05月14日 17:13:51 Pink Exp $
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJson {

	/**
	 * json名称
	 */
	String value() default "";

}