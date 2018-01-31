/**
 * Project Name:esearchcenter-backend
 * File Name:ContentServiceImpl.java
 * Package Name:com.xinguang.esearch.dubbo.impl
 * Date:2016年11月9日下午7:12:46
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.dubbo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearch.service.ClientQueryService;
import com.xinguang.esearch.service.impl.ClientQueryServiceImpl;
import com.xinguang.esearchcenter.enums.SortOrderEnum;
import com.xinguang.esearchcenter.inter.ContentService;
import com.xinguang.esearchcenter.request.BaseRequest;
import com.xinguang.esearchcenter.request.PageRequest;
import com.xinguang.esearchcenter.request.TopRequest;

/**
 * ClassName:ContentServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月9日 下午7:12:46 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

	@Value("${elasticsearch.content_index}")
	private String content_index;

	@Value("${elasticsearch.content_type}")
	private String content_type;

	@Autowired
	private ClientQueryService clientQueryService;

	@Override
	public JSONObject pageSearch(PageRequest request) {
		// TODO Auto-generated method stub
		IndexBean indexBean = setIndexInfo(content_index, content_type);
		JSONObject result = null;
		if (indexBean != null) {
			result = clientQueryService.queryForPage(indexBean, request);
		}

		return result;
	}

	@Override
	public JSONObject topSearch(TopRequest request) {
		// TODO Auto-generated method stub
		IndexBean indexBean = setIndexInfo(content_index, content_type);
		JSONObject result = null;
		if (indexBean != null) {
			result = clientQueryService.queryForTop(indexBean, request);
		}

		return result;
	}

	private IndexBean setIndexInfo(String indexName, String typeName) {
		if (StringUtils.isNotBlank(indexName) && StringUtils.isNotBlank(typeName)) {
			return new IndexBean(indexName, typeName);
		} else {
			LOGGER.error("load index info failed. content_index={}, content_type={}", indexName, typeName);
			return null;
		}
	}
}
