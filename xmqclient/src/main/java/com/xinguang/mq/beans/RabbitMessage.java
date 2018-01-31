package com.xinguang.mq.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** * 消息 * */
public class RabbitMessage extends MQMessage implements Serializable {

	private static final long serialVersionUID = -6487839157908352120L;
	private String exchange;// 交换器
	private String routingKey;// 路由key
	
	public RabbitMessage(String exchange, String routingKey, String msgBody) {
		super(msgBody);
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	public byte[] getSerialBytes() {
		byte[] res = new byte[0];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			res = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

}