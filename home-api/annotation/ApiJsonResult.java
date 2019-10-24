package com.bench.app.ch.user.wap.base.web.home.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回结果的定义
 *
 * @author Pink
 *
 * @version $Id: ApiJsonResult, v 0.1 2019年05月27日 17:36:17  Pink Exp $
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonResult {

	ApiJsonResultPro[] value(); // 对象属性值

}