/**
 * Project Name:esearchcenter-backend
 * File Name:QueryBuilderUtils.java
 * Package Name:com.xinguang.esearch.curd.impl
 * Date:2016年11月17日下午3:27:06
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.curd.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.xinguang.esearchcenter.request.LongRange;

/**
 * ClassName:QueryBuilderUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月17日 下午3:27:06 <br/>
 * @author   lengxing
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class QueryBuilderUtils {
	
	public static void assembleQueryByMust(Map<String, Object> condition, BoolQueryBuilder bqb) {
		// 遍历map
		Iterator<Entry<String, Object>> entries = condition.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Object> entry = entries.next();
			bqb.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
		}
	}
	
	public static void assembleQueryByShould(Map<String, Object> condition, BoolQueryBuilder bqb) {
		// 遍历map
		Iterator<Entry<String, Object>> entries = condition.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Object> entry = entries.next();
			// 设置查询条件
			bqb.should(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
		}
	}
	
	public static QueryBuilder assembleQuery(Map<String, Object> condition) {
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();

		if(condition.size() == 1){
			assembleQueryByTerms(condition, bqb);
		}else{
			assembleQueryByShould(condition, bqb);
		}
		return bqb;
	}
	
	public static void assembleQueryByRange(Map<String, LongRange> condition, BoolQueryBuilder bqb) {
		// 遍历map
		Iterator<Entry<String, LongRange>> entries = condition.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, LongRange> entry = entries.next();
			LongRange longRange = entry.getValue();
			if(longRange != null){
				switch (longRange.getType().getStatusCode()) {
				//设置上限
				case 1:
					setRangelt(entry, bqb);
					break;
				//设置下限
				case 2:
					setRangegt(entry, bqb);
					break;
				//设置上下限
				default:
					setRangeBoth(entry, bqb);
					break;
				}
			}
		}
	}
	
	public static void setRangelt(Entry<String, LongRange> entry, BoolQueryBuilder bqb) {
		bqb.must(QueryBuilders.rangeQuery(entry.getKey()).lt(entry.getValue().getMax()));
	}
	
	public static void setRangegt(Entry<String, LongRange> entry, BoolQueryBuilder bqb) {
		bqb.must(QueryBuilders.rangeQuery(entry.getKey()).gt(entry.getValue().getMin()));
	}
	
	public static void setRangeBoth(Entry<String, LongRange> entry, BoolQueryBuilder bqb) {
		bqb.must(QueryBuilders.rangeQuery(entry.getKey()).from(entry.getValue().getMin()).to(entry.getValue().getMax()));
	}
	
	private static void assembleQueryByTerms(Map<String, Object> condition, BoolQueryBuilder bqb){
		Iterator<Entry<String, Object>> entries = condition.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Object> entry = entries.next();
			bqb.must(QueryBuilders.termsQuery(entry.getKey(), entry.getValue()));
		}
	}

}

