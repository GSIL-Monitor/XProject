/**
 * Project Name:esearchcenter-backend
 * File Name:IndexBean.java
 * Package Name:com.xinguang.esearch.beans
 * Date:2016年11月16日上午11:18:54
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.beans;

/**
 * ClassName:IndexBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月16日 上午11:18:54 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class IndexBean extends BaseBean{
	private String indexName;
	private String typeName;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public IndexBean(String indexName, String typeName) {
		super();
		this.indexName = indexName.trim();
		this.typeName = typeName.trim();
	}

}
