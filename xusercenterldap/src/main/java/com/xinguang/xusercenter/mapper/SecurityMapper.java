package com.xinguang.xusercenter.mapper;

import com.xinguang.xusercenter.entity.Security;
import org.apache.ibatis.annotations.Update;

/**
 * Created by yangsh
 */
public interface SecurityMapper {

    /**
     * 新增安全
     * @param security
     */
    void addSecurity(Security security);

    /**
     * 通过 ID 获取安全
     * @return
     */
    Security getSecurityById(String id);

    /**
     * 修改安全 (设置密保问题)
     * @param
     */
    @Update("UPDATE t_security SET questionone_id=#{1}, answerone=#{2}, questionone_prompt=#{3}, questiontwo_id=#{4}, answertwo=#{5}, questiontwo_prompt=#{6}, update_time=now() WHERE id=#{0}")
    void updateSecurityForSetQuestion(
            String id,
            Integer questiononeId,
            String answerone,
            String questiononePrompt,
            Integer questiontwoId,
            String answertwo,
            String questiontwoPrompt);

}
