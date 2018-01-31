package com.xinguang.xusercenter.mapper;

import com.xinguang.xusercenter.entity.UserId;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yangsh on 2017-04-19
 */
public interface UserIdMapper {

    /** 获取用户ID */
    UserId get(String companyCode);

    /** 新增用户ID */
    void add(UserId userId);

    /** 修改用户ID */
    void update(UserId userId);

}
