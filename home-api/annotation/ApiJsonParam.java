package com.bench.app.ch.user.wap.base.web.home.api.annotation;

import java.lang.annotation.*;

/**
 * 描述传参
 *
 * @author Pink
 *
 * @version $Id: ApiJsonParam, v 0.1 2019年05月23日 15:58:30 Pink Exp $
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonParam {

	/**
	 * 参数名
	 */
	String value();

	/**
	 * 是否为空
	 */
	boolean empty() default false;

	/**
	 * 注释
	 */
	String explain() default "";

	/**
	 * 参数类型
	 */
	Class<?> paramType();
}