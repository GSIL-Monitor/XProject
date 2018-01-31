/**
 * Project Name:esearchcenter-client
 * File Name:PageRequest.java
 * Package Name:com.xinguang.esearchcenter.request
 * Date:2016年11月9日下午7:23:51
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearchcenter.request;

import java.io.Serializable;

/**
 * 
 * ClassName: PageRequest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年11月11日 下午2:10:53 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
public class PageRequest  implements Serializable{
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	private BaseRequest baseRequest;

	//设置分页信息
    private int size;
    
    private int offset;

	public BaseRequest getBaseRequest() {
		return baseRequest;
	}

	public void setBaseRequest(BaseRequest baseRequest) {
		this.baseRequest = baseRequest;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}

