package com.bench.app.ch.user.wap.base.core.model.api;

/**
 * 传参实体
 * 
 * @author Pink
 *
 * @version $Id: ApiJsonParam, v 0.1 2019年05月23日 16:17:18 Pink Exp $
 */
public class ApiJsonParamModel {

	private String value;

	private String explain;

	private boolean empty;

	private String paramType;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
}
