/**
 * 
 */
package com.xinguang.esearch.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinguang.esearch.curd.UpdateService;
import com.xinguang.esearch.service.ClientUpdateService;

/**
 * 更新索引
 * ClassName: ClientUpdateServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午3:28:25 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Service
public class ClientUpdateServiceImpl extends ESUtil implements ClientUpdateService {

	/**
	 * updateService:更新索引服务(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
    @Autowired
	private UpdateService updateService;

    @Override
	public String update(String index, String type, String id, Map<String, Object> doc) {
		// 获取client
        init();
        
        //更新
        String updatResult = updateService.update(getCLIENT(), index, type, id, doc);
        
        // 关闭client
        //close();
        
        return updatResult;
    }

    @Override
	public String upsert(String index, String type, String id, Map<String, Object> doc) {
		// 获取client
        init();
        
        //更新
        String updatResult = updateService.upsert(getCLIENT(), index, type, id, doc);
        
        // 关闭client
        //close();
        
        return updatResult;
    }

}
