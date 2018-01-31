/**
 * 
 */
package com.xinguang.xherald.es.service;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MetaData;

import com.xinguang.xherald.es.beans.IndexPojo;


/**
 * 
 * ClassName: IndexService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月11日 下午3:26:23 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface IndexService {

    /**
     * createIndex:创建索引及mapping <br/>
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
    boolean createIndexMapping(Client client,IndexPojo index);

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
    
    /**
     * deleteIndexDoc:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator-lengxing
     * @param client
     * @param indexName
     * @param type
     * @return
     * @since JDK 1.7
     */
    boolean deleteIndexDoc(Client client, String indexName, String type, String id);
    
    /**
     * 
     * getMetaData:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param client
     * @return
     * @since JDK 1.7
     */
     MetaData getMetaData(Client client);
     
     /**
      * 
      * indexisExist:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * TODO(这里描述这个方法的执行流程 – 可选).<br/>
      * TODO(这里描述这个方法的使用方法 – 可选).<br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      *
      * @author hzlengxing
      * @param client
      * @param index
      * @return
      * @since JDK 1.7
      */
     boolean indexIsExist(Client client,String index);
     
     /**
      * 
      * indexisExist:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * TODO(这里描述这个方法的执行流程 – 可选).<br/>
      * TODO(这里描述这个方法的使用方法 – 可选).<br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      *
      * @author hzlengxing
      * @param client
      * @param index
      * @return
      * @since JDK 1.7
      */
     boolean indexTypeIsExist(Client client, String indexName, String type);
     
     /**
      * 
      * verifyMapping:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * TODO(这里描述这个方法的执行流程 – 可选).<br/>
      * TODO(这里描述这个方法的使用方法 – 可选).<br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      *
      * @author hzlengxing
      * @param client
      * @param index
      * @return
      * @since JDK 1.7
      */
     boolean verifyMapping(Client client,String index,String type);
     
     /**
      * 创建索引，设置mapping，设置分片
      * createIndex:(这里用一句话描述这个方法的作用). <br/>
      * TODO(这里描述这个方法适用条件 – 可选).<br/>
      * TODO(这里描述这个方法的执行流程 – 可选).<br/>
      * TODO(这里描述这个方法的使用方法 – 可选).<br/>
      * TODO(这里描述这个方法的注意事项 – 可选).<br/>
      *
      * @author Administrator-lengxing
      * @param client
      * @param index
      * @return
      * @since JDK 1.7
      */
     boolean createIndex(Client client, IndexPojo index);

}
