package com.dangdang.ddframe.job.lite.console.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 作业执行日志
 * @author yangsh
 *
 */
@Getter
@Setter
public class ExecutionLog {
	
	private String id; // 主键
	private String jobName; // 作业名称
	private String taskId; // 任务名称
	private String hostname; // 主机名称
	private String ip; // IP
	private String shardingItem; // 分片项
	private String executionSource; // 作业执行来源: NORMAL_TRIGGER, MISFIRE, FAILOVER
	private String failureCause; // 执行失败原因
	private String isSuccess; // 是否执行成功
	private Date startTime; // 作业开始执行时间
	private Date completeTime; // 作业结束执行时间
	
}
