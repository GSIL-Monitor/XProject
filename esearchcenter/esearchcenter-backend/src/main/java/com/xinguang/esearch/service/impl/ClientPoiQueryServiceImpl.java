/**
 * 
 */
package com.xinguang.esearch.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.beans.PoiPojo;
import com.xinguang.esearch.beans.ScaleCondition;
import com.xinguang.esearch.constant.CommonConstant;
import com.xinguang.esearch.curd.PoiQueryService;
import com.xinguang.esearch.service.ClientPoiQueryService;

/**
 * 
 * ClassName: ClientPOIQueryServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月12日 上午10:29:46 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
@Service
public class ClientPoiQueryServiceImpl extends ESUtil implements ClientPoiQueryService {
    /**
     * logger:TODO(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPoiQueryServiceImpl.class);

    @Autowired
    private PoiQueryService poiQueryService;
    
    @Override
    public JSONObject poiQuery(String index, String type, GeoCondition gc){
        // TODO Auto-generated method stub
        try {
            init();
            
            long totalCount = poiQueryService.getTotalCount(getCLIENT(), index, type, gc);
            
            List<PoiPojo> resList = null;
            if(totalCount > gc.getOffset()){
                resList = poiQueryService.getNearByPOI(getCLIENT(), index, type, gc);
            }
            
            if (resList != null && resList.size() > 0) {
                JSONObject contentJson = new JSONObject();
                contentJson.put(CommonConstant.COUNT, totalCount);
                contentJson.put(CommonConstant.LIST, resList);
                if (totalCount > gc.getOffset() + gc.getSize()) {
                    contentJson.put(CommonConstant.HASNEXT, true);
                } else {
                    contentJson.put(CommonConstant.HASNEXT, false);
                }
                return contentJson;
            } else {
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error("The network is busy, please try again later. function={}, input={}, searchErrorMessage={}", "poiQuery",
                    gc, e);
            return null;
            
        }
    }
    
    @Override
    public List<PoiPojo> scaleQuery(String index, String type, ScaleCondition sc) {
        // TODO Auto-generated method stub
        try {
            init();
            
            return poiQueryService.getNearByScale(getCLIENT(), index, type, sc);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error("The network is busy, please try again later. function={}, input={}, searchErrorMessage={}", "scaleQuery",
                    sc, e);
            return null;
            
        }
    }

}
