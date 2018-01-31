/**
 * Project Name:es-syncdata
 * File Name:ESHandler.java
 * Package Name:com.netease.esearch.es
 * Date:2016年6月17日下午3:30:01
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.xherald.es;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.es.service.AddService;
import com.xinguang.xherald.es.service.IndexService;
import com.xinguang.xherald.mq.enums.MessageExpEnum;


/**
 * ClassName:ESHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年6月17日 下午3:30:01 <br/>
 * @author   hzlengxing
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Component
public class ESService extends ESBase{

	@Autowired
	private AddService addService;
	
	@Autowired
	private IndexService indexService;
	
	public void indexDataBulk(String index, String type,List<Map<String, Object>> sourceList){
		// 获取client
	    init();
		
		//批量新增
	    Integer addNum = addService.addIndexDataBulk(getCLIENT(), index, type, sourceList);
	}
	
	public void add(String index, String type,Map<String, Object> map){
		// 获取client
	    init();
	    
	    //批量新增
	    String resStr = addService.add(getCLIENT(), index, type, map);
	}
	
	/**
	 * 单个upsert
	 * singleUpsert:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param index
	 * @param type
	 * @param id
	 * @param map
	 * @return
	 * @since JDK 1.7
	 */
	public boolean singleUpsert(String index, String type, String id, Map<String, Object> map) throws Exception{
	    try {
	    	boolean resultFlag = false;
	    	
	    	// 获取client
			init();
			
			resultFlag = addService.upsertBySingle(getCLIENT(), index, type, id, map);
			
			return resultFlag;
			
		} catch (Exception e) {
			throw new Exception(String.valueOf(MessageExpEnum.Mes_2.getStatusCode()),e);
		}
	}
	
	
	public boolean deleteDoc(String index, String type, String id) throws Exception{
	    try {
	    	boolean resultFlag = false;
	    	
	    	// 获取client
			init();
			
			resultFlag = indexService.deleteIndexDoc(getCLIENT(), index, type, id);
			
			return resultFlag;
			
		} catch (Exception e) {
			throw new RuntimeException(String.valueOf(MessageExpEnum.Mes_2.getStatusCode()),e);
		}
	}
	

			
}

