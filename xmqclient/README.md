 XMQClient

项目作用： 封装现有的MQ(rabbitmq/activemq)client的调用细节，使得开发者在使用mq时不必关注mq具体的细节，配置相应的参数，调用统一的接口即可。

# #######use Examples #
## A) pom文件中添加 ##
### repository ###

<repository>

    <id>snapshots</id>
    <url>http://120.26.91.179:8081/nexus/content/repositories/snapshots/</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
### dependency ###
<dependency>

	<groupId>com.xinguang</groupId>
	<artifactId>mqclient</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>

## B)spring配置文件引入 ##
### xmqclient.xml引入 ###
    <import resource="classpath*:xmqclient.xml" />
### mqclient.properties引入 ###
	<bean
		class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="order" value="1"/>   
		<property name="locations">
			<list>
				<value>classpath:mqclient.properties</value>
			</list>
		</property>
	</bean>
#### 其中mqclient.properties中根据使用情况需配置的模板如下： ####
#-------------------------------------------------------------------------------
#------------------rabbit----------------
rabbitmq.ip=
rabbitmq.port=

##producer paramList(list的各个连接以'逗号'分隔，每个连接的各个参数以'|'分隔)格式如下：
##username|password|virtualHost|exchange|exchangeType|queue|routingkey,username|password|virtualHost|exchange|exchangeType|queue|routingkey,...
rabbitmq.producer.paramStr=

##consumer paramList(list的各个连接以'逗号'分隔，每个连接的各个参数以'|'分隔)格式如下：
##username|password|virtualHost|queue,username|password|virtualHost|queue,...
rabbitmq.consumer.paramList=

##delay-queue paramList(list的各个连接以'逗号'分隔，每个连接的各个参数以'|'分隔)格式如下：
##username|password|virtualHost|exchange|exchangeType|delayQueue|delayRoutingKey|postExchange|postExchangeType|postQueue|postRoutingKey|perDelayQueueMessageTTL,...
rabbitmq.delayQueue.paramList=


#-----------------activemq----------------
##activemq
activemq.brokerURL=

activemq.username=

activemq.password=

activemq.pool.maxConnections=100

##transaction
activemq.xapool.maxConnections=100

activemq.transaction.queueName=

#-------------------------------------------------------------------------------



# ####### Code Examples #
#生产端
```java
package test.mq;
import java.io.IOException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.xinguang.mq.beans.MQMessage;
import com.xinguang.mq.beans.MQPrdDestination;
import com.xinguang.mq.client.MQConsumerClient;
import com.xinguang.mq.client.MQProducerClient;
import com.xinguang.mq.enums.ActiveDestEnum;
import com.xinguang.mq.enums.MQEnum;


/**
 * 
 * @author Administrator
 *
 */
public class TestMQProducer extends BaseTest{
	
	@Autowired
	private MQProducerClient pc;
	
	@Autowired
	private MQConsumerClient cc;
	
	@Test
	//@Ignore
	public void testProducer() throws IOException {
		
		//rabbit
		/*pc.setMqType(MQEnum.RABBITMQ.getIndex());
		MQMessage mqMsg = new MQMessage("hello667");	
		MQPrdDestination dest = new MQPrdDestination("lx-exchange","lx-queue");*/
		
		//active
		pc.setMqType(MQEnum.ACTIVEMQ.getIndex());
		MQMessage mqMsg = new MQMessage("hello");		
		MQPrdDestination dest = new MQPrdDestination(ActiveDestEnum.QUEUE.getIndex(),"lx-queue");
		
		//delayQueue
		/*pc.setMqType(MQEnum.DELAYQUEUE.getIndex());
		MQMessage mqMsg = new MQMessage("hello");		
		MQPrdDestination dest = new MQPrdDestination("lx-exchange-pre","lx-queue-d");*/
		
		pc.send(dest,mqMsg);

	}
	
}
```



#消费端
```java
package test.mq;
import java.io.IOException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.xinguang.mq.beans.MQCosDestination;
import com.xinguang.mq.client.MQConsumerClient;
import com.xinguang.mq.enums.ActiveDestEnum;
import com.xinguang.mq.enums.MQEnum;
import com.xinguang.mq.service.ConsumerMsgCallBack;


/**
 * 
 * @author Administrator
 *
 */
public class TestMQConsumer extends BaseTest{
	
	@Autowired
	private MQConsumerClient cc;
		
	@Test
	//@Ignore
	public void testConsumer() throws IOException {
		
		//rabbitmq
		/*MQCosDestination dest = new MQCosDestination("lx-queue");
		cc.setMqType(MQEnum.RABBITMQ.getIndex());
		try {
			cc.receiveMsg(dest,new ConsumerMsgCallBack() {	
				int i = 0;
				@Override
				public boolean ackMsg(String msg) {
					
					System.out.println("test activemq:" + msg);
					i++;
					System.out.println(i);
					if(i%2 == 0 ){
						return true; 
					}else {
						return false;
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		//active
		MQCosDestination dest = new MQCosDestination(ActiveDestEnum.QUEUE.getIndex(), "lx-queue");
		cc.setMqType(MQEnum.ACTIVEMQ.getIndex());
		try {
			cc.receiveMsg(dest, new ConsumerMsgCallBack() {
				int i = 0;
				@Override
				public boolean ackMsg(String msg) {
					System.out.println("test activemq:" + msg);
					i++;
					System.out.println(i);
					if(i%2 == 0 ){
						return true; 
					}else {
						return false;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		System.out.println();
		
	}
}
```

#事务
```java
package com.xinguang.mq.transaction;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xinguang.mq.transaction.db.service.DBService;

@Component("xaHandler")
public class XAHandler {
	
	@Autowired
	private DBService dbService;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void activeXAHandler(){
		String name = "lengxing";
		
		dbService.insertDataService(name);
		
		jmsTemplate.send("lx-txqueue",new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("response TEY");
			}
		});
		
		System.out.println("=============================");
		//int j = 1 / 0;
		
		System.out.println("执行完毕！");
	}
}
```