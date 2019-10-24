package com.bench.app.ch.user.wap.base.core.model.api;

import com.bench.common.enums.EnumBase;

/**
 * web工程服务枚举
 * 
 * @author Pink
 *
 * @version $Id: WebProjectServiceEnum, v 0.1 2019年05月15日 09:58:03 Pink Exp $
 */
public enum WebProjectServiceEnum implements EnumBase {

	ACCOUNT("账务核心"),

	CMS("新闻核心"),

	HOME("基础核心"),

	KEFU("客服核心"),

	LOTTERY("彩票核心"),

	MSGCORE("消息核心"),

	RACE("赛事核心"),

	USER("用户核心"),

	;

	private WebProjectServiceEnum(String message) {
		this.message = message;
	}

	private String message;

	public String message() {
		// TODO Auto-generated method stub
		return message;
	}

	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}
}
