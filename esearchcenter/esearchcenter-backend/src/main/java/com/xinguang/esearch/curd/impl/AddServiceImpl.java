/**
 * 
 */
package com.xinguang.esearch.curd.impl;

import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.xinguang.esearch.curd.AddService;

/**
 * 
 * ClassName: AddServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:46:42 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Repository
public class AddServiceImpl implements AddService {
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AddServiceImpl.class);
    
    @Override
    public String add(Client client,String index, String type, String id, Map<String, Object> doc) {
    	IndexResponse response;
    	try {
    	    response = client.prepareIndex(index, type, id).setSource(doc).get();
    	} catch (Exception e) {
    	    LOGGER.error("add index failed. input={},searchErrorMessage={}",doc,e);
    	    return null;
    	}
    	boolean created = response.isCreated();
    	String esid = response.getId();
    	if (!created) {
    	    LOGGER.error("indexing adding is not created. request_id={},return_id={}", id, esid);
    	}
    	return esid;
    }

}
