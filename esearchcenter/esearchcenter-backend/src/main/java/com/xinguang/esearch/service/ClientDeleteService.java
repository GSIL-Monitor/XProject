/**
 * 
 */
package com.xinguang.esearch.service;


/**
 * 
 * ClassName: ClientDeleteService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午3:19:55 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface ClientDeleteService {
	
	/**
	 * delete:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param index 索引
	 * @param type 类型
	 * @param id 文档id
	 * @return _id
	 * @since JDK 1.7
	 */
    String delete(String index, String type, String id);

}
