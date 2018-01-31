/**
 * Project Name:esearch
 * File Name:PoiPojo.java
 * Package Name:com.netease.esearch.beans
 * Date:2016年9月14日上午10:38:49
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.beans;

import java.util.Map;

/**
 * ClassName:PoiPojo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年9月14日 上午10:38:49 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class PoiPojo extends BaseBean{
    
    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final long serialVersionUID = -502960946971258060L;

    //当前点距离POI的距离
    private String distance;
    
    //当前点本身的属性
    private Map<String,Object> data;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
}

