package com.xinguang.esearch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.constant.CommonConstant;
import com.xinguang.esearch.enums.HttpCodeEnum;
import com.xinguang.esearch.service.ClientPoiQueryService;
import com.xinguang.esearch.utils.JsonUtil;
import com.xinguang.esearch.utils.POIBeansUtil;
import com.xinguang.esearchcenter.request.GeoRequest;

import io.swagger.annotations.ApiOperation;

//@RestController
//@RequestMapping("/yougo")
public class YougoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YougoController.class);

    @Autowired
    private ClientPoiQueryService clientPoiQueryService;

    @ApiOperation(value = "获取指定经纬度临近景点分页列表", notes = "按所在人经纬度为圆心查询 景区内的景点(按距离排序)")
    @RequestMapping(value = "/{index}/{type}", method = RequestMethod.POST)
    public String queryPoi(@PathVariable("index") String index, @PathVariable("type") String type,
            @RequestBody GeoRequest request) {
        
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

   /* @ApiOperation(value = "获取特定比例尺下指定经纬度临近景点列表", notes = "移动地图时，以当前地图中心点及半径查询接口")
    @RequestMapping(value = "/{index}/{type}/scale", method = RequestMethod.POST)
    public String queryScaleScenicSpots(@PathVariable("index") String index, @PathVariable("type") String type,
            @RequestBody ScaleRequest request) {
        LOGGER.info("function={}, index={}, type={}, request={}", "queryScaleScenicSpots", index, type, request);
        ScaleCondition sc = POIBeansUtil.setScaleCondition(request);
        List<PoiPojo> queryResult = null;
        if (sc != null) {
            queryResult = clientPoiQueryService.scaleQuery(index, type, sc);
        }

        LOGGER.info("function={}, index={}, type={}, request={}, response={}", "queryScaleScenicSpots", index, type, request, queryResult);
        if (queryResult == null || queryResult.size() == 0) {
            return JsonUtil.getResultJsonStr(HttpCodeEnum.HTTPCODE_204.getStatusCode(),
                    HttpCodeEnum.HTTPCODE_204.getStatusMsg() + ",query result is null ", null);
        } else {
            return JsonUtil.getResultJsonStr(HttpCodeEnum.HTTPCODE_200.getStatusCode(),
                    HttpCodeEnum.HTTPCODE_200.getStatusMsg(), queryResult);
        }
    }*/

}