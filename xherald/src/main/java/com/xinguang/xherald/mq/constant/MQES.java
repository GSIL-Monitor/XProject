package com.xinguang.xherald.mq.constant;

import com.xinguang.xherald.common.beans.BaseBean;


/**
 * ClassName: MQES <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月14日 下午2:51:27 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
public class MQES extends BaseBean{
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 5648083985275299295L;

	public static final String EXCHANGENAME = "exchangeName";
	
	public static final String QUEUENAME = "queueName";

	public static final String INDEXNAME= "indexName";

	public static final String INDEXTYPE = "indexType";
	
	public static final String ROUTINGKEY = "routingKey";
	
	public static final String HOST = "host";
	public static final String VHOST = "vhost";
	public static final String PORT = "port";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	public static final String SHARDS = "shards";
	
	public static final String REPLICAS = "replicas";
	
	public static final String FIELDS = "fields";
	
	public static final String CONNECTOR = "-";
	
	public static final String TAB = "\t";
	
	public static final String MQ_OK_PATH = "ok";
	
	public static final String MQ_ING_PATH = "ing";
	
	public static final String MQ_INITIAL_PATH = "initial";
	
	public static final String MQ_OK_FILE = "listener_thread";
	
	public static final String TXT = ".txt";
	
	public static final int THREADNUM = 1;
	
	public static final String ADD = "add";
	
	public static final String UPDATE = "update";
	
	public static final String DELETE = "delete";
	
	public static final String OBJECT = "object";
	
}
