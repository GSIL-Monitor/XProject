package com.xinguang.xherald.es.service;
/**
 * 
 */


import java.util.List;
import java.util.Map;

import org.elasticsearch.client.Client;

/**
 * 
 * ClassName: AddService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年6月17日 下午2:46:00 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface AddService {
    
    /**
     * add:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param client client
     * @param index 索引名称，一般为一个产品一个索引 
     * @param type 类似数据库的一张表
     * @param id 成功则返回文档的id，否则为null
     * @param doc 要索引的文档
     * @return 生成的id
     * @since JDK 1.7
     */
    String add(Client client,String index, String type, Map<String, Object> doc);
    
    boolean upsertBySingle(Client client, String index, String type, String id, Map<String, Object> doc); 
    
    Integer addIndexDataBulk(Client client, String index, String type, List<Map<String, Object>> sourceList);

}
