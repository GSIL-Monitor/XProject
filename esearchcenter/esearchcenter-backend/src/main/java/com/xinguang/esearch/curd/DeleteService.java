/**
 * 
 */
package com.xinguang.esearch.curd;

import org.elasticsearch.client.Client;

/**
 * 
 * ClassName: DeleteService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:33:16 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface DeleteService {
	
	/**
	 * 删除指定id的文档，id为string
	 * @param client es client
	 * @param index 索引
	 * @param type 类型
	 * @param id  要删除的文档的id
	 * @return 删除的id
	 */
    String delete(Client client,String index, String type, String id);

}
