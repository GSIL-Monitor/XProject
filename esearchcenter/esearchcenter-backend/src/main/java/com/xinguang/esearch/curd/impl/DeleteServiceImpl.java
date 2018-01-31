/**
 * 
 */
package com.xinguang.esearch.curd.impl;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.xinguang.esearch.curd.DeleteService;

/**
 * 
 * ClassName: DeleteServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:49:35 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Repository
public class DeleteServiceImpl implements DeleteService {
	
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteServiceImpl.class);
    
    @Override
    public String delete(Client client,String index, String type, String id) {
    	DeleteResponse response;
    	try {
    	    response = client.prepareDelete(index, type, id).get();
    	} catch (Exception e) {
    	    LOGGER.error("add index failed. input={},searchErrorMessage={}",id,e);
    	    return null;
    	}
    	if (!response.isFound()) {
    	    LOGGER.error("delete failed. no id found.id={}", id);
    	}
    	return response.getId();
    }


}
