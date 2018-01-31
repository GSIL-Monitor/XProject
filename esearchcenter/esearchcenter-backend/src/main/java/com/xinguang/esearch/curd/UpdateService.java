/**
 * 
 */
package com.xinguang.esearch.curd;

import java.util.Map;

import org.elasticsearch.client.Client;

/**
 * 
 * ClassName: UpdateService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:37:33 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface UpdateService {
    
	/**
	 * update:更新文档，当文档不存在将报错，存在则merge. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param client es client
	 * @param index 索引名称
	 * @param type 类型
	 * @param id 文档id
	 * @param doc 更新项
	 * @return 更新文档的id
	 * @since JDK 1.7
	 */
    String update(Client client,String index, String type, String id, Map<String, Object> doc);

	/**
     * upsert:upsert为操作规则为：有该id的文档则更新；不存该id文档则添加该doc（index操作）. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param client es client
     * @param index 索引名称
     * @param type 类型
     * @param id 文档id
     * @param doc 更新项
     * @return 更新文档id
     * @since JDK 1.7
     */
    String upsert(Client client,String index, String type, String id, Map<String, Object> doc); 
}
