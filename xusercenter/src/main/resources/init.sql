-- 创建数据库
CREATE DATABASE IF NOT EXISTS xusercenter DEFAULT CHARSET=utf8;

-- 连接数据库
USE xusercenter;

-- 创建用户表
CREATE TABLE IF NOT EXISTS t_user (
	id VARCHAR(40) PRIMARY KEY COMMENT '用户ID (主键)',
	username VARCHAR(20) UNIQUE NOT NULL COMMENT '用户名',
	password VARCHAR(50) NOT NULL COMMENT '密码',
	name VARCHAR(24) COMMENT '姓名',
	phone VARCHAR(11) COMMENT '手机',
	email VARCHAR(24) COMMENT '邮箱',
	state CHAR(1) NOT NULL COMMENT '状态: 0-禁用; 1-启用',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB default CHARSET=utf8 COMMENT='用户表';

-- 创建日志表
CREATE TABLE IF NOT EXISTS t_log (
	id VARCHAR(40) PRIMARY KEY COMMENT '日志ID (主键)',
	user_id VARCHAR(40) NOT NULL COMMENT '用户ID',
	username VARCHAR(20) NOT NULL COMMENT '用户名',
	action_type VARCHAR(20) NOT NULL COMMENT '行为类型: REGIST-注册; LOGIN-登录; UPDATEPASSWORD-修改密码; LOGOUT-退出; RESETPASSWORD-重置密码',
	create_time DATETIME NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB default CHARSET=utf8 COMMENT='日志表';

-- 创建问题表
CREATE TABLE IF NOT EXISTS t_question (
	id INT(2) PRIMARY KEY COMMENT '问题ID (主键)',
	content VARCHAR(50) UNIQUE NOT NULL COMMENT '问题内容'
) ENGINE=InnoDB default CHARSET=utf8 COMMENT='问题表';

-- 创建安全表
CREATE TABLE IF NOT EXISTS t_security (
	id VARCHAR(40) PRIMARY KEY COMMENT '安全ID (主键) = 用户ID',
	phone VARCHAR(11) COMMENT '手机',
	email VARCHAR(24) COMMENT '邮箱',
	questionone_id INT(2) COMMENT '问题 1',
	answerone VARCHAR(20) COMMENT '问题 1 答案',
	questionone_prompt VARCHAR(50) COMMENT '问题 1 提示',
	questiontwo_id INT(2) COMMENT '问题 2',
	answertwo VARCHAR(20) COMMENT '问题 2 答案',
	questiontwo_prompt VARCHAR(50) COMMENT '问题 2 提示',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	update_time DATETIME COMMENT '更新时间'
) ENGINE=InnoDB default CHARSET=utf8 COMMENT='安全表';