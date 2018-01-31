package com.xinguang.xherald.es.service.impl;
/**
 * 
 */


import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinguang.xherald.es.service.AddService;

/**
 * 
 * ClassName: AddServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月5日 下午7:47:34 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Service
public class AddServiceImpl implements AddService {
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AddServiceImpl.class);
    
    @Override
    public String add(Client client,String index, String type, Map<String, Object> doc) {
    	IndexResponse response;
    	try {
    	    response = client.prepareIndex(index, type).setSource(doc).get();
    	} catch (Exception e) {
    	    LOGGER.error("add index failed. input={},searchErrorMessage={}",doc,e);
    	    return null;
    	}
    	boolean created = response.isCreated();
    	String esid = response.getId();
    	if (!created) {
    	    LOGGER.error("indexing adding is not created.return_id={}", esid);
    	}
    	return esid;
    }
    
    @Override
    public boolean upsertBySingle(Client client, String index, String type, String id, Map<String, Object> doc) {
    	IndexRequest indexRequest = new IndexRequest(index, type, id).source(doc);
    
    	UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(doc).upsert(indexRequest);
    
    	UpdateResponse updateResponse = null;
    	try {
    	    updateResponse = client.update(updateRequest).get();
    	    return true;
    	} catch (Exception e) {
            LOGGER.error("update index failed. request id={}, updateRespose={}, exception={}", id, updateResponse.getGetResult(), e);
            return false;
    	}
    }

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.xinguang.xherald.es.service.netease.esearch.es.AddService#addBulk(org.elasticsearch.client.Client, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public Integer addIndexDataBulk(Client client, String index, String type, List<Map<String, Object>> sourceList) {
		// TODO Auto-generated method stub
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Map<String, Object> source: sourceList) {
			bulkRequest.add(client.prepareIndex(index, type).setSource(source));
			//bulkRequest.add(client.prepareIndex(index, type,id).setSource(source)); 
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if(bulkResponse.hasFailures()){//请求失败，异常处理
			System.out.println(bulkResponse.buildFailureMessage());
		}
		return bulkRequest.numberOfActions();
	}

}
