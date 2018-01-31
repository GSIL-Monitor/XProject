/**
 * 
 */
package com.xinguang.esearch.curd;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import com.alibaba.fastjson.JSONArray;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearchcenter.enums.SortOrderEnum;
import com.xinguang.esearchcenter.request.BaseRequest;
import com.xinguang.esearchcenter.request.PageRequest;
import com.xinguang.esearchcenter.request.TopRequest;

/**
 * ClassName: QueryService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 下午4:48:52 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface QueryService {
	
    boolean queryIsExsit(Client client, String index, String type, QueryBuilder qb);

    List<String> searchSpecifyFields(Client client,String index, String type, QueryBuilder qb, String[] retrunFields);
	
    long searchTotal(Client client,String index, String type, QueryBuilder qb);
    
    long searchTotal(Client client, IndexBean indexBean, BaseRequest request);   
    
    List<String> searchTop(Client client,String index, String type, QueryBuilder qb, int size);
    
    JSONArray searchTop(Client client, IndexBean indexBean, TopRequest request);

    String searchForPage(Client client,String index, String type, QueryBuilder qb,int page,int pageSize);
    
    List<String> searchPage(Client client, IndexBean indexBean, PageRequest request);

    List<String> getAnalyzeResults(Client client,String index, String analyzer, String text);

    void assembleQueryCondition(List<Map<String, Object>> orList, BoolQueryBuilder bqb);
    
    QueryBuilder assembleQueryContent(String queryContent, String[] queryFields, BoolQueryBuilder bqb);

    QueryBuilder assembleQuery(BaseRequest request);
    
    void setPageInfo(int offset, int size, SearchRequestBuilder srb);
    
    void setSortInfo(Map<String , SortOrderEnum> sortFields, SearchRequestBuilder srb);
    
    void setHighLightInfo(Map<String, Integer> LighLightInfo, SearchRequestBuilder srb);

    <T> QueryBuilder assembleQueryCommon(Map<String, T> queryMap);
    
    List<String> searchQueryDSLForPage(Client client,String index, String type, String queryDSL);
    
    long searchQueryDSLTotal(Client client,String index, String type, String queryDSL);
    
    JSONArray searchUser(Client client, IndexBean indexBean, PageRequest request);
    
    JSONArray searchUserByIds(Client client, IndexBean indexBean, List<Long> idList);

}
