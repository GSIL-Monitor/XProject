package com.xinguang.xherald.web.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinguang.xherald.common.utils.JsonPojoUtil;
import com.xinguang.xherald.es.beans.IndexFields;
import com.xinguang.xherald.es.beans.IndexPojo;
import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.web.enums.TopologyStatusEnum;
import com.xinguang.xherald.web.file.WriteFile;
import com.xinguang.xherald.web.request.TopologyRequest;
import com.xinguang.xherald.web.response.TopologyResponse;

/**
 * 
 * ClassName: CheckUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年10月12日 上午11:08:46 <br/>
 *
 * @author Administrator-lengxing
 * @version
 * @since JDK 1.7
 */

public class TopologyUtils {

	/**
	 * setTopologyResponse:创建返回对象. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param flag
	 * @param code
	 * @param message
	 * @return
	 * @since JDK 1.7
	 */
	public static TopologyResponse setTopologyResponse(boolean flag, int code, String message) {
		return new TopologyResponse(flag, code, message);
	}

	/**
	 * 
	 * setIndex:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public static IndexPojo setIndex(TopologyRequest request) throws Exception {
		IndexPojo index = new IndexPojo();
		index.setIndexName(request.getIndexName());
		index.setIndexType(request.getIndexType());
		index.setShards(request.getShards());
		index.setReplicas(request.getReplicas());

		JSONArray jsonArray = JSONObject.parseArray(request.getFields());
		List<IndexFields> indexFieldsList = new ArrayList<IndexFields>();

		for (Object obj : jsonArray) {
			IndexFields indexFields = JsonPojoUtil.json2Obj(JSON.toJSONString(obj), IndexFields.class);
			indexFieldsList.add(indexFields);
		}
		index.setIndexFields(indexFieldsList);
		return index;
	}

	/**
	 * write2file:记录状态. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @param status
	 * @since JDK 1.7
	 */
	public static void write2file(TopologyRequest request, int status, String filePath) {
		// 登录名
		String userName = SecurityUtil.getCurrentUsername();

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(request.getExchangeName()).append(MQES.CONNECTOR).append(request.getRoutingKey())
				.append(MQES.CONNECTOR).append(request.getQueueName()).append(MQES.CONNECTOR)
				.append(request.getIndexName()).append(MQES.CONNECTOR).append(request.getIndexType());
		String fileName = stringBuffer.toString();

		StringBuffer stringBuffer2 = new StringBuffer();

		// 此处的用户(username)是指登录rabitmq集群的用户名
		stringBuffer2.append(request.getVhost()).append(MQES.CONNECTOR).append(request.getUserName())
				.append(MQES.CONNECTOR).append(request.getPassword());

		// 此处的用户(username)指的是登录xherald的用户名
		String content = stringBuffer.append(MQES.TAB).append(userName).append(MQES.TAB)
				.append(stringBuffer2.toString()).append(MQES.TAB)
				.append(DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date())).append(MQES.TAB).append(status)
				.append(MQES.TAB).append(TopologyStatusEnum.getStatusMsg(status)).toString();
		String path = null;
		if (status == TopologyStatusEnum.STATUS_0.getStatusCode()) {
			path = new StringBuffer().append(filePath).append(MQES.MQ_INITIAL_PATH).append("/")
					.append(fileName).append(MQES.TXT).toString();
		} else if (status == TopologyStatusEnum.STATUS_4.getStatusCode()) {
			path = new StringBuffer().append(filePath).append(MQES.MQ_OK_PATH).append("/")
					.append(MQES.MQ_OK_FILE).append(MQES.TXT).toString();
		} else {
			path = new StringBuffer().append(filePath).append(MQES.MQ_ING_PATH).append("/").append(fileName)
					.append(MQES.TXT).toString();
		}
		WriteFile.write2File(path, content);
	}
}