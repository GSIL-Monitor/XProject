package com.xinguang.xusercenter.customer.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * JSON 返回格式自定义
 * 
 * @author yangsh
 *
 */
@Slf4j
public class CustomObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = -2180400817772904183L;

	public CustomObjectMapper() {
		log.info("load CustomObjectMapper");

		this.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true); // 字段名加双引号
		this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false); // 数值加双引号

		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException {
				jg.writeString("");
			}
		});
	}
	
}
