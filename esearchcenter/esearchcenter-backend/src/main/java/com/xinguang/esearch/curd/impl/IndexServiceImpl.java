/**
 * 
 */
package com.xinguang.esearch.curd.impl;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.xinguang.esearch.curd.IndexService;

/**
 * ClassName: IndexServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 上午10:55:03 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Repository
public class IndexServiceImpl implements IndexService {	
    /**
     * LOGGER:logger对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);
	
    @Override
	public boolean createIndex(Client client,String index, String type) {

        try {
            //设置setting
            XContentBuilder settingsBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject("analysis")
                            .startObject("tokenizer")
                                .startObject("my_ngram_tokenizer")
                                    .field("type","edge_ngram")//edge_ngram
                                    .field("min_gram",1)
                                    .field("max_gram",10)
                                .endObject()
                            .endObject()
                            .startObject("analyzer")
                                .startObject("my_ngram_analyzer")
                                    .field("tokenizer","my_ngram_tokenizer")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            
          //创建索引
          client.admin().indices().prepareCreate(index).setSettings(settingsBuilder).execute().actionGet();//indexName
          
        } catch (IOException e1) {
            e1.printStackTrace();
            
        }

        PutMappingResponse putMappingResponse = null;
        try {
			//创建mapping
            XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
			        .startObject()
			            //设置mapping
			            .startObject(type) //typeName 索引类型
			            	.startObject("properties")
			                    .startObject("goodsId").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("goodsNo").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("picUrl").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("maxPrice").field("type", "double").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("minPrice").field("type", "double").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("brokerage").field("type", "double").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("platformBrokerage").field("type", "double").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("supplierBrokerage").field("type", "double").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("productName").field("type", "string").field("store", "yes").field("index", "analyzed").field("analyzer", "ik").endObject()
                                .startObject("brandNameCH").field("type", "string").field("store", "yes").field("index", "analyzed").field("analyzer", "ik").endObject()
                                .startObject("brandNameEN").field("type", "string").field("store", "yes").field("index", "analyzed").field("analyzer", "ik").endObject()
                                .startObject("firstCategoryName").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("secondCategoryName").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("defaultUrlMapping").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("supplierId").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("productIds").field("type", "string").field("store", "yes").field("index", "analyzed").field("analyzer", "ik").endObject()//需要逗号分词
                                .startObject("platformType").field("type", "integer").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("spreadPlanDetailId").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("spreadPlanDetailStatus").field("type", "integer").field("store", "yes").field("index", "not_analyzed").endObject()
                                //multi-fields
                                .startObject("shopId").field("type", "string").field("store", "yes").field("index", "not_analyzed")
                                     .startObject("fields")
                                            .startObject("raw")
                                                .field("type", "string")
                                                .field("store", "yes")
                                                .field("index", "analyzed")
                                                .field("analyzer", "my_ngram_analyzer")
                                                .field("search_analyzer", "keyword")
                                            .endObject()
                                     .endObject()
                                .endObject()
                                //multi-fields
                                .startObject("shopName").field("type", "string").field("store", "yes").field("index", "not_analyzed")
                                    .startObject("fields")
                                            .startObject("raw")
                                                .field("type", "string")
                                                .field("store", "yes")
                                                .field("index", "analyzed")
                                                .field("analyzer", "my_ngram_analyzer")
                                                .field("search_analyzer", "keyword")
                                            .endObject()
                                     .endObject()                               
                                .endObject()
                                
			                .endObject()
			            .endObject()
			        .endObject();
            putMappingResponse = client.admin().indices().putMapping(Requests.putMappingRequest(index).type(type).source(mappingBuilder)).actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        } 
		//System.out.println(putMappingResponse.isAcknowledged()); // true表示成功
        LOGGER.info("create index ===> indexName={}, indexType={},isSuccess={}", index,type,putMappingResponse.isAcknowledged());
        System.out.println( index+","+type+","+putMappingResponse.isAcknowledged());
        return putMappingResponse.isAcknowledged();
    }
    
    @Override
    public boolean createAudioIndex(Client client,String index, String type) {
        //创建索引
        client.admin().indices().prepareCreate(index).execute().actionGet();//indexName

        PutMappingResponse putMappingResponse = null;
        try {
            //创建mapping
            XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                        .startObject(type)  //typeName 索引类型
                            .startObject("properties")
                                .startObject("url").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("brand").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                                .startObject("category").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
                            .endObject()
                        .endObject()
                    .endObject();
            putMappingResponse = client.admin().indices().putMapping(Requests.putMappingRequest(index).type(type).source(mappingBuilder)).get();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        LOGGER.info("create index ===> indexName={}, indexType={},isSuccess={}", index,type,putMappingResponse.isAcknowledged());
        return putMappingResponse.isAcknowledged();
    }
	
    @Override
	public boolean deleteIndex(Client client, String index) {
        DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index).get();
		//System.out.println(deleteIndexResponse.isAcknowledged()); // true表示成功
        LOGGER.info("delete index ===> indexName={},isSuccess={}", index,deleteIndexResponse.isAcknowledged());
        return deleteIndexResponse.isAcknowledged();
    }

}
