package com.xinguang.xherald.mq.enums;

public enum MQActionEnum {
	/*参数校验错误*/
	
	//处理成功
	ACCEPT,		 
	
	//可以重试的错误
	RETRY, 	
	
	//无需重试的错误
	REJECT;
}