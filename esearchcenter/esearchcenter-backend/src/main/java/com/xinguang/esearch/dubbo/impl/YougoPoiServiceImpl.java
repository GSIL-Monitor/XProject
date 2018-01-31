/**
 * Project Name:esearch
 * File Name:YougoPoiServiceImpl.java
 * Package Name:com.xinguang.esearch.dubbo.impl
 * Date:2016年9月29日下午5:31:20
 *
*/

package com.xinguang.esearch.dubbo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.constant.CommonConstant;
import com.xinguang.esearch.controller.YougoController;
import com.xinguang.esearch.enums.HttpCodeEnum;
import com.xinguang.esearch.service.ClientPoiQueryService;
import com.xinguang.esearch.utils.JsonUtil;
import com.xinguang.esearch.utils.POIBeansUtil;
import com.xinguang.esearchcenter.inter.YougoPoiService;
import com.xinguang.esearchcenter.request.GeoRequest;

import org.springframework.stereotype.Service;
/**
 * 
 * ClassName: YougoPoiServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年10月19日 上午11:32:09 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
@Service("yougoPoiService")
public class YougoPoiServiceImpl implements YougoPoiService{
    private static final Logger LOGGER = LoggerFactory.getLogger(YougoController.class);
    
    @Autowired
    private ClientPoiQueryService clientPoiQueryService;
    
    @Override
    public String PoiSearch(String index, String type, GeoRequest request) {
        // TODO Auto-generated method stub
        LOGGER.info("function={}, index={}, type={}, request={}", "queryScenicSpots", index, type, request);
        GeoCondition gc = POIBeansUtil.setGeoCondition(request);
        JSONObject queryResult = null;
        if (gc != null) {
            queryResult = clientPoiQueryService.poiQuery(index, type, gc);
        }

        LOGGER.info("function={}, index ={}, type ={}, request ={}, response={}", "queryScenicSpots", index, type, request, queryResult);
        if (queryResult == null || queryResult.getIntValue(CommonConstant.COUNT) == 0) {
            return JsonUtil.getResultJsonStr(HttpCodeEnum.HTTPCODE_204.getStatusCode(),
                    HttpCodeEnum.HTTPCODE_204.getStatusMsg() + ",query result is null ", null);
        } else {
            return JsonUtil.getResultJsonStr(HttpCodeEnum.HTTPCODE_200.getStatusCode(),
                    HttpCodeEnum.HTTPCODE_200.getStatusMsg(), queryResult);
        }
    }

}

