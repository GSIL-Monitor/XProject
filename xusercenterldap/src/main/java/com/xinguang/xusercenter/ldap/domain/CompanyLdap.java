package com.xinguang.xusercenter.ldap.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh on 2017-04-19
 */
@Getter
@Setter
public class CompanyLdap {

    private String dc; // 公司ID
    private String o; // 公司Code
    private String description; // 公司名称
    private String telephoneNumber; // 联系电话
    private String street; // 联系地址

}
