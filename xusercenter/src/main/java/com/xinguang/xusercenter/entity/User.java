package com.xinguang.xusercenter.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户
 *
 * @author yangsh
 */
@Getter
@Setter
public class User {

	private String id; // 用户ID
	private String username; // 用户名
	private String password; // 密码
	private String name; // 姓名
	private String phone; // 手机
	private String email; // 邮箱
	private String state; // 状态: 0-禁用; 1-启用
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	
}