/**
 * Project Name:mq
 * File Name:RabbitExchangeType.java
 * Package Name:com.xinguang.mq.enums
 * Date:2017年3月1日上午9:43:37
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.enums;


/**
 * ClassName:RabbitExchangeEnum <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年3月1日 上午9:43:37 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public enum RabbitExchangeTypeEnum {
	Null(-1, "NULL"), Direct(0, "direct"), Fanout(1, "fanout"), Topic(2, "topic");

	private final int index;
	private final String name;

	private RabbitExchangeTypeEnum(int key, String value) {
		index = key;
		name = value;
	}

	public static RabbitExchangeTypeEnum getEnumByindex(int index) {
		for (RabbitExchangeTypeEnum type : RabbitExchangeTypeEnum.values()) {
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

