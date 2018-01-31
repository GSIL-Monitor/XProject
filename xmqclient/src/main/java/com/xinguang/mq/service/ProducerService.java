/**
 * Project Name:mqclient
 * File Name:ProducerService.java
 * Package Name:com.xinguang.mq.service
 * Date:2017年3月3日下午2:27:46
 * Copyright (c) 2017, lengxing@xinguangnet.com All Rights Reserved.
 *
*/

package com.xinguang.mq.service;

import com.xinguang.mq.beans.MQMessage;


/**
 * ClassName:ProducerService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年3月3日 下午2:27:46 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public interface ProducerService {
	
	/**
	 * 发送信息
	 * @param T extends MQMessage
	 */
	<T extends MQMessage> void sendMessage(T msg)throws Exception;

}

