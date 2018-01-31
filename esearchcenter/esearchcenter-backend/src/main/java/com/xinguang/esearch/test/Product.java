/**
 * Project Name:esearchcenter-backend
 * File Name:Product.java
 * Package Name:com.xinguang.esearch.test
 * Date:2016年11月10日上午10:04:13
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.test;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * ClassName:Product <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月10日 上午10:04:13 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class Product {

	private String descHighlight;
	private Map<String, Object> data;
	public String getDescHighlight() {
		return descHighlight;
	}
	public void setDescHighlight(String descHighlight) {
		this.descHighlight = descHighlight;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	
}
