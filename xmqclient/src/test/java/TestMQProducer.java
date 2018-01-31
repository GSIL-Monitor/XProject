import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinguang.mq.beans.MQCosDestination;
import com.xinguang.mq.beans.MQMessage;
import com.xinguang.mq.beans.MQPrdDestination;
import com.xinguang.mq.client.MQConsumerClient;
import com.xinguang.mq.client.MQProducerClient;
import com.xinguang.mq.enums.ActiveDestEnum;
import com.xinguang.mq.enums.MQEnum;


/**
 * ClassName:TestRabbit <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年3月3日 上午10:28:15 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
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
		pc.setMqType(MQEnum.RABBITMQ.getIndex());
		MQMessage mqMsg = new MQMessage("hello667");	
		MQPrdDestination dest = new MQPrdDestination("lx-exchange","lx-queue");
		
		//active
		/*pc.setMqType(MQEnum.ACTIVEMQ.getIndex());
		MQMessage mqMsg = new MQMessage("hello");		
		MQPrdDestination dest = new MQPrdDestination(ActiveDestEnum.QUEUE.getIndex(),"lx-queue");*/
		
		//delayQueue
		/*pc.setMqType(MQEnum.DELAYQUEUE.getIndex());
		MQMessage mqMsg = new MQMessage("hello");		
		MQPrdDestination dest = new MQPrdDestination("lx-exchange-pre","lx-queue-d");*/
		
		pc.send(dest,mqMsg);

	}
	
}
