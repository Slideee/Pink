package com.bench.app.ch.user.wap.base.core.model.api;

import com.bench.common.ErrorCode;
import com.bench.common.doc.Doc;
import com.bench.common.enums.EnumBase;
import com.bench.common.lang.ListUtils;
import com.bench.common.lang.ToStringObject;
import com.bench.common.web.errors.ErrorEntry;
import com.bench.common.web.errors.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON输出结果
 * 
 * @author chenbug
 * 
 * @version $Id: JsonOutputResult.java, v 0.1 2014-4-25 下午2:27:31 chenbug Exp $
 */

public class JsonOutputResult extends ToStringObject {

	/**
	 * 是否成功
	 */
	@Doc("是否成功")
	private boolean success;

	/**
	 * 错误
	 */
    @Doc("错误")
	private ErrorEntry error;

	/**
	 * 详细信息
	 */
    @Doc("详细信息")
	private String detailMessage;

	/**
	 * 其他输出属性
	 */
    @Doc("其他输出属性")
	private Map<String, Object> outputParameters = new HashMap<String, Object>();

	/**
	 * 字段错误
	 */
    @Doc("字段错误")
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	/**
	 * 设置错误枚举
	 * 
	 * @param errorEnum
	 */
	public void setErrorEnum(EnumBase errorEnum) {
		if (errorEnum == null) {
			return;
		}
		this.error = new ErrorEntry(errorEnum);
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * 设置验证结果集
	 * 
	 * @param result
	 * @param request
	 */
	public void setBindingResult(BindingResult result, HttpServletRequest request) {
		if (ListUtils.size(result.getAllErrors()) > 0) {
			RequestContext requestContext = new RequestContext(request);
			for (org.springframework.validation.ObjectError objectError : result.getAllErrors()) {
				if (objectError instanceof org.springframework.validation.FieldError) {
					org.springframework.validation.FieldError originalError = (org.springframework.validation.FieldError) objectError;
					FieldError error = new FieldError();
					error.setName(originalError.getField());
					error.setError(new ErrorEntry(originalError.getCode(), requestContext.getMessage(originalError)));
					fieldErrors.add(error);
				} else {
					this.setError(new ErrorEntry(objectError.getCode(), requestContext.getMessage(objectError)));
				}
			}
			this.setSuccess(false);
		}
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	/**
	 * 将结果集合push到modelMap
	 * 
	 * @param model
	 */
	public void pushToModel(Map<String, Object> model) {
		model.put("success", this.success);
		model.put("error", this.error);
		model.put("detailMessage", this.detailMessage);
		model.put("outputParameters", this.outputParameters);
		model.put("fieldErrors", this.fieldErrors);
	}

	public Map<String, Object> toMap() {
		Map<String, Object> model = new HashMap<String, Object>();
		pushToModel(model);
		return model;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorEntry getError() {
		return error;
	}

	public void setError(ErrorEntry error) {
		this.error = error;
	}

	public void setError(ErrorCode errorCode) {
		if (errorCode == null) {
			return;
		}
		this.error = new ErrorEntry(errorCode);
	}

	public Map<String, Object> getOutputParameters() {
		return outputParameters;
	}

	public void setOutputParameters(Map<String, Object> outputParameters) {
		this.outputParameters = outputParameters;
	}

	public void setOutputParameter(String key, Object value) {
		this.outputParameters.put(key, value);
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
}
