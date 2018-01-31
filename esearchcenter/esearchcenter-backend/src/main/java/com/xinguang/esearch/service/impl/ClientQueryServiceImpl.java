/**
 * 
 */
package com.xinguang.esearch.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearch.constant.CommonConstant;
import com.xinguang.esearch.constant.DisRequestConstant;
import com.xinguang.esearch.curd.QueryService;
import com.xinguang.esearch.curd.impl.QueryServiceImpl;
import com.xinguang.esearch.service.ClientQueryService;
import com.xinguang.esearch.utils.JsonUtil;
import com.xinguang.esearchcenter.request.BaseRequest;
import com.xinguang.esearchcenter.request.PageRequest;
import com.xinguang.esearchcenter.request.TopRequest;

/**
 * ClassName: ClientQueryServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 下午4:33:18 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
@Service
public class ClientQueryServiceImpl extends ESUtil implements ClientQueryService {
	/**
	 * logger:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientQueryServiceImpl.class);

	/**
	 * queryService:查询es服务(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	@Autowired
	private QueryService queryService;
	
	@Override
	public JSONObject queryForTop(IndexBean indexBean, TopRequest request) {
		// 获取client
		init();

		//查询
		JSONArray queryResult = queryService.searchTop(getCLIENT(), indexBean, request);

		JSONObject contentJson = new JSONObject();
		if (queryResult != null && queryResult.size() > 0) {
			contentJson.put(CommonConstant.FLAG, true);
			contentJson.put(CommonConstant.LIST, queryResult);
		} else {
			contentJson.put(CommonConstant.FLAG, false);
			contentJson.put(CommonConstant.LIST, null);
		}
		return contentJson;
	}
	
	@Override
	public JSONObject queryUser(IndexBean indexBean, PageRequest request) {
		
		init();
		
		JSONArray queryResult = queryService.searchUser(getCLIENT(), indexBean, request);
		JSONObject contentJson = new JSONObject();
		if (queryResult != null && queryResult.size() > 0) {
			contentJson.put(CommonConstant.FLAG, true);
			contentJson.put(CommonConstant.LIST, queryResult);
		} else {
			contentJson.put(CommonConstant.FLAG, false);
			contentJson.put(CommonConstant.LIST, null);
		}
		return contentJson;
	}
	
	@Override
	public JSONObject queryUserByIds(IndexBean indexBean, List<Long> idList) {
		
		init();
		JSONArray queryResult = queryService.searchUserByIds(getCLIENT(), indexBean, idList);
		JSONObject contentJson = new JSONObject();
		if (queryResult != null && queryResult.size() > 0) {
			contentJson.put(CommonConstant.FLAG, true);
			contentJson.put(CommonConstant.LIST, queryResult);
		} else {
			contentJson.put(CommonConstant.FLAG, false);
			contentJson.put(CommonConstant.LIST, null);
		}
		return contentJson;
	}
	
	@Override
	public JSONArray queryForTop2(IndexBean indexBean, TopRequest request) {
		// 获取client
		init();

		//查询
		JSONArray queryResult = queryService.searchTop(getCLIENT(), indexBean, request);
		 
		return queryResult;
	}

	@Override
	public JSONObject queryForPage(IndexBean indexBean, PageRequest request) {
		// 获取client
		init();

		long totalCount = queryService.searchTotal(getCLIENT(), indexBean, request.getBaseRequest());

		List<String> queryResult = null;

		// 判断当前查询的数目是否超过最大的条数
		if (totalCount > request.getOffset()) {
			queryResult = queryService.searchPage(getCLIENT(), indexBean, request);
		}

		JSONObject contentJson = new JSONObject();
		if (queryResult != null && queryResult.size() > 0) {
			contentJson.put(CommonConstant.FLAG, true);
			contentJson.put(CommonConstant.COUNT, totalCount);
			contentJson.put(CommonConstant.LIST, queryResult);
			if (totalCount > request.getOffset() + request.getSize()) {
				contentJson.put(CommonConstant.HASNEXT, true);
			} else {
				contentJson.put(CommonConstant.HASNEXT, false);
			}
		} else {
			contentJson.put(CommonConstant.FLAG, false);
			contentJson.put(CommonConstant.COUNT, totalCount);
			contentJson.put(CommonConstant.LIST, null);
			contentJson.put(CommonConstant.HASNEXT, false);
		}
		return contentJson;
	}

	@Override
	public boolean checkForIsExist(String index, String type, String field, String value) {
		// 获取client
		init();

		TermQueryBuilder tqb = QueryBuilders.termQuery(field, value);

		return queryService.queryIsExsit(getCLIENT(), index, type, tqb);
	}

	@Override
	public List<String> queryForAutoComplte(String index, String type, Map<String, HashSet<String>> queryMap) {
		// 获取client
		init();

		// 执行搜索引擎

		return null;
	}

	@Override
	public JSONObject queryDSL(String index, String type, String queryDSL) {
		// 获取client
		init();

		String query = CommonConstant.QUERY;

		JSONObject queryObject = JSON.parseObject(queryDSL);

		JSONObject query4Total = queryObject.getJSONObject(query);// 获取queryDSL中query部分

		String jsonStr = JsonUtil.getUnitJsonStr(query, query4Total);

		long totalCount = queryService.searchQueryDSLTotal(getCLIENT(), index, type, jsonStr);

		int from = queryObject.getIntValue(CommonConstant.FROM);
		int size = queryObject.getIntValue(CommonConstant.SIZE);

		List<String> queryResult = null;
		if (totalCount > (from - 1) * size) {// 判断当前查询的数目是否超过最大的条数
			queryResult = queryService.searchQueryDSLForPage(getCLIENT(), index, type, queryDSL);
		}

		JSONObject contentJson = new JSONObject();
		contentJson.put(CommonConstant.COUNT, totalCount);
		contentJson.put(CommonConstant.LIST, queryResult);

		return contentJson;
	}

	private static QueryBuilder assembleSpecificQuery(Map<String, Object> reqMap) {

		BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();

		if (reqMap.containsKey(DisRequestConstant.SUPPLIERID)) {
			mustQuery.must(
					QueryBuilders.termQuery(DisRequestConstant.SUPPLIERID, reqMap.get(DisRequestConstant.SUPPLIERID)));
		}

		if (reqMap.containsKey(DisRequestConstant.PRODUCTIDS)) {
			mustQuery.must(
					QueryBuilders.termQuery(DisRequestConstant.PRODUCTIDS, reqMap.get(DisRequestConstant.PRODUCTIDS)));
		}

		if (reqMap.containsKey(DisRequestConstant.PRODUCTNAME)) {
			mustQuery.must(QueryBuilders.termQuery(DisRequestConstant.PRODUCTNAME,
					reqMap.get(DisRequestConstant.PRODUCTNAME)));
		}

		if (reqMap.containsKey(DisRequestConstant.MAXPRICE)) {
			mustQuery.must(
					QueryBuilders.rangeQuery(DisRequestConstant.MAXPRICE).gte(reqMap.get(DisRequestConstant.MAXPRICE)));
		}

		if (reqMap.containsKey(DisRequestConstant.MINPRICE)) {
			mustQuery.must(
					QueryBuilders.rangeQuery(DisRequestConstant.MINPRICE).lte(reqMap.get(DisRequestConstant.MINPRICE)));
		}

		if (reqMap.containsKey(DisRequestConstant.MINBROKERAGE)) {
			mustQuery.must(QueryBuilders.rangeQuery(DisRequestConstant.BROKERAGE)
					.gte(reqMap.get(DisRequestConstant.MINBROKERAGE)));
		}

		if (reqMap.containsKey(DisRequestConstant.MAXBROKERAGE)) {
			mustQuery.must(QueryBuilders.rangeQuery(DisRequestConstant.BROKERAGE)
					.lte(reqMap.get(DisRequestConstant.MAXBROKERAGE)));
		}

		return mustQuery;
	}

}
