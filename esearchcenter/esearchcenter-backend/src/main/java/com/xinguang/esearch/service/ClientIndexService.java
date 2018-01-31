/**
 * 
 */
package com.xinguang.esearch.service;


/**
 * 
 * ClassName: ClientIndexService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午3:20:47 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface ClientIndexService {
	
	/**
	 * createIndex:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param index 索引
	 * @param type 文档类型
	 * @return 创建索引是否成功
	 * @since JDK 1.7
	 */
    boolean createIndex(String index, String type) ;

	/**
	 * deleteIndex:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param index 索引
	 * @return 删除索引是否成功
	 * @since JDK 1.7
	 */
    boolean deleteIndex(String index);
}
