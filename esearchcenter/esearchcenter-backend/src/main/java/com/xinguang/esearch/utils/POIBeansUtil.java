/**
 * Project Name:esearch
 * File Name:POIBeansUtil.java
 * Package Name:com.netease.esearch.utils
 * Date:2016年8月30日下午7:02:10
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.utils;

import org.elasticsearch.common.geo.GeoDistance;

import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.beans.ScaleCondition;
import com.xinguang.esearch.enums.OptimizeBboxEnum;
import com.xinguang.esearch.request.ScaleRequest;
import com.xinguang.esearchcenter.request.GeoRequest;

/**
 * ClassName:POIBeansUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月30日 下午7:02:10 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class POIBeansUtil {
    
    public static GeoCondition setGeoCondition(GeoRequest gr){
        if(gr == null){
            return null;
        }
        GeoCondition gc = new GeoCondition();
        
        if(gr.getSourceType() != null && gr.getSourceType()>0){
            gc.setSourceType(gr.getSourceType());
        }
        
        gc.setGeoDistance(GeoDistance.ARC);
        gc.setStartDistance("0km");
        gc.setEndDistance("10000km");
        gc.setOptimizeBbox(OptimizeBboxEnum.memory.GetDes());
        gc.setLatitude(gr.getLatitude());
        gc.setLongitude(gr.getLongitude());
        gc.setSize(gr.getSize());
        gc.setOffset(gr.getOffset());
        gc.setScenicRegionId(gr.getScenicRegionId());
        return gc;
    }
    
    public static ScaleCondition setScaleCondition(ScaleRequest sr){
        if(sr == null){
            return null;
        }
        ScaleCondition sc = new ScaleCondition();
        sc.setGeoDistance(GeoDistance.ARC);
        sc.setStartDistance("0km");
        sc.setEndDistance(sr.getRadius()+"km");
        sc.setOptimizeBbox(OptimizeBboxEnum.memory.GetDes());
        sc.setLatitude(sr.getLatitude());
        sc.setLongitude(sr.getLongitude());
        sc.setScale(sr.getScale());
        return sc;
    }

}

