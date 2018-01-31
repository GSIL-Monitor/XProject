/**
 * 
 */
package com.xinguang.esearch.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinguang.esearch.curd.AddService;
import com.xinguang.esearch.service.ClientAddService;

/**
 * 
 * ClassName: ClientAddServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月6日 下午9:55:24 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Service
public class ClientAddServiceImpl extends ESUtil implements ClientAddService{

    /**
     * addService:es新增索引服务(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    @Autowired
	private AddService addService;
	
    /**
     * 
     * TODO 简单描述该方法的实现功能（可选）.
     * @see com.xinguang.esearch.service.ClientAddService#add(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
	public String add(String index, String type, String id, Map<String, Object> doc) {
		// 获取client
        init();
		
		//新增
        String addResult = addService.add(getCLIENT(), index, type, id, doc);
		
		//关闭连接
		//close();
		
        return addResult;
    }
	
}
