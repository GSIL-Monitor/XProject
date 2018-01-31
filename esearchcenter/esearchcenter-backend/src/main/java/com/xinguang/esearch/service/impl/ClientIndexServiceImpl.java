/**
 * 
 */
package com.xinguang.esearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinguang.esearch.curd.IndexService;
import com.xinguang.esearch.service.ClientIndexService;

/**
 * 
 * ClassName: ClientIndexServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 上午9:33:10 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Service
public class ClientIndexServiceImpl extends ESUtil implements ClientIndexService {

    /**
     * indexService:TODO(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.7
     */
    @Autowired
    private IndexService indexService;

    @Override
    public boolean createIndex(String index, String type) {
        // 获取client
        init();

        // 新增
        boolean createIndexResult = indexService.createIndex(getCLIENT(), index, type);

        // 关闭连接
        // close();

        return createIndexResult;
    }

    @Override
    public boolean deleteIndex(String index) {
        // 获取client
        init();

        // 新增
        boolean delResult = indexService.deleteIndex(getCLIENT(), index);

        // 关闭连接
        // close();

        return delResult;

    }

}
