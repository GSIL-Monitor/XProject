package com.xinguang.xherald.web.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.es.beans.IndexPojo;
import com.xinguang.xherald.es.handler.ESCheckHandler;
import com.xinguang.xherald.es.handler.ESHandler;
import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.mq.handler.MQCheckHandler;
import com.xinguang.xherald.mq.handler.MQHandler;
import com.xinguang.xherald.mq.thread.ThreadManager;
import com.xinguang.xherald.mq.utils.DecryptMethod;
import com.xinguang.xherald.mq.utils.MQConnectionUtil;
import com.xinguang.xherald.web.enums.StatusEnum;
import com.xinguang.xherald.web.enums.TopologyStatusEnum;
import com.xinguang.xherald.web.request.TopologyRequest;
import com.xinguang.xherald.web.response.TopologyResponse;
import com.xinguang.xherald.web.utils.TopologyUtils;
import com.xinguang.xherald.web.utils.ValidatorUtils;

@Component
public class TopologyHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyHandler.class);

	@Autowired
	private MQCheckHandler mqCheckHandler;

	@Autowired
	private ESCheckHandler esCheckHandler;

	@Autowired
	private MQHandler mqHandler;

	@Autowired
	private ESHandler esHandler;

	@Autowired
	private ThreadManager threadManager;

	@Autowired
	private MQConnectionUtil mqUtil;
	
	@Autowired
	private ValidatorUtils validatorUtils;

	public TopologyResponse createTopology(TopologyRequest request, String filePath) {
		LOGGER.info("function = {}, request = {}", "createTopology", request);

		// 第一步: 写入文件,检查入参
		TopologyUtils.write2file(request,TopologyStatusEnum.STATUS_0.getStatusCode(),filePath);

		try {
			validatorUtils.setDefaultValue(request);
			String checkStr = ValidatorUtils.notNullParam(request);
			if (checkStr != null) {
				return TopologyUtils.setTopologyResponse(false, 0000, checkStr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" create request paramters error. function={}, input={}, errorMessage={}", "createTopology",
					request, e);
			return TopologyUtils.setTopologyResponse(false, 0000, "request check failed");
		}

		IndexPojo indexPojo = null;
		try {
			indexPojo = TopologyUtils.setIndex(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" set user-defined index pojo failed. function={}, input={}, errorMessage={}", "setIndexInfo",
					request, e);
			return TopologyUtils.setTopologyResponse(false, StatusEnum.ESStatus_000.getStatusCode(),
					StatusEnum.ESStatus_000.getStatusMsg());
		}

		// 第二步：检查mq(exchangge和queue是否存在)和es(index、type和mapping是否存在)
		StatusEnum mqStatus = mqCheckHandler.checkMQTopology(request);
		if (mqStatus.getStatusCode() != StatusEnum.MQStatus_007.getStatusCode()) {
			return TopologyUtils.setTopologyResponse(false, mqStatus.getStatusCode(), mqStatus.getStatusMsg());
		}

		if (request.isCheckFields()) {
			StatusEnum esStatus = esCheckHandler.checkESTopology(indexPojo);
			if (esStatus.getStatusCode() != StatusEnum.ESStatus_007.getStatusCode()) {
				return TopologyUtils.setTopologyResponse(false, mqStatus.getStatusCode(), mqStatus.getStatusMsg());
			}
		}

		// 第三步： 创建mq拓扑结构
		StatusEnum mqInfo = createMQTopology(request);
		if (mqInfo.getStatusCode() != StatusEnum.MQStatus_005.getStatusCode()) {
			TopologyUtils.write2file(request,TopologyStatusEnum.STATUS_1.getStatusCode(),filePath);
			return TopologyUtils.setTopologyResponse(false, mqInfo.getStatusCode(), mqInfo.getStatusMsg());
		}

		// 第四步： 创建es索引信息
		if (request.isCheckFields()) {
			StatusEnum indexInfo = createIndexInfo(indexPojo);
			if (indexInfo.getStatusCode() != StatusEnum.ESStatus_005.getStatusCode()) {
				TopologyUtils.write2file(request,TopologyStatusEnum.STATUS_2.getStatusCode(),filePath);
				return TopologyUtils.setTopologyResponse(false, indexInfo.getStatusCode(), indexInfo.getStatusMsg());
			}
		}

		// 第五步： 启动线程监听mq消息
		if (!createListenerThread(request)) {
			TopologyUtils.write2file(request, TopologyStatusEnum.STATUS_3.getStatusCode(),filePath);
			return TopologyUtils.setTopologyResponse(false, StatusEnum.LinstenerStatus_000.getStatusCode(),
					StatusEnum.LinstenerStatus_000.getStatusMsg());
		}

		TopologyUtils.write2file(request, TopologyStatusEnum.STATUS_4.getStatusCode(),filePath);
		TopologyResponse topologyResponse = TopologyUtils.setTopologyResponse(true,
				StatusEnum.LinstenerStatus_001.getStatusCode(), StatusEnum.LinstenerStatus_001.getStatusMsg());

		LOGGER.info("function = {}, request = {}, response = {}", "createTopology", request, topologyResponse);

		return topologyResponse;
	}

	private StatusEnum createMQTopology(TopologyRequest request) {
		// 创建
		try {
			return mqHandler.createMQTopology(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(" create mqTopology failed. function={}, input={}, errorMessage={}", "createMQTopology", request,
					e);
			return StatusEnum.MQStatus_006;
		}
	}

	private StatusEnum createIndexInfo(IndexPojo indexPojo) {
		try {
			return esHandler.indexCreateHandler(indexPojo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" create esTopology failed. function={}, input={}, errorMessage={}", "createIndexInfo", indexPojo,
					e);
			return StatusEnum.ESStatus_006;
		}
	}

	private boolean createListenerThread(TopologyRequest request) {
		Map<String, String> msg = new HashMap<String, String>();
		msg.put(MQES.QUEUENAME, request.getQueueName());
		msg.put(MQES.INDEXNAME, request.getIndexName());
		msg.put(MQES.INDEXTYPE, request.getIndexType());
		
		msg.put(MQES.HOST, mqUtil.getMQHost());
		msg.put(MQES.VHOST, request.getVhost());
		msg.put(MQES.PORT, String.valueOf(mqUtil.getMQPort()));
		msg.put(MQES.USERNAME, request.getUserName());
		msg.put(MQES.PASSWORD, DecryptMethod.getDecryptValue(request.getPassword().trim()));
		
		try {
			threadManager.createThread(msg, MQES.THREADNUM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(" create listener thread failed. function={}, input={}, errorMessage={}",
					"createListenerThread", request, e);
			return false;
		}
		return true;
	}

}
