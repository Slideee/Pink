package com.bench.app.ch.user.wap.base.web.home.api;

import com.bench.app.ch.user.wap.base.core.model.api.ApiJsonModel;
import com.bench.app.ch.user.wap.base.core.model.api.WebProjectServiceEnum;
import com.bench.app.ch.user.wap.base.core.model.enums.ConfigNameEnum;
import com.bench.app.ch.user.wap.base.web.home.api.listener.ApplicationListenerImpl;
import com.bench.common.lang.BooleanUtils;
import com.bench.runtime.core.config.base.BaseConfigComponent;
import com.bench.runtime.core.config.base.model.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口文档
 *
 * @author Pink
 *
 * @version $Id: APIController, v 0.1 2019年05月14日 17:34:09 Pink Exp $
 */
@Controller
@RequestMapping(value = "/apiList.htm")
public class APIController {

	@Autowired
	private BaseConfigComponent baseConfigComponent;
	@Autowired
	private ApplicationListenerImpl applicationListenerImpl;

	@RequestMapping(method = { RequestMethod.GET })
	public String doGet(ModelMap modelMap, HttpServletRequest httpServletRequest, WebRequest webRequest) {
		// 记得做开关

		Map<String, List<ApiJsonModel>> sortMap = applicationListenerImpl.returnApiJsonModelMap();
		BaseConfig config = baseConfigComponent.getConfig(ConfigNameEnum.API_LIST_CONFIG.name());
		Map<String, String> apiListConfigMap = config.returnConfigValueAsProperties();
		Map<String, String> projectMap = new HashMap<>();
		for (int i = 0; i < WebProjectServiceEnum.values().length; i++) {
			projectMap.put(WebProjectServiceEnum.values()[i].name(), WebProjectServiceEnum.values()[i].message());
		}
		modelMap.addAttribute("enabled", BooleanUtils.toBoolean(apiListConfigMap.get("enabled")));
		modelMap.addAttribute("sortMap", sortMap);
		modelMap.addAttribute("projectMap", projectMap);
		return "apiList.vm";
	}

}
