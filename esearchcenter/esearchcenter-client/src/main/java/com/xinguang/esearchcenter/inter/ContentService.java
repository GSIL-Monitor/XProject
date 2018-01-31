/**
 * Project Name:esearchcenter-client
 * File Name:ContentService.java
 * Package Name:com.xinguang.esearchcenter.inter
 * Date:2016年11月9日下午7:04:17
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearchcenter.inter;

import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearchcenter.request.PageRequest;
import com.xinguang.esearchcenter.request.TopRequest;

/**
 * ClassName:ContentService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年11月9日 下午7:04:17 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public interface ContentService {
	
	/**
	 * pageSearch:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	JSONObject pageSearch(PageRequest request);
	
	/**
	 * topSearch:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	JSONObject topSearch(TopRequest request);
}
