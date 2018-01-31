package com.xinguang.xherald.es.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.es.ESBase;
import com.xinguang.xherald.es.beans.IndexPojo;
import com.xinguang.xherald.es.service.IndexService;
import com.xinguang.xherald.web.enums.StatusEnum;

/**
 * ClassName: ESCheckHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年10月12日 下午12:00:49 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
@Component
public class ESCheckHandler extends ESBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ESCheckHandler.class);

	@Autowired
	private IndexService indexService;

	/**
	 * 
	 * checkESTopology:检查es(index、type和mapping是否存在). <br/>
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
	public StatusEnum checkESTopology(IndexPojo indexPojo) {
		// 获取client
		init();

		// 检查index是否存在
		if (indexService.indexIsExist(getCLIENT(), indexPojo.getIndexName())) {
			// 在index存在的情况下，检查type是否存在
			if (indexService.indexTypeIsExist(getCLIENT(), indexPojo.getIndexName(), indexPojo.getIndexType())) {
				// 在index和type都存在的情况下，检查mapping是否存在
				if (indexService.verifyMapping(getCLIENT(), indexPojo.getIndexName(), indexPojo.getIndexType())) {
					return StatusEnum.ESStatus_003;
				} else {
					return StatusEnum.ESStatus_002;
				}
			} else {
				return StatusEnum.ESStatus_001;
			}
		}else{
			return StatusEnum.ESStatus_007;
		}
	}
}
