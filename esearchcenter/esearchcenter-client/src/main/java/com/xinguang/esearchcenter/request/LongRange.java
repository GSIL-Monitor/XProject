/**
 * Project Name:esearchcenter-client
 * File Name:LongRange.java
 * Package Name:com.xinguang.esearchcenter.request
 * Date:2016年11月17日下午4:20:02
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearchcenter.request;

import java.io.Serializable;

import com.xinguang.esearchcenter.enums.RangeTypeEnum;

/**
 * ClassName:LongRange <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月17日 下午4:20:02 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class LongRange implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	
	private RangeTypeEnum type;
	private long min;
	private long max;
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public LongRange(long min, long max) {
		super();
		this.min = min;
		this.max = max;
	}

	public LongRange() {
		super();
	}
	public RangeTypeEnum getType() {
		return type;
	}
	public void setType(RangeTypeEnum type) {
		this.type = type;
	}
}

