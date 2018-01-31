package com.xinguang.xherald.web.request;

import com.xinguang.xherald.common.beans.BaseBean;

/**
 * ClassName:TopologyRequest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月25日 上午10:53:34 <br/>
 * @author   hzlengxing
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TopologyRequest extends BaseBean{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	//用户信息
	private String userName;
	private String password;
	private String vhost;
	
	//拓扑信息
	private String exchangeName;
	private String queueName;
	private String routingKey;
	private String indexName;
	private String indexType;
	private int shards;
	private int replicas;
	private String fields;
	private boolean checkFields;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVhost() {
		return vhost;
	}
	public void setVhost(String vhost) {
		this.vhost = vhost;
	}
	public String getExchangeName() {
		return exchangeName;
	}
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getIndexType() {
		return indexType;
	}
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
	public int getShards() {
		return shards;
	}
	public void setShards(int shards) {
		this.shards = shards;
	}
	public int getReplicas() {
		return replicas;
	}
	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public boolean isCheckFields() {
		return checkFields;
	}
	public void setCheckFields(boolean checkFields) {
		this.checkFields = checkFields;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
}

