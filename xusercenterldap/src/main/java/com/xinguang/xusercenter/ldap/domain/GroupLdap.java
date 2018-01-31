package com.xinguang.xusercenter.ldap.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by yangsh on 2017-04-19
 */
@Getter
@Setter
public class GroupLdap {

    private String gidNumber; // 组ID
    private String cn; // 组Code
    private String description; // 组名称

}
