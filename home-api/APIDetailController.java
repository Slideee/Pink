package com.bench.app.ch.user.wap.base.web.home.api;

import com.bench.app.ch.user.wap.base.core.model.api.ApiJsonParamModel;
import com.bench.app.ch.user.wap.base.core.model.api.ApiJsonResultProModel;
import com.bench.app.ch.user.wap.base.core.model.api.JsonOutputResult;
import com.bench.app.ch.user.wap.base.core.model.enums.ConfigNameEnum;
import com.bench.app.ch.user.wap.base.web.home.api.listener.ApplicationListenerImpl;
import com.bench.common.lang.BooleanUtils;
import com.bench.common.lang.FieldUtils;
import com.bench.common.lang.StringUtils;
import com.bench.runtime.core.config.base.BaseConfigComponent;
import com.bench.runtime.core.config.base.model.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接口详情
 *
 * @author Pink
 *
 * @version $Id: APIDetailController, v 0.1 2019年05月15日 18:06:14 Pink Exp $
 */
@Controller
@RequestMapping(value = "/apiDetail.htm")
public class APIDetailController {

	@Autowired
	private ApplicationListenerImpl applicationListenerImpl;
	@Autowired
	private BaseConfigComponent baseConfigComponent;

	@RequestMapping(method = { RequestMethod.GET })
	public String doGet(ModelMap modelMap, HttpServletRequest httpServletRequest, WebRequest webRequest, String serviceName, String serviceUrl) {
		// 记得做开关
		BaseConfig config = baseConfigComponent.getConfig(ConfigNameEnum.API_LIST_CONFIG.name());
		Map<String, String> apiListConfigMap = config.returnConfigValueAsProperties();
		modelMap.addAttribute("enabled", BooleanUtils.toBoolean(apiListConfigMap.get("enabled")));

		Map<String, Class<?>> requestClassMap = applicationListenerImpl.returnRequestClass();
		Map<String, Class<?>> responseClassMap = applicationListenerImpl.returnResponseClass();
		modelMap.addAttribute("serviceNameEnum", serviceName);
		// 返回结果，参数集
		if (requestClassMap.get(serviceName) != null) {
			Set<Field> requestFields = FieldUtils.getAllField(requestClassMap.get(serviceName));
			modelMap.addAttribute("requestFields", requestFields);
		} else {
			Map<String, List<ApiJsonParamModel>> paramMap = applicationListenerImpl.returnApiJsonParamModelMap();
			modelMap.addAttribute("paramList", paramMap.get(serviceName));
		}

		if (responseClassMap.get(serviceName) != null) {
			Set<Field> responseFields = FieldUtils.getAllField(responseClassMap.get(serviceName));
			responseFields.addAll(FieldUtils.getAllField(JsonOutputResult.class));
			modelMap.addAttribute("responseFields", responseFields);
		} else {
			Map<String, List<ApiJsonResultProModel>> resultMap = applicationListenerImpl.returnApiJsonResultProModelMap();
			modelMap.addAttribute("resultList", resultMap.get(serviceName));
			modelMap.addAttribute("responseFields", FieldUtils.getAllField(JsonOutputResult.class));
		}

		modelMap.addAttribute("enabled", true);
		// 找到真正的scheme
		String scheme = httpServletRequest.getHeader("X-Forwarded-Proto");
		// 获取url
		StringBuffer url = httpServletRequest.getRequestURL();
		// 替换scheme
		String replaceUrl = StringUtils.replaceOnce(url.toString(), "http", scheme);
		// 拼接serviceUrl
		StringBuffer trueUrl = new StringBuffer(replaceUrl);
		String tempContextUrl = trueUrl.delete(trueUrl.length() - httpServletRequest.getRequestURI().length(), trueUrl.length()).toString();
		modelMap.addAttribute("serviceUrl", tempContextUrl + serviceUrl);
		String[] requestValue = StringUtils.split(serviceName, StringUtils.UNDERSCORE_SIGN);
		if (requestValue.length == 3) {
			modelMap.addAttribute("requestMethod", requestValue[2]);
		} else {
			modelMap.addAttribute("requestMethod", RequestMethod.POST.name() + StringUtils.COMMA_SIGN + RequestMethod.GET.name());
		}

		return "apiDetail.vm";
	}
}
