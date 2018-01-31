package com.xinguang.esearch.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearchcenter.request.PageRequest;
import com.xinguang.esearchcenter.request.TopRequest;

/**
 * 
 * ClassName: ClientQueryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 下午4:31:40 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface ClientQueryService {
	
	/**
	 * 
	 * queryForTop:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	JSONObject queryForTop(IndexBean indexBean, TopRequest request);
	
	JSONArray queryForTop2(IndexBean indexBean, TopRequest request);
	
	/**
	 * 
	 * queryForPage:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Administrator-lengxing
	 * @param request
	 * @return
	 * @since JDK 1.7
	 */
	JSONObject queryForPage(IndexBean indexBean, PageRequest request);
    
    /**
     * checkForIsExist:步骤(DP、search). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param index 索引
     * @param type 类型
     * @param request 请求参数
     * @param chainName QP过滤链
     * @return 返回查询结果
     * @since JDK 1.7
     */
    boolean checkForIsExist(String index, String type, String field, String value);
    
    /**
     * queryForAutoComplte:步骤(DP、search). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param index 索引
     * @param type 类型
     * @param Map<String, HashSet<String>> 请求参数
     * @param chainName QP过滤链
     * @return 返回查询结果
     * @since JDK 1.7
     */
    List<String> queryForAutoComplte(String index, String type, Map<String, HashSet<String>> queryMap);
	
	/**
	 * queryDSL:直接将Query DSL传给es查询，不经过QP(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param index 索引
	 * @param type 类型
	 * @param queryDSL 查询queryDSL
	 * @return 返回查询结果
	 * @since JDK 1.7
	 */
    JSONObject queryDSL(String index, String type, String queryDSL);
    
    JSONObject queryUser(IndexBean indexBean, PageRequest request);
    
    JSONObject queryUserByIds(IndexBean indexBean, List<Long> idList);
    
}
