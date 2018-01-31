package com.xinguang.xherald.web.response;

import com.xinguang.xherald.common.beans.BaseBean;

/**
 * ClassName:TopologyResponse <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年8月29日 上午11:53:34 <br/>
 * @author   hzlengxing
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TopologyResponse extends BaseBean{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 2742685860772782302L;
	private boolean flag;
	private int code;
	private String message;

	public TopologyResponse(boolean flag, int code, String message) {
		super();
		this.flag = flag;
		this.code = code;
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

