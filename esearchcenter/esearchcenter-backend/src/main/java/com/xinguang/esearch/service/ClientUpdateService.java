/**
 * 
 */
package com.xinguang.esearch.service;

import java.util.Map;

/**
 * 
 * ClassName: ClientUpdateService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午3:22:59 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface ClientUpdateService {
	
	/**
	 * update:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param index 索引
	 * @param type 文档类型
	 * @param id 文档id
	 * @param doc 文档更新内容
	 * @return 更新的esid
	 * @since JDK 1.7
	 */
    String update(String index, String type, String id, Map<String, Object> doc);
	
	/**
	 * upsert:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param index 索引
	 * @param type 文档类型
	 * @param id 文档id
	 * @param doc 文档更新内容
	 * @return 更新的esid 
	 * @since JDK 1.7
	 */
    String upsert(String index, String type, String id, Map<String, Object> doc);

}
