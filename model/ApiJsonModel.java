package com.bench.app.ch.user.wap.base.core.model.api;

import java.util.List;

/**
 * 参数实体
 * 
 * @author Pink
 *
 * @version $Id: ApiJsonModel, v 0.1 2019年05月20日 11:14:19  Pink Exp $
 */
public class ApiJsonModel {

	private String key;

	private String value;

	private List<String> requestMethod;

	private String explain;

	private WebProjectServiceEnum service;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(List<String> requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public WebProjectServiceEnum getService() {
		return service;
	}

	public void setService(WebProjectServiceEnum service) {
		this.service = service;
	}
}
