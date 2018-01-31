/**
 * 
 */
package com.xinguang.esearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinguang.esearch.curd.DeleteService;
import com.xinguang.esearch.service.ClientDeleteService;

/**
 * 
 * ClassName: ClientDeleteServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午3:27:16 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Service
public class ClientDeleteServiceImpl extends ESUtil implements ClientDeleteService {

	/**
	 * deleteService:索引删除服务(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
    @Autowired
	private DeleteService deleteService;

    @Override
	public String delete(String index, String type, String id) {
		// 获取client
        init();
        
        // 新增
        String delResult = deleteService.delete(getCLIENT(), index, type, id);
        
        // 关闭连接
        // close();
        
    	return delResult;
    }

}
