package com.baidu.disconf.web.service.env.dao;

import java.util.List;
import java.util.Set;

import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface EnvDao extends BaseDao<Long, Env> {

    /**
     * @param name
     *
     * @return
     */
    Env getByName(String name);
    
    /**
     * @param ids
     *
     * @return
     */
    List<Env> getByIds(Set<Long> ids);
    
}
