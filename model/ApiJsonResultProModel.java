package com.bench.app.ch.user.wap.base.core.model.api;

/**
 * 
 * 
 * @author Pink
 *
 * @version $Id: ApiJsonParam, v 0.1 2019年05月23日 16:17:18 Pink Exp $
 */
public class ApiJsonResultProModel {

	private String key;

	private String explain;

	private Class<?> dataType;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public void setDataType(Class<?> dataType) {
		this.dataType = dataType;
	}
}
