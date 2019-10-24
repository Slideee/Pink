package com.bench.app.ch.user.wap.base.web.home.api.listener;

import com.bench.app.ch.user.wap.base.core.model.api.ApiJsonModel;
import com.bench.app.ch.user.wap.base.core.model.api.ApiJsonParamModel;
import com.bench.app.ch.user.wap.base.core.model.api.ApiJsonResultProModel;
import com.bench.app.ch.user.wap.base.web.home.api.annotation.ApiJson;
import com.bench.app.ch.user.wap.base.web.home.api.annotation.ApiJsonMethod;
import com.bench.app.ch.user.wap.base.web.home.api.annotation.ApiJsonParam;
import com.bench.app.ch.user.wap.base.web.home.api.annotation.ApiJsonResult;
import com.bench.common.lang.ListUtils;
import com.bench.common.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author Pink
 *
 * @version $Id: ApplicationListenerImpl, v 0.1 2019年05月15日 10:23:34 Pink Exp $
 */
@Component
public class ApplicationListenerImpl implements ApplicationListener<ContextRefreshedEvent> {
	public static final Logger log = Logger.getLogger(ApplicationListenerImpl.class);

	private static final String FORM = "Form";
	/**
	 * 封装所有@ApiJson的接口
	 */
	private Map<String, List<ApiJsonModel>> apiJsonModelMap = new ConcurrentHashMap<>();
	/**
	 * 封装请求表单xxxxForm
	 */
	private Map<String, Class<?>> requestClassMap = new ConcurrentHashMap<>();
	/**
	 * 封装返回结果集
	 */
	private Map<String, Class<?>> responseClassMap = new ConcurrentHashMap<>();
	/**
	 * 封装方法参数
	 */
	private Map<String, List<ApiJsonParamModel>> apiJsonParamModelMap = new ConcurrentHashMap<>();
	/**
	 * 封装返回结果
	 */
	private Map<String, List<ApiJsonResultProModel>> apiJsonResultProModelMap = new ConcurrentHashMap<>();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			if (event.getApplicationContext().getParent() == null) {
				long startTime = System.currentTimeMillis();
				// 获取上下文
				ApplicationContext context = event.getApplicationContext();
				// 获取所有含有ApiJson注解beanNames
				String[] beanNames = context.getBeanNamesForAnnotation(ApiJson.class);
				if (beanNames.length > 0) {
					for (String beanName : beanNames) {
						Method[] methods = context.getBean(beanName).getClass().getDeclaredMethods();
						for (Method method : methods) {

							// 判断该方法是否有ApiJsonMethod注解
							if (method.isAnnotationPresent(ApiJsonMethod.class)) {
								ApiJsonMethod apiJsonMethod = method.getAnnotation(ApiJsonMethod.class);
								// 获取注解中的值 封装一个model
								ApiJsonModel apiJsonModel = new ApiJsonModel();
								apiJsonModel.setExplain(apiJsonMethod.explain());
								apiJsonModel.setValue(apiJsonMethod.value());
								apiJsonModel.setKey(apiJsonMethod.key());
								apiJsonModel.setService(apiJsonMethod.service());
								apiJsonModel.setRequestMethod(new ArrayList<>());
								// service为key
								String key = apiJsonMethod.service().name();
								for (int i = 0; i < apiJsonMethod.method().length; i++) {
									apiJsonModel.getRequestMethod().add(apiJsonMethod.method()[i].name());
								}
								if (ListUtils.isEmpty(apiJsonModelMap.get(key))) {
									List<ApiJsonModel> list = new ArrayList<>();
									list.add(apiJsonModel);
									apiJsonModelMap.put(key, list);
								} else {
									apiJsonModelMap.get(key).add(apiJsonModel);
								}
								// 我们把Form结尾的类拿出来，封装。
								Class<?> requestClass = null;
								for (int i = 0; i < method.getParameterTypes().length; i++) {
									if (method.getParameterTypes()[i].getTypeName().endsWith(FORM)) {
										requestClass = method.getParameterTypes()[i];
										break;
									}
								}
								// 支持多个请求方法分离，如[GET,POST]/[GET],[POST]，所以封装的key不一样
								String classKey = null;
								if (apiJsonModel.getRequestMethod().size() > 1) {
									classKey = key + StringUtils.UNDERSCORE_SIGN + apiJsonModel.getKey();
								} else {
									classKey = key + StringUtils.UNDERSCORE_SIGN + apiJsonModel.getKey() + StringUtils.UNDERSCORE_SIGN
											+ apiJsonModel.getRequestMethod().get(0);
								}
								// 如果不存在Form,封装@ApiJsonParam
								if (requestClass == null) {
									List<ApiJsonParamModel> paramModelList = new ArrayList<>();
									for (int i = 0; i < method.getParameters().length; i++) {
										ApiJsonParam apiJsonParam = method.getParameters()[i].getAnnotation(ApiJsonParam.class);
										if (apiJsonParam != null) {
											ApiJsonParamModel apiJsonParamModel = new ApiJsonParamModel();
											apiJsonParamModel.setEmpty(apiJsonParam.empty());
											apiJsonParamModel.setExplain(apiJsonParam.explain());
											apiJsonParamModel.setParamType(apiJsonParam.paramType().getSimpleName());
											apiJsonParamModel.setValue(apiJsonParam.value());
											paramModelList.add(apiJsonParamModel);
										}
									}
									apiJsonParamModelMap.put(classKey, paramModelList);
								} else {
									requestClassMap.put(classKey, requestClass);
								}
								Class<?> responseClass = method.getReturnType();
								// 对于没有返回值的我们查找对应的注解
								if (responseClass == void.class) {
									ApiJsonResult apiJsonResult = method.getAnnotation(ApiJsonResult.class);
									if (apiJsonResult != null) {
										List<ApiJsonResultProModel> apiJsonResultProModelList = new ArrayList<>();
										for (int i = 0; i < apiJsonResult.value().length; i++) {
											ApiJsonResultProModel proModel = new ApiJsonResultProModel();
											proModel.setDataType(apiJsonResult.value()[i].dataType());
											proModel.setKey(apiJsonResult.value()[i].key());
											proModel.setExplain(apiJsonResult.value()[i].explain());
											apiJsonResultProModelList.add(proModel);
										}
										apiJsonResultProModelMap.put(classKey, apiJsonResultProModelList);
									}
								} else {
									responseClassMap.put(classKey, responseClass);
								}
							}
						}
					}
				}
				long hs = System.currentTimeMillis() - startTime;
				log.warn("封装@ApiJson所用耗时：" + hs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, List<ApiJsonModel>> returnApiJsonModelMap() {
		return apiJsonModelMap;
	}

	public Map<String, Class<?>> returnResponseClass() {
		return responseClassMap;
	}

	public Map<String, Class<?>> returnRequestClass() {
		return requestClassMap;
	}

	public Map<String, List<ApiJsonParamModel>> returnApiJsonParamModelMap() {
		return apiJsonParamModelMap;
	}

	public Map<String, List<ApiJsonResultProModel>> returnApiJsonResultProModelMap() {
		return apiJsonResultProModelMap;
	}
}