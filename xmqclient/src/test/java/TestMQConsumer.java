import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinguang.mq.beans.MQCosDestination;
import com.xinguang.mq.client.MQConsumerClient;
import com.xinguang.mq.enums.ActiveDestEnum;
import com.xinguang.mq.enums.MQEnum;
import com.xinguang.mq.service.ConsumerMsgCallBack;


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
