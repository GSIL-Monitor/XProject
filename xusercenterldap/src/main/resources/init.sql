-- 创建数据库
CREATE DATABASE IF NOT EXISTS xusercenter DEFAULT CHARSET=utf8;

-- 连接数据库
USE xusercenter;

-- 创建日志表
CREATE TABLE IF NOT EXISTS t_log (
	id VARCHAR(40) PRIMARY KEY COMMENT '日志ID (主键)',
	user_id VARCHAR(40) NOT NULL COMMENT '用户ID',
	username VARCHAR(20) NOT NULL COMMENT '用户名',
	action_type VARCHAR(20) NOT NULL COMMENT '行为类型: REGIST-注册; LOGIN-登录; UPDATEPASSWORD-修改密码; UPDATEINFO-修改信息; LOGOUT-退出; RESETPASSWORD-重置密码',
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

-- 创建用户ID表
CREATE TABLE IF NOT EXISTS t_user_id (
    id VARCHAR(20) PRIMARY KEY COMMENT 'ID',
    user_id INT(4) NOT NULL COMMENT '用户ID',
    company_code VARCHAR(20) NOT NULL UNIQUE COMMENT '公司Code',
    update_time DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB default CHARSET=utf8 COMMENT='用户ID';

-- 问题记录
INSERT INTO t_question (`id`, `content`) VALUES (1, '最喜欢的运动？');
INSERT INTO t_question (`id`, `content`) VALUES (2, '父亲的名字？');
INSERT INTO t_question (`id`, `content`) VALUES (3, '母亲的名字？');
INSERT INTO t_question (`id`, `content`) VALUES (4, '最想去的地方？');
INSERT INTO t_question (`id`, `content`) VALUES (5, '最好的朋友？');
INSERT INTO t_question (`id`, `content`) VALUES (6, '最喜欢吃的菜？');
INSERT INTO t_question (`id`, `content`) VALUES (7, '出生的地方？');
INSERT INTO t_question (`id`, `content`) VALUES (8, '大学专业？');
INSERT INTO t_question (`id`, `content`) VALUES (9, '出生的年份？');
INSERT INTO t_question (`id`, `content`) VALUES (10, '最喜欢的明星？');