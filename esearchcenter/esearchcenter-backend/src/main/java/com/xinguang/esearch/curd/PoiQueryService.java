/**
 * 
 */
package com.xinguang.esearch.curd;

import java.util.List;

import org.elasticsearch.client.Client;

import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.beans.PoiPojo;
import com.xinguang.esearch.beans.ScaleCondition;

/**
 * 
 * ClassName: POIQueryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月12日 上午10:22:42 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
public interface PoiQueryService {
    
    /**
     * 
     * getNearByPOI:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator-lengxing
     * @param client
     * @param index
     * @param type
     * @param gc
     * @return
     * @since JDK 1.7
     */
    List<PoiPojo> getNearByPOI(Client client, String index, String type, GeoCondition gc);
    
    List<PoiPojo> getNearByScale(Client client, String index, String type, ScaleCondition sc);
    
    /**
     * 
     * getTotalCount:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator-lengxing
     * @param client
     * @param index
     * @param type
     * @param gc
     * @return
     * @since JDK 1.7
     */
    long getTotalCount(Client client,String index, String type, GeoCondition gc);
    

}
