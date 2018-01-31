package com.xinguang.xusercenter.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户ID
 *
 * Created by yangsh on 2017-04-19
 */
@Getter
@Setter
public class UserId {

    private String id; // ID
    private Integer userId; // 用户ID
    private String companyCode; // 公司Code
    private Date updateTime; // 更新时间

}
