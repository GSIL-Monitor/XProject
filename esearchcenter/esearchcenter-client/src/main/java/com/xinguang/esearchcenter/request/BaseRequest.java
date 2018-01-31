/**
 * Project Name:esearchcenter-client
 * File Name:BaseRequest.java
 * Package Name:com.xinguang.esearchcenter.request
 * Date:2016年11月11日下午2:19:55
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearchcenter.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xinguang.esearchcenter.enums.SortOrderEnum;

/**
 * ClassName:BaseRequest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月11日 下午2:19:55 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class BaseRequest implements Serializable{
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	// 设置查询内容（string:模糊查询内容；String[]：查询辐射的字段）
	private String queryContent;
	private String[] queryFields;
	
	/**
	 * 设置查询条件：
	 * 1）list元素之间是and的关系；
	 * 2）list每个元素（map）内部是or的关系
	 */
	private List<Map<String, Object>> termConditions;
	
	//设置排序信息
    private Map<String, SortOrderEnum> sortFields;
    
    //高亮显示信息（字段及截取长度，-1表示不需要截取）
  	private Map<String, Integer> LighLightInfo;
  	
  	//设置限制条件（限制查询范围内的数据）
  	private Map<String,LongRange> rangeCondition;

	public String getQueryContent() {
		return queryContent;
	}

	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}

	public String[] getQueryFields() {
		return queryFields;
	}

	public void setQueryFields(String[] queryFields) {
		this.queryFields = queryFields;
	}

	public Map<String, Integer> getLighLightInfo() {
		return LighLightInfo;
	}

	public void setLighLightInfo(Map<String, Integer> lighLightInfo) {
		LighLightInfo = lighLightInfo;
	}

	public Map<String, SortOrderEnum> getSortFields() {
		return sortFields;
	}

	public void setSortFields(Map<String, SortOrderEnum> sortFields) {
		this.sortFields = sortFields;
	}

	public List<Map<String, Object>> getTermConditions() {
		return termConditions;
	}

	public void setTermConditions(List<Map<String, Object>> termConditions) {
		this.termConditions = termConditions;
	}

	public Map<String, LongRange> getRangeCondition() {
		return rangeCondition;
	}

	public void setRangeCondition(Map<String, LongRange> rangeCondition) {
		this.rangeCondition = rangeCondition;
	}
}
