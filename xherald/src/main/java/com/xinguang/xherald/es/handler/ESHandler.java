/**
 * Project Name:es-syncdata
 * File Name:ESHandler.java
 * Package Name:com.netease.esearch.es
 * Date:2016年6月17日下午3:30:01
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.xherald.es.handler;

import org.elasticsearch.cluster.metadata.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.es.ESBase;
import com.xinguang.xherald.es.beans.IndexPojo;
import com.xinguang.xherald.es.service.IndexService;
import com.xinguang.xherald.web.enums.StatusEnum;

/**
 * ClassName:ESHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年6月17日 下午3:30:01 <br/>
 * 
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 * @see
 */
@Component
public class ESHandler extends ESBase {
	@Autowired
	private IndexService indexService;

	/**
	 * indexCreateHandler:创建es索引mapping. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param indexPojo
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public StatusEnum indexCreateHandler(IndexPojo indexPojo) throws Exception{
		//步骤一： 获取client
		init();

		//步骤二：新增(创建索引、设置mapping和setting)
		boolean indexFlag = indexService.createIndex(getCLIENT(), indexPojo);

		if (!indexFlag) {
			return StatusEnum.ESStatus_004;
		} else {
			return StatusEnum.ESStatus_005;
		}
	}

	public String[] indexQueryHandler() {
		// 获取client
		init();

		// 新增
		MetaData metaData = indexService.getMetaData(getCLIENT());
		metaData.concreteAllIndices();
		return metaData.concreteAllIndices();
	}

}
