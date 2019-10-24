package com.bench.app.ch.user.wap.base.web.home.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回结果中字段的定义
 *
 * @author Pink
 *
 * @version $Id: ApiJsonResultPro, v 0.1 2019年05月27日 17:36:23  Pink Exp $
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonResultPro {

	String key();

	Class<?> dataType() default String.class;

	String explain() default "";

}