package com.dangdang.ddframe.job.lite.console.mapper;

import java.util.List;
import java.util.Map;

public interface ExecutionLogMapper {

	/** 获取总记录数 */
	Integer getTotalRecord(Map<String, Object> param);

	List<Map<String, Object>> getList(Map<String, Object> param);
	
}
