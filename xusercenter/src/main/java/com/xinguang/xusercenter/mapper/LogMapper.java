package com.xinguang.xusercenter.mapper;

import com.xinguang.xusercenter.entity.Log;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yangsh
 */
public interface LogMapper {

    void addLog(Log log);

    /**
     * 通过用户 ID 获取上一次登录时间
     * @param userId 用户ID
     * @return
     */
    @Select("SELECT create_time FROM t_log WHERE user_id=#{0} AND action_type='LOGIN' ORDER BY create_time DESC LIMIT 1")
    String getLastLoginTimeByUserId(String userId);

    /**
     * 通过用户 ID 获取登录次数
     * @param userId 用户ID
     * @return
     */
    @Select("SELECT count(*) FROM t_log WHERE user_id=#{0} AND action_type='LOGIN'")
    Integer getLoginCountByUserId(String userId);

}
