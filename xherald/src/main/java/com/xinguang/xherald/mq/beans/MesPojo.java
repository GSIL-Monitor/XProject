package com.xinguang.xherald.mq.beans;

import com.xinguang.xherald.common.beans.BaseBean;

/**
 * ClassName:MessagePojo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年9月13日 下午5:06:42 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public class MesPojo extends BaseBean{
	//必须，应用唯一标识
	private String appid;
	
	//必须，消息类型，object–对象消息，event–事件消息，heartbeat–心跳，metric–度量，log–日志 等等
	private String msgtype;
	
	//非必须，必须消息类型为object时必须提供。表示实体的唯一id，通常为该数据在数据库中得唯一id键值
	private String objid;
	
	//非必须，消息类型为object时必须提供。表示实体发生的变化类型。可选值 add–新增，update–修改，delete–删除
	private String objopt;
	
	//非必须，消息类型为event时必须提供
	private String etype;
	
	//必须，消息产生时间
	private long ctime;
	
	//必须，消息数据内容
	private Object data;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getEtype() {
		return etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getObjopt() {
		return objopt;
	}

	public void setObjopt(String objopt) {
		this.objopt = objopt;
	}
	
}
