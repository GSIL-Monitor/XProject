package com.xinguang.mq.beans;

import java.io.Serializable;

/** * 消息 * */
public class ActiveMessage extends MQMessage implements Serializable {
	private static final long serialVersionUID = -6487839157908352120L;
	
	private String destName;
	private int destType;


	public ActiveMessage(String destName, int destType, String msgBody) {
		super(msgBody);
		this.destName = destName;
		this.destType = destType;
	}

	public String getDestName() {
		return destName;
	}

	public int getDestType() {
		return destType;
	}

}