/**
 * 
 */
package com.xinguang.esearch.curd;

import org.elasticsearch.client.Client;

/**
 * 
 * ClassName: IndexService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:34:46 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface IndexService {

    /**
     * createIndex:创建索引. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param client es client
     * @param index 索引名称
     * @param type 类型
     * @return 创建索引是否成功
     * @since JDK 1.7
     */
    boolean createIndex(Client client,String index, String type);
    
    /**
     * createIndex:创建索引. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param client es client
     * @param index 索引名称
     * @param type 类型
     * @return 创建索引是否成功
     * @since JDK 1.7
     */
    boolean createAudioIndex(Client client,String index, String type);
	
	
    /**
     * deleteIndex:删除索引. <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param client es cient
     * @param index 索引名称
     * @return 删除索引是否成功
     * @since JDK 1.7
     */
    boolean deleteIndex(Client client,String index);

}
