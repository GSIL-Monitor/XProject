package com.xinguang.esearch.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.beans.PoiPojo;
import com.xinguang.esearch.beans.ScaleCondition;

/**
 * 
 * ClassName: ClientQueryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 下午4:31:40 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface ClientPoiQueryService {
        
    /**
     * 
     * poiQuery:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator-lengxing
     * @param index
     * @param type
     * @param gc
     * @return
     * @since JDK 1.7
     */
    JSONObject poiQuery(String index, String type, GeoCondition gc);
    
    /**
     * 
     * scaleQuery:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator-lengxing
     * @param index
     * @param type
     * @param sc
     * @return
     * @since JDK 1.7
     */
    List<PoiPojo> scaleQuery(String index, String type, ScaleCondition sc);
    
    
}
