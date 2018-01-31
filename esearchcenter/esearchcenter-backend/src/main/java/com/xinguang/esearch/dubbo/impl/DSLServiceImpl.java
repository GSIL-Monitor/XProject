package com.xinguang.esearch.dubbo.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearch.service.ClientQueryService;
import com.xinguang.esearchcenter.inter.DSLService;

@Service("dSLService")
public class DSLServiceImpl implements DSLService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DSLService.class);

	@Autowired
	private ClientQueryService clientQueryService;
	
	@Override
	public String DSLExcute(String DSLString, String indexName, String typeName) {
		// TODO Auto-generated method stub
		IndexBean indexBean = setIndexInfo(indexName, typeName);
		JSONObject result = null;
		if (indexBean != null) {
			result = clientQueryService.queryDSL(indexName, typeName, DSLString);
		}
		return result.toJSONString();
	}

	private IndexBean setIndexInfo(String indexName, String typeName) {
		if (StringUtils.isNotBlank(indexName) && StringUtils.isNotBlank(typeName)) {
			return new IndexBean(indexName, typeName);
		} else {
			LOGGER.error("load index info failed. indexName={}, typeName={}", indexName, typeName);
			return null;
		}
	}
}
