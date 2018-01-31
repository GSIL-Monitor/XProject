package com.navercorp.pinpoint.web.alarm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.navercorp.pinpoint.web.alarm.checker.AlarmChecker;
import com.navercorp.pinpoint.web.alarm.email.Email;
import com.navercorp.pinpoint.web.alarm.email.EmailUtilImpl;
import com.navercorp.pinpoint.web.service.UserGroupService;

public class AlarmMessageSenderImple implements AlarmMessageSender{
	
	private final Logger logger = LoggerFactory.getLogger(AlarmMessageSenderImple.class);
	
	@Autowired
	private UserGroupService userGroupService;
		
	private EmailUtilImpl emailUtilImpl = new EmailUtilImpl();

	@Override
	public void sendSms(AlarmChecker checker, int sequenceCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmail(AlarmChecker checker, int sequenceCount) {
		// TODO Auto-generated method stub
		 List<String> receivers = userGroupService.selectEmailOfMember(checker.getuserGroupId());

	        if (receivers.size() == 0) {
	            return;
	        }

	       String message = checker.getEmailMessage();
	       
	       logger.info("send email : {}", message);
	       
	       Email email = new Email();
	       email.setHost("imap.exmail.qq.com");
	       email.setSender("gxtest@xinguangnet.com");
	       email.setName("pinpoint");
	       email.setUsername("gxtest@xinguangnet.com");
	       email.setPassword("Aa123465");
	       email.setMessage(message);
	       email.setSubject("pinpoint告警信息");
	       for(String receiver : receivers) {
	    	   email.setReceiver(receiver);
	    	   emailUtilImpl.send(email);
	       }
	}

}
