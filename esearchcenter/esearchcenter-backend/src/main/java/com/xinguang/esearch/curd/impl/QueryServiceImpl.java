/**
 * 
 */
package com.xinguang.esearch.curd.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xinguang.esearch.beans.ContentBean;
import com.xinguang.esearch.beans.IndexBean;
import com.xinguang.esearch.curd.QueryService;
import com.xinguang.esearch.utils.HtmlUtils;
import com.xinguang.esearch.utils.JsonPojoUtil;
import com.xinguang.esearchcenter.enums.SortOrderEnum;
import com.xinguang.esearchcenter.request.BaseRequest;
import com.xinguang.esearchcenter.request.LongRange;
import com.xinguang.esearchcenter.request.PageRequest;
import com.xinguang.esearchcenter.request.TopRequest;

/**
 * ClassName: QueryServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 下午4:52:44 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
@Repository
public class QueryServiceImpl implements QueryService {
	/**
	 * LOGGER:logger对象(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryServiceImpl.class);

	@Override
	public long searchTotal(Client client, String index, String type, QueryBuilder qb) {
		SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setQuery(qb);
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return 0;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return 0;
		}
		return rsp.getHits().getTotalHits();
	}

	@Override
	public long searchTotal(Client client, IndexBean indexBean, BaseRequest request) {
		SearchRequestBuilder srb = client.prepareSearch(indexBean.getIndexName()).setTypes(indexBean.getTypeName())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setQuery(assembleQuery(request));
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return 0;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return 0;
		}
		long count = rsp.getHits().getTotalHits();
		LOGGER.info("searchTotal. input={},totalCount={}", srb.toString(), count);
		return count;
	}

	public List<String> searchTop(Client client, String index, String type, QueryBuilder qb, int size) {
		SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setQuery(qb);
		srb.setSize(size);
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}

		SearchHits searchHit = rsp.getHits();

		List<String> resList = new ArrayList<String>();

		for (int i = 0; i < searchHit.getHits().length; i++) {
			resList.add(searchHit.getHits()[i].getSourceAsString());
		}
		LOGGER.info("searchTop. input={},searchResultList={}", srb.toString(), resList.toString());
		return resList;
	}

	public JSONArray searchTop(Client client, IndexBean indexBean, TopRequest request) {
		SearchRequestBuilder srb = client.prepareSearch(indexBean.getIndexName()).setTypes(indexBean.getTypeName())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		// 设置查询内容
		srb.setQuery(assembleQuery(request.getBaseRequest()));

		// 设置排序信息
		setSortInfo(request.getBaseRequest().getSortFields(), srb);

		// 高亮显示信息
		setHighLightInfo(request.getBaseRequest().getLighLightInfo(), srb);

		// 设置查询条数
		srb.setSize(request.getTop());

		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("searchTop failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("searchTop failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}

		//List<String> resList = new ArrayList<String>();
		JSONArray ja = new JSONArray();
		SearchHits searchHits = rsp.getHits();
		SearchHit[] hits = searchHits.getHits();
		for (int i = 0; i < hits.length; i++) {
			SearchHit hit = hits[i];
			String json = hit.getSourceAsString();

			// 将json串值转换成对应的实体对象
			ContentBean cb = JsonPojoUtil.json2Obj(json, ContentBean.class);

			// 获取对应的高亮域
			Map<String, HighlightField> highLightResult = hit.highlightFields();

			try {
				setHighLightResult(request.getBaseRequest().getLighLightInfo(), cb, highLightResult);
			} catch (Exception e) {
				LOGGER.error("set HighLight failed. input={},searchErrorMessage={}", srb.toString(), e);
				continue;
			}
			//ja.add(JsonPojoUtil.obj2Map(cb));
			ja.add(JSON.toJSON(cb));
		}
		LOGGER.info("searchTop. input={},searchResultList={}", srb.toString(), ja);
		return ja;
	}

	@Override
	public List<String> searchSpecifyFields(Client client, String index, String type, QueryBuilder qb,
			String[] retrunFields) {
		SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setFetchSource(retrunFields, null);
		srb.setQuery(qb);
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}

		SearchHits searchHit = rsp.getHits();

		List<String> resList = new ArrayList<String>();

		for (int i = 0; i < searchHit.getHits().length; i++) {
			resList.add(searchHit.getHits()[i].getSource().toString());
		}
		LOGGER.info("search. input={},searchResultList={}", srb.toString(), resList.toString());

		return resList;
	}

	@Override
	public List<String> getAnalyzeResults(Client client, String index, String analyzer, String text) {
		AdminClient adminClient = client.admin();
		IndicesAdminClient indicesAdminClient = adminClient.indices();
		AnalyzeRequestBuilder analyzeRequestBuilder = indicesAdminClient.prepareAnalyze(index, text);
		analyzeRequestBuilder.setAnalyzer(analyzer);
		AnalyzeResponse response;
		try {
			response = analyzeRequestBuilder.execute().actionGet();
		} catch (Exception e) {
			LOGGER.error("getAnalyzeResults failed. input={},error={}", text, e);
			return null;
		}
		List<AnalyzeToken> analyzeTokens = response.getTokens();
		List<String> resList = new ArrayList<String>();
		for (AnalyzeToken token : analyzeTokens) {
			resList.add(token.getTerm());
		}
		LOGGER.info("input={},analyzeResults={}", text, resList.toString());
		return resList;
	}

	@Override
	public String searchForPage(Client client, String index, String type, QueryBuilder qb, int page, int pageSize) {
		SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setQuery(qb);
		srb.setFrom((page - 1) * pageSize);// 分页起始位置（跳过开始的n个）
		srb.setSize(pageSize); // 本次返回的文档数量;
		System.out.println(srb);
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}

		SearchHits searchHit = rsp.getHits();

		List<Object> resList = new ArrayList<Object>();

		for (int i = 0; i < searchHit.getHits().length; i++) {
			resList.add(searchHit.getHits()[i].getSourceAsString());
		}
		LOGGER.info("search. input={},searchResultList={}", srb.toString(), resList.toString());

		return resList.toString();
	}

	@Override
	public <T> QueryBuilder assembleQueryCommon(Map<String, T> queryMap) {
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();

		// 遍历map
		Iterator<Entry<String, T>> entries = queryMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, T> entry = entries.next();
			bqb.must(QueryBuilders.termsQuery(entry.getKey(), entry.getValue()));
		}
		LOGGER.info("input={},queryBuilder={}", queryMap, bqb.toString());

		return bqb;
	}

	@Override
	public List<String> searchQueryDSLForPage(Client client, String index, String type, String queryDSL) {
		SearchResponse sr = client.prepareSearch(index).setTypes(type).setSource(queryDSL).get();

		List<String> resList = new ArrayList<String>();

		for (SearchHit hit : sr.getHits()) {
			resList.add(hit.getSourceAsString());
		}
		LOGGER.info("search. input={},searchResultList={}", queryDSL, resList.toString());
		return resList;
	}

	@Override
	public long searchQueryDSLTotal(Client client, String index, String type, String queryDSL) {
		SearchResponse sr = client.prepareSearch(index).setTypes(type).setSource(queryDSL).get();
		return sr.getHits().getTotalHits();
	}

	@Override
	public boolean queryIsExsit(Client client, String index, String type, QueryBuilder qb) {
		boolean retrunFlag = false;
		SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setQuery(qb);

		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return retrunFlag;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return retrunFlag;
		}

		SearchHits searchHit = rsp.getHits();

		if (searchHit.getHits().length > 0) {
			retrunFlag = true;
		}

		return retrunFlag;
	}

	@Override
	public QueryBuilder assembleQueryContent(String queryContent, String[] queryFields, BoolQueryBuilder bqb) {
		QueryBuilder qb = null;
		if (queryFields != null && queryFields.length > 0) {
			qb = QueryBuilders.multiMatchQuery(queryContent, queryFields);
		} else {
			qb = QueryBuilders.matchAllQuery();
		}
		return bqb.must(qb);
	}

	@Override
	public QueryBuilder assembleQuery(BaseRequest request) {
		//条件设置
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		if (request.getTermConditions() != null && request.getTermConditions().size() > 0) {
			assembleQueryCondition(request.getTermConditions(), bqb);
		}
		
		//范围设置
		if (request.getRangeCondition() != null && request.getRangeCondition().size() > 0) {
			QueryBuilderUtils.assembleQueryByRange(request.getRangeCondition(), bqb);
		}

		// 查询设置
		assembleQueryContent(request.getQueryContent(), request.getQueryFields(), bqb);

		return bqb;
	}

	@Override
	public List<String> searchPage(Client client, IndexBean indexBean, PageRequest request) {
		SearchRequestBuilder srb = client.prepareSearch(indexBean.getIndexName()).setTypes(indexBean.getTypeName())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

		// 设置查询内容
		srb.setQuery(assembleQuery(request.getBaseRequest()));
		
		// 设置分页信息
		setPageInfo(request.getOffset(), request.getSize(), srb);

		// 设置排序信息
		setSortInfo(request.getBaseRequest().getSortFields(), srb);

		// 高亮显示信息
		setHighLightInfo(request.getBaseRequest().getLighLightInfo(), srb);

		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}

		List<String> resList = new ArrayList<String>();
		SearchHits searchHits = rsp.getHits();
		SearchHit[] hits = searchHits.getHits();
		for (int i = 0; i < hits.length; i++) {
			SearchHit hit = hits[i];
			String json = hit.getSourceAsString();

			// 将json串值转换成对应的实体对象
			ContentBean cb = JsonPojoUtil.json2Obj(json, ContentBean.class);

			// 获取对应的高亮域
			Map<String, HighlightField> highLightResult = hit.highlightFields();

			try {
				setHighLightResult(request.getBaseRequest().getLighLightInfo(), cb, highLightResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				LOGGER.error("set HighLight failed. input={},searchErrorMessage={}", srb.toString(), e);
				continue;
			}
			resList.add(JsonPojoUtil.obj2Json(cb));
		}
		LOGGER.info("searchPage. input={},searchResultList={}", srb.toString(), resList.toString());
		return resList;
	}
	
	@Override
	public JSONArray searchUser(Client client, IndexBean indexBean, PageRequest request) {
		// TODO Auto-generated method stub
		SearchRequestBuilder srb = client.prepareSearch(indexBean.getIndexName()).setTypes(indexBean.getTypeName())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
		srb.setQuery(assembleQuery(request.getBaseRequest()));
		setPageInfo(request.getOffset(), request.getSize(), srb);
		
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}
		
		//List<String> resList = new ArrayList<String>();
		JSONArray js = new JSONArray();
		SearchHits searchHits = rsp.getHits();
		SearchHit[] hits = searchHits.getHits();
		for (int i = 0; i < hits.length; i++) {
			SearchHit hit = hits[i];
			String json = hit.getSourceAsString();

			js.add(json);
		}
		LOGGER.info("searchUser. input={},searchResultList={}", srb.toString(), js.toString());
		return js;
	}
	
	private QueryBuilder buildUserQueryByIds(List<Long> idList) {
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		if(idList != null && idList.size() > 0) {
			for(Long id:idList) {
				bqb.should(QueryBuilders.termQuery("userId", id));
			}
		}
		return bqb;
	}
	
	@Override
	public JSONArray searchUserByIds(Client client, IndexBean indexBean, List<Long> idList) {
		SearchRequestBuilder srb = client.prepareSearch(indexBean.getIndexName()).setTypes(indexBean.getTypeName())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
		//拼装查询语句
		srb.setQuery(buildUserQueryByIds(idList));
		SearchResponse rsp;
		try {
			rsp = srb.execute().get();
		} catch (InterruptedException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		} catch (ExecutionException e) {
			LOGGER.error("search failed. input={},searchErrorMessage={}", srb.toString(), e);
			return null;
		}
		
		//List<String> resList = new ArrayList<String>();
		JSONArray js = new JSONArray();
		SearchHits searchHits = rsp.getHits();
		SearchHit[] hits = searchHits.getHits();
		for (int i = 0; i < hits.length; i++) {
			SearchHit hit = hits[i];
			String json = hit.getSourceAsString();

			js.add(json);
		}
		LOGGER.info("searchUserByIds. input={},searchResultList={}", srb.toString(), js.toString());
		return js;
	}

	@Override
	public void setPageInfo(int offset, int size, SearchRequestBuilder srb) {
		// 分页
		srb.setFrom(offset).setSize(size);
	}

	@Override
	public void setSortInfo(Map<String, SortOrderEnum> sortFields, SearchRequestBuilder srb) {
		// 排序
		srb.addSort("_score", SortOrder.DESC);
		
		if (sortFields != null && sortFields.size() > 0) {
			Iterator<Map.Entry<String, SortOrderEnum>> entries = sortFields.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, SortOrderEnum> entry = entries.next();
				//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null) {
					srb.addSort(entry.getKey(), entry.getValue().getStatusCode() == 1 ? SortOrder.DESC : SortOrder.ASC);
				}
			}
		}
	}

	@Override
	public void setHighLightInfo(Map<String, Integer> LighLightInfo, SearchRequestBuilder srb) {
		// TODO Auto-generated method stub
		if (LighLightInfo != null && LighLightInfo.size() > 0) {
			Iterator<Map.Entry<String, Integer>> entries = LighLightInfo.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Integer> entry = entries.next();

				if (entry.getValue() != null && entry.getValue() > 0) {
					srb.addHighlightedField(entry.getKey(), entry.getValue());
				} else {
					srb.addHighlightedField(entry.getKey());
				}
				srb.setHighlighterPreTags("");
				srb.setHighlighterPostTags("");
			}
		}
	}

	private void setHighLightResult(Map<String, Integer> LighLightInfo, ContentBean cb,
			Map<String, HighlightField> result) throws NoSuchMethodException, SecurityException, IllegalAccessException,
					IllegalArgumentException, InvocationTargetException {
		if (LighLightInfo != null && LighLightInfo.size() > 0) {
			Iterator<Map.Entry<String, Integer>> entries = LighLightInfo.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Integer> entry = entries.next();

				// 循环遍历处理高亮字段的信息
				if (entry.getValue() != null) {
					String name = entry.getKey();
					HighlightField nameField = result.get(name);
					if (nameField != null) {
						// 取得定义的高亮标签
						Text[] titleTexts = nameField.fragments();
						// 为title串值增加自定义的高亮标签
						String value = "";
						for (Text text : titleTexts) {
							String Str = text.toString();
							//去掉content中的html标签
							if(name.equals("contents")){
								Str = HtmlUtils.Html2Text(Str);
							}
							value += Str;
						}
						setHighLightVuale(cb, name, value, String.class);// 截取字符串一般是针对字符类型进行的
					}
				}
			}
		}
	}

	private void setHighLightVuale(ContentBean cb, String name, String value, Class<?> type)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
		Method setMethod = cb.getClass().getMethod("set" + name, type);
		setMethod.invoke(cb, value);
	}

	@Override
	public void assembleQueryCondition(List<Map<String, Object>> orList, BoolQueryBuilder bqb) {
		for (Map<String, Object> map : orList) {
			if (map != null && map.size() > 0) {
				QueryBuilder qb = QueryBuilderUtils.assembleQuery(map);
				bqb.must(qb);
			}
		}
	}

}
