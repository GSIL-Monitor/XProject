/**
 * Project Name:mq
 * File Name:ActiveDestType.java
 * Package Name:com.xinguang.mq.enums
 * Date:2017年3月1日上午9:42:12
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.enums;


/**
 * ClassName:ActiveDestEnum <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年3月1日 上午9:42:12 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public enum ActiveDestEnum {
	Null(-1, "NULL"), TOPIC(0, "topic"), QUEUE(1, "queue");
	
	private final int index;
	private final String name;

	private ActiveDestEnum(int key, String value) {
		index = key;
		name = value;
	}

	public static ActiveDestEnum getEnumByindex(int index) {
		for (ActiveDestEnum type : ActiveDestEnum.values()) {
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

