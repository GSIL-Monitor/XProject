package com.navercorp.pinpoint.web.alarm.email;

public class EmailTest {

	public static void main(String[] args) {  
        Email mail = new Email();  
        mail.setHost("imap.exmail.qq.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
        mail.setSender("gxtest@xinguangnet.com");  
        mail.setReceiver("kongyt0823@163.com"); // 接收人  
        mail.setUsername("gxtest@xinguangnet.com"); // 登录账号,一般都是和邮箱名一样吧  
        mail.setPassword("Aa123465"); // 发件人邮箱的登录密码  
        mail.setSubject("testaaaaaaaaa");  
        mail.setMessage("bbbbbbbbbbbbbbbbb");
        new EmailUtilImpl().send(mail);  
    }  
}
