/**
 * Project Name:mq
 * File Name:MQType.java
 * Package Name:com.xinguang.mq.enums
 * Date:2017年3月1日上午9:51:33
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.enums;


/**
 * ClassName:MQEnum <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年3月1日 上午9:51:33 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public enum MQEnum {
	Null(-1, "NULL"), RABBITMQ(0, "rabbitmq"), ACTIVEMQ(1, "activemq"), DELAYQUEUE(2, "delayqueue");
	private final int index;
	private final String name;

	private MQEnum(int key, String value) {
		index = key;
		name = value;
	}

	public static MQEnum getEnumByindex(int index) {
		for (MQEnum type : MQEnum.values()) {
			if (type.index == index)
				return type;
		}
		return Null;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}
}

