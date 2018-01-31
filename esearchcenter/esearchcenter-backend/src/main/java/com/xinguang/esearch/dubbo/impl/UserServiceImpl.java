package com.xinguang.esearch.dubbo.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearch.service.ClientQueryService;
import com.xinguang.esearchcenter.inter.UserService;
import com.xinguang.esearchcenter.request.PageRequest;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Value("${elasticsearch.user_index}")
	private String user_index;
	
	@Value("${elasticsearch.user_type}")
	private String user_type;

	@Autowired
	private ClientQueryService clientQueryService;
	
	@Override
	public String userSearch(PageRequest request) {
		// TODO Auto-generated method 
		IndexBean indexBean = setIndexInfo(user_index, user_type);
		JSONObject result = null;
		if (indexBean != null) {
			result = clientQueryService.queryUser(indexBean, request);
		}
		return result.toJSONString();
	}
	
	@Override
	public String userSearchByIds(List<Long> idList) {
		IndexBean indexBean = setIndexInfo(user_index, user_type);
		JSONObject result =null;
		if(indexBean != null) {
			result = clientQueryService.queryUserByIds(indexBean, idList);
		}
		return result.toJSONString();
	}
	
	private IndexBean setIndexInfo(String indexName, String typeName) {
		if (StringUtils.isNotBlank(indexName) && StringUtils.isNotBlank(typeName)) {
			return new IndexBean(indexName, typeName);
		} else {
			LOGGER.error("load index info failed. user_index={}, user_type={}", indexName, typeName);
			return null;
		}
	}

}
