package com.xinguang.xusercenter.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by yangsh
 */
@Getter
@Setter
public class Security {

    private String id; // 安全ID = 用户ID
    private String phone; // 手机
    private String email; // 邮箱
    private Integer questiononeId; // 问题1ID
    private String answerone; // 问题1答案
    private String questiononePrompt; // 问题1提示
    private Integer questiontwoId; // 问题2ID
    private String answertwo; // 问题2答案
    private String questiontwoPrompt; // 问题2提示
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

}
