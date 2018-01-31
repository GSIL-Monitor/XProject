package com.xinguang.xherald.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * ClassName: JsonUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年6月30日 下午8:30:28 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class JsonPojoUtil {
	private static Logger Logger = LoggerFactory.getLogger(JsonPojoUtil.class);

	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T json2Obj(String json, Class<T> type) {
		try {
			return mapper.readValue(json, type);
		} catch (Exception e) {
			Logger.error("The data format error,ipput={},errorMessage={}",json,e);
			throw new RuntimeException("The data format error", e);
		}
	}

	public static String obj2Json(Object obj) {
		try {
			if (obj == null) {
				return "";
			}
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			Logger.error("The data format error,ipput={},errorMessage={}",obj,e);
			throw new RuntimeException("The data format error", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> obj2Map(Object obj) {
		try {
			return mapper.convertValue(obj, HashMap.class);
		} catch (Exception e) {
			Logger.error("The data format error,ipput={},errorMessage={}",obj,e);
			throw new RuntimeException("The data format error", e);
		}
	}

	/**
	 * f返回json格式。
	 *
	 * @param <T>
	 * @param response
	 * @param data
	 * @param isSuccess
	 */
	public static <T> Object strToJsonObject(String data, T t) {
		try {
			if (StringUtils.equals(data, "error")) {
				return null;
			}
			ObjectMapper mapper = new ObjectMapper();
			T object = mapper.readValue(data, new TypeReference<T>() {});
			return object;
		} catch (Exception e) {
			Logger.error("The data format error,ipput={},errorMessage={}",data,e);
			throw new RuntimeException("The data format error", e);
		}
	}
}
