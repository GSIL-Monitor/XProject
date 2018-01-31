/**
 * 
 */
package com.xinguang.esearch.curd.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.xinguang.esearch.beans.GeoCondition;
import com.xinguang.esearch.beans.PoiPojo;
import com.xinguang.esearch.beans.ScaleCondition;
import com.xinguang.esearch.curd.PoiQueryService;
import com.xinguang.esearch.utils.JavaUtil;

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
public class PoiQueryServiceImpl implements PoiQueryService {
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * 
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiQueryServiceImpl.class);
    
    //@Value("${spotType}")
    private int[] spotType;
    
    //@Value("${facilityType}")
    private int[] facilityType;
    
    //@Value("${shopType}")
    private int[] shopType;

    private void setSearchRequestBuilder(GeoCondition gc, SearchRequestBuilder srb) {
        QueryBuilder queryBuilder = QueryBuilders.geoDistanceRangeQuery("location")
                .point(gc.getLatitude(), gc.getLongitude())
                // 注意纬度在前，经度在后
                .from(gc.getStartDistance()).to(gc.getEndDistance()).includeLower(true).includeUpper(false)
                .optimizeBbox(gc.getOptimizeBbox()).geoDistance(gc.getGeoDistance()); // Or GeoDistance.PLANE
        
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("scenicRegionId", gc.getScenicRegionId()))
                .filter(QueryBuilders.termQuery("deleted", gc.getDeleted()))
                .filter(queryBuilder);
        
        //数据来源限制条件
        if(gc.getSourceType()>0){
            boolQuery.filter(QueryBuilders.termQuery("type", gc.getSourceType()));
            
            //type和status的对照关系
            int status = getStatusByType(gc.getSourceType());
            if(status > 0){
                boolQuery.filter(QueryBuilders.termQuery("status", status));
                if(status == 5){//店铺，isFacadeAddr只查询值为1
                    boolQuery.filter(QueryBuilders.termQuery("isFacadeAddr", 1)); 
                }
            }            
        }
        
        srb.setQuery(boolQuery);
    }

    private void setSearchRequestBuilderLimit(GeoCondition gc, SearchRequestBuilder srb) {
        srb.setFrom(gc.getOffset()).setSize(gc.getSize());
        srb.addSort(setSort(gc));
    }

    private void setScaleSearchRequestBuilder(ScaleCondition sc, SearchRequestBuilder srb) {
        QueryBuilder geoBuilder = QueryBuilders.geoDistanceRangeQuery("location")
                .point(sc.getLatitude(), sc.getLongitude())
                // 注意纬度在前，经度在后
                .from(sc.getStartDistance()).to(sc.getEndDistance()).includeLower(true).includeUpper(false)
                .optimizeBbox(sc.getOptimizeBbox()).geoDistance(sc.getGeoDistance()); // Or GeoDistance.PLANE

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("scale", sc.getScale()))
                .filter(QueryBuilders.termQuery("deleted", sc.getDeleted()))
                .filter(QueryBuilders.termQuery("status", sc.getStatus()))
                .filter(geoBuilder);

        srb.setQuery(boolQuery);
        
        srb.addSort(setScaleSort(sc));
        srb.setSize(1000);// 目前限定返回结果值为1000
    }

    private GeoDistanceSortBuilder setSort(GeoCondition gc) {
        GeoDistanceSortBuilder sort = new GeoDistanceSortBuilder("location");
        sort.unit(DistanceUnit.KILOMETERS);// 距离单位公里
        sort.order(SortOrder.ASC);
        sort.point(gc.getLatitude(), gc.getLongitude());// 注意纬度在前，经度在后
        return sort;
    }

    private GeoDistanceSortBuilder setScaleSort(ScaleCondition sc) {
        GeoDistanceSortBuilder sort = new GeoDistanceSortBuilder("location");
        sort.unit(DistanceUnit.KILOMETERS);// 距离单位公里
        sort.order(SortOrder.ASC);
        sort.point(sc.getLatitude(), sc.getLongitude());// 注意纬度在前，经度在后
        return sort;
    }

    @Override
    public List<PoiPojo> getNearByPOI(Client client, String index, String type, GeoCondition gc) {
        // TODO Auto-generated method stub

        // 设置查询条件
        SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
        setSearchRequestBuilder(gc, srb);
        setSearchRequestBuilderLimit(gc, srb);

        // 查询
        SearchResponse searchResponse;
        try {
            searchResponse = srb.execute().actionGet();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error(" search failed. function={}, input={}, searchErrorMessage={}", "getNearByPOI", srb.toString(),
                    e);
            return null;
        }

        // 查询结果处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        //System.out.println("该位置的附近景点有(" + hits.getTotalHits() + "个)：");

        List<PoiPojo> resList = new ArrayList<PoiPojo>();
        for (SearchHit hit : searchHists) {
            resList.add(getPoiHit(hit));

            // -----------------------------start------------------------------------------------
            String scenicRegionName = (String) hit.getSource().get("scenicRegionName");
            String name = (String) hit.getSource().get("name");
            System.out.println(scenicRegionName + "距离当前位置" + getPoiHit(hit).getDistance()
                    + DistanceUnit.KILOMETERS.toString() + "---" + name);
            // ------------------------------end-----------------------------------------------
        }

        LOGGER.info("function={}, input={},searchResultList={}", "getNearByPOI", srb.toString(), resList.toString());
        return resList;
    }

    @Override
    public List<PoiPojo> getNearByScale(Client client, String index, String type, ScaleCondition sc) {
        // TODO Auto-generated method stub

        // 设置查询条件
        SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);
        setScaleSearchRequestBuilder(sc, srb);

        // 查询
        SearchResponse searchResponse;
        try {
            searchResponse = srb.execute().actionGet();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error(" search failed. function={}, input={}, searchErrorMessage={}", "getNearByScale",
                    srb.toString(), e);
            return null;
        }

        // 查询结果处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHists = hits.getHits();
        System.out.println("该位置的附近景点有(" + hits.getTotalHits() + "个)：");

        List<PoiPojo> resList = new ArrayList<PoiPojo>();
        for (SearchHit hit : searchHists) {
            resList.add(getPoiHit(hit));

            // -----------------------------start------------------------------------------------
            String scenicRegionName = (String) hit.getSource().get("scenicRegionName");
            String name = (String) hit.getSource().get("name");
            System.out.println(scenicRegionName + "距离当前位置" + getPoiHit(hit).getDistance()
                    + DistanceUnit.KILOMETERS.toString() + "---" + name);
            // ------------------------------end-----------------------------------------------
        }

        LOGGER.info("function={}, input={}, searchResultList={}", "getNearByScale", srb.toString(), resList.toString());
        return resList;
    }

    @Override
    public long getTotalCount(Client client, String index, String type, GeoCondition gc) {
        // TODO Auto-generated method stub
        SearchRequestBuilder srb = client.prepareSearch(index).setTypes(type);

        setSearchRequestBuilder(gc, srb);

        SearchResponse searchResponse;
        try {
            searchResponse = srb.execute().actionGet();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error(" search failed. function={}, input={}, searchErrorMessage={}", "getNearByPOI", srb.toString(),
                    e);
            return 0;
        }
        return searchResponse.getHits().getTotalHits();
    }

    private static PoiPojo getPoiHit(SearchHit hit) {
        PoiPojo poiPojo = new PoiPojo();

        // 设置data
        poiPojo.setData(hit.getSource());

        // 设置distance
        // 1）获取距离值，并保留两位小数点
        BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
        hit.getSource().put("geoDistance", geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));

        // 2）在创建MAPPING的时候，属性名的不可为geoDistance。
        poiPojo.setDistance(hit.getSource().get("geoDistance").toString());

        return poiPojo;
    }
    
    private int getStatusByType(int sourceType){
        if(JavaUtil.useLoopInt(spotType, sourceType)){//spot 1:启用       
            return 1;
        }else if(JavaUtil.useLoopInt(shopType, sourceType)){//shop 5.审核通过
            return 5;
        }else{
            return -1;
        }
    }

}
