/**
 * Project Name:esearchcenter-backend
 * File Name:TestHighLight.java
 * Package Name:com.xinguang.esearch.service.impl
 * Date:2016年11月10日上午10:01:42
 * Copyright (c) 2016, js.xiupin@list.nie.netease.com All Rights Reserved.
 *
*/

package com.xinguang.esearch.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ClassName:TestHighLight <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月10日 上午10:01:42 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TestHighLight {
	  private static final Logger LOGGER = LoggerFactory.getLogger(TestHighLight.class);
	
	private String clustername = "elasticsearch-yougou";

    private Integer estcpport = 9600;

    private String esdiscoveryhosts = "139.224.3.135:9600,139.196.173.48:9600";
    
	public void search() throws IOException {
        
        // 获取客户端
        Client client = setClient();    

        // 创建查询索引,参数productindex表示要查询的索引库为productindex
        SearchRequestBuilder searchRequestBuilder = client
                .prepareSearch("yougouindex");

        // 设置查询索引类型,setTypes("productType1", "productType2","productType3");
        // 用来设定在多个类型中搜索
        searchRequestBuilder.setTypes("yougoutype");

        // 设置查询类型 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询 2.SearchType.SCAN = 扫描查询,无序
        //searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

        // 设置查询关键词
        QueryBuilder qb = QueryBuilders.matchPhraseQuery("desc", "千岛湖");
        //QueryBuilder qb = QueryBuilders.matchPhraseQuery("desc", "漂流我描述");
        searchRequestBuilder
                .setQuery(qb);

        // 分页应用
        searchRequestBuilder.setFrom(0).setSize(60);

        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        
        //设置高亮显示
        searchRequestBuilder.addHighlightedField("desc", 7, 2, 0);
        //searchRequestBuilder.addHighlightedField("desc");
        //searchRequestBuilder.setHighlighterFragmentSize(10);
     /*   searchRequestBuilder.setHighlighterPreTags("<span style=\"color:red\">");
         searchRequestBuilder.setHighlighterPostTags("</span>");*/
         searchRequestBuilder.setHighlighterPreTags("<");
         searchRequestBuilder.setHighlighterPostTags(">");
        // 执行搜索,返回搜索响应信息
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        
        //获取搜索的文档结果
        SearchHits searchHits = response.getHits();
        SearchHit[] hits = searchHits.getHits();
        //SimpleFragmenter
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < hits.length; i++) {
            SearchHit hit = hits[i];
            //将文档中的每一个对象转换json串值
            String json = hit.getSourceAsString();
            //将json串值转换成对应的实体对象
            //Product product = mapper.readValue(json, Product.class);  
            
            //获取对应的高亮域
            Map<String, HighlightField> result = hit.highlightFields();
            
            //从设定的高亮域中取得指定域
            HighlightField titleField = result.get("desc");
            
            //取得定义的高亮标签
            Text[] titleTexts =  titleField.fragments();

            
            //为title串值增加自定义的高亮标签
            String title = "";  
            for(Text text : titleTexts){
                  title += text; 
            }
            //将追加了高亮标签的串值重新填充到对应的对象
            //product.setDescHighlight(title);;
            //打印高亮标签追加完成后的实体对象
            System.out.println(title);
        }
        System.out.println("search success ..");

    }
	
	public static void main(String[] args) {
		TestHighLight th = new TestHighLight();
		try {
			th.search();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	private Client setClient(){
		 Settings settings = Settings.settingsBuilder().put("cluster.name", clustername).build();
         TransportClient tcpClient = TransportClient.builder().settings(settings).build();
         String[] hosts = esdiscoveryhosts.split(",|，");
         for (String host : hosts) {
             String[] hostparts = host.split(":");
             String hostname = hostparts[0];
             int hostport = estcpport;
             if (hostparts.length == 2) {
                 hostport = Integer.parseInt(hostparts[1]);
             }
             try {
                 tcpClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname),
                         hostport));
             } catch (UnknownHostException e) {
                 LOGGER.error("build TcpClient error for invalide host. host:port={}:{}", hostname, hostport);
             }
         }
         return tcpClient;
	}
}

