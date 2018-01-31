/**
 * 
 */
package com.xinguang.xherald.mq.beans;

/**
 * 
 * ClassName: MQInitConnection <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月23日 下午4:05:15 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
public class MQInitConnection {
	public String host;
	public String vhost;
	public int port;
	public String username;
	public String password;
	
	public String cron;	
	public int duplicated;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getVhost() {
		return vhost;
	}
	public void setVhost(String vhost) {
		this.vhost = vhost;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public int getDuplicated() {
		return duplicated;
	}
	public void setDuplicated(int duplicated) {
		this.duplicated = duplicated;
	}
}
