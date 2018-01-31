/**
 * 
 */
package com.xinguang.xherald.mq.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinguang.xherald.mq.api.MQAPI;
import com.xinguang.xherald.mq.beans.MQInitConnection;
import com.xinguang.xherald.mq.utils.MQHttpUtil;

/**
 * @author hzlige
 *
 */
@Component
public class RabbitMQAPI implements MQAPI {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQAPI.class);
	
	@Autowired
	private MQHttpUtil httpRequest;
	
	@Autowired
	private MQInitConnection mqInitConnection;

	@Override
		// TODO Auto-generated method stub
	public String listExchange() {
		String requestURL = "http://"+mqInitConnection.host+":"+mqInitConnection.port+"/api/exchanges";
		String result = httpRequest.sendGet(requestURL, null, mqInitConnection.username, mqInitConnection.password);
		return result;
	}

	@Override
	public String listQueue() {
		// TODO Auto-generated method stub
		String requestURL = "http://"+mqInitConnection.host+":"+mqInitConnection.port+"/api/queues";
		String result = httpRequest.sendGet(requestURL, null, mqInitConnection.username, mqInitConnection.password);
		return result;
	}

	public boolean hasExchange(String name) {
		String jsonString = listExchange();
		JSONArray jsonList =  (JSONArray) JSON.parse(jsonString);
		for (int i = 0; i < jsonList.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonList.get(i);
			//System.out.println("[" + i + "]name=" + jsonObj.get("name"));
			if (jsonObj.get("name").equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasQueue(String name) {
		String jsonString = listQueue();
		JSONArray jsonList =  (JSONArray) JSON.parse(jsonString);
		for (int i = 0; i < jsonList.size(); i++) {
			JSONObject jsonObj = (JSONObject) jsonList.get(i);
			//System.out.println("[" + i + "]name=" + jsonObj.get("name"));
			if (jsonObj.get("name").equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	

}
