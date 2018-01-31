package com.xinguang.xusercenter.common.base;

import lombok.Getter;

/**
 * 提示编码和描述 <br/>
 * code 含义:<br/>
 * ----- 0		: 成功 <br/>
 * ----- 1 开头	: 系统错误 <br/>
 * ----- 2 开头	: 验证失败 <br/>
 *
 * @author yangsh
 */
@Getter
public enum ReturnCode {

	/** 成功 */
    SUCCESS("0", "成功"),
    
    /** 系统异常 */
    SYS_EXCEPTION("10000", "系统异常"),
    
    /** 验证失败 */
    PARAM_ERROR("20000", "参数有误"),
    PARAM_INCOMPLETE("20001", "参数不完整"),
    UID_HAS_EXIST("20002", "用户名已存在"),
    COMPANY_NOT_EXIST("20003", "公司不存在"),
    LT_ERROR("20004", "登录票据有误"),
    UNAME_ERROR("20005", "用户名有误"),
    PASSWD_ERROR("20006", "密码有误"),
	TICKET_ERROR("20007", "服务票据有误"),
    USER_ID_ERROR("20008", "用户ID有误"),
    ANSWER_ERROR("20009", "答案有误"),
    OTHER_ERROR("20010", "其他错误"),
    UNKNOWN_ERROR("20011", "未知错误"),
    INVALID_REQUEST("20012", "无效请求"),
    MOBILE_FORMAT_ERROR("20013", "手机格式有误"),
    MAIL_FORMAT_ERROR("20014", "邮箱格式有误"),

    /** 提示信息 */
    UNSET_PASSWORD_QUESTION("30000", "未设置密保问题");
	
	private String code; // 编码
    private String desc; // 描述

    private ReturnCode(String code, String desc) {
    	this.code = code;
        this.desc = desc;
    }
    
	@Override
	public String toString() {
		return "code: " + code + ", desc: " + desc;
	}
	
}
