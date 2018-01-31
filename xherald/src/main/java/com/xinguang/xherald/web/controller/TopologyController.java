package com.xinguang.xherald.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinguang.xherald.common.utils.JsonPojoUtil;
import com.xinguang.xherald.mq.constant.MQES;
import com.xinguang.xherald.web.file.ReadFile;
import com.xinguang.xherald.web.handler.TopologyHandler;
import com.xinguang.xherald.web.request.TopologyRequest;
import com.xinguang.xherald.web.response.TopologyResponse;
import com.xinguang.xherald.web.utils.SecurityUtil;

@Controller
@RequestMapping("/mqesTopology")
public class TopologyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopologyController.class);
	
	@Autowired
	private TopologyHandler TopologyHandler;
	
	@Autowired
	private ReadFile readFile;
	
	@Value("${filePath}")
    private String filePath;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public TopologyResponse createTopology(@RequestBody TopologyRequest request){
		return TopologyHandler.createTopology(request, filePath);
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public String queryTopology(HttpServletRequest request){
		String result = null;
		LOGGER.info("function = {}, request = {}", "queryTopology", request);
		String userName = SecurityUtil.getCurrentUsername();
		// 登录名
		if(userName != null && !StringUtils.isBlank(userName)){
			String path = new StringBuffer().append(filePath).append(MQES.MQ_OK_PATH).append("/")
					.append(MQES.MQ_OK_FILE).append(MQES.TXT).toString();
			List<String> resultList = readFile.getConnections(path,userName);
			try {
				result = JsonPojoUtil.obj2Json(resultList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error(" The data format error. function={}, input={}, errorMessage={}", "queryTopology", request, e);
			}
		}
		LOGGER.info("function = {}, request = {}, response = {}", "queryTopology", request, result);
        return result;
	}
	
}
