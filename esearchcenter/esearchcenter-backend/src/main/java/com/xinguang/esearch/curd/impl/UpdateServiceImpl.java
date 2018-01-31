/**
 * 
 */
package com.xinguang.esearch.curd.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.xinguang.esearch.curd.UpdateService;

/**
 * ClassName: UpdateServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月6日 下午9:50:14 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Repository
public class UpdateServiceImpl implements UpdateService {
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateServiceImpl.class);
    
    @Override
    public String update(Client client, String index, String type, String id, Map<String, Object> doc) {
    	UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(doc);
    	UpdateResponse updateResponse = null;
    	try {
    	    updateResponse = client.update(updateRequest).get();
        } catch (Exception e) {
            if (updateResponse == null || StringUtils.isBlank(updateResponse.getId())) {
                LOGGER.error("update index failed. request id={}, exception={}", id, e);
                return null;
            }
            LOGGER.error("update index failed. request id={}, updateRespose={}, exception={}", id, updateResponse.getGetResult(), e);
    	}
    	return updateResponse.getId();
    }
    
    @Override
    public String upsert(Client client, String index, String type, String id, Map<String, Object> doc) {
    	IndexRequest indexRequest = new IndexRequest(index, type, id).source(doc);
    
    	UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(doc).upsert(indexRequest);
    
    	UpdateResponse updateResponse = null;
    	try {
    	    updateResponse = client.update(updateRequest).get();
    	} catch (Exception e) {
            if (updateResponse == null || StringUtils.isBlank(updateResponse.getId())) {
                LOGGER.error("update index failed. request id={}, exception={}", id, e);
            	return null;
            }
            LOGGER.error("update index failed. request id={}, updateRespose={}, exception={}", id, updateResponse.getGetResult(), e);
    	}
    
    	return updateResponse.getId();
    }
    
    
    /**
     * 转换成json对象 (暂未使用)
     * 
     * @param map 入参
     * @return 查询json对象
     */
    private String generateJson(Map<String, Object> map) {
    	String json = null;
    
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
        	
            for (Map.Entry<String, Object> entry : map.entrySet()) {
            	//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            	contentBuilder.field(entry.getKey(),entry.getValue());
            }
            json = contentBuilder.endObject().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return json;
    }

}
