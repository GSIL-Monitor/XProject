/**
 * 
 */
package com.xinguang.xherald.es.service.impl;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinguang.xherald.es.beans.IndexFields;
import com.xinguang.xherald.es.beans.IndexPojo;
import com.xinguang.xherald.es.service.IndexService;

/**
 * 
 * ClassName: IndexServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月11日 下午3:26:09 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
@Service("IndexService")
public class IndexServiceImpl implements IndexService {
	/**
	 * LOGGER:logger对象(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);

	@Override
	public boolean createIndexMapping(Client client, IndexPojo index) {
		// 创建索引
		client.admin().indices().prepareCreate(index.getIndexName()).execute().actionGet();// indexName

		PutMappingResponse putMappingResponse = null;
		try {
			// 创建mapping
			XContentBuilder mappingBuilder = setXContentBuilder(index);
			// System.out.println("mapping:" + mappingBuilder.toString());
			putMappingResponse = client.admin().indices().putMapping(
					Requests.putMappingRequest(index.getIndexName()).type(index.getIndexType()).source(mappingBuilder))
					.get();
		} catch (Exception e) {
			// e.printStackTrace();
			LOGGER.error(" create index mapping failed. function={}, input={}, errorMessage={}", "createIndex", index,
					e);
			return false;
		}
		LOGGER.info("create index ===> indexName={}, indexType={},isSuccess={}", index.getIndexName(),
				index.getIndexType(), putMappingResponse.isAcknowledged());
		return putMappingResponse.isAcknowledged();
	}

	@Override
	public boolean deleteIndex(Client client, String index) {
		DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index).get();
		// System.out.println(deleteIndexResponse.isAcknowledged()); // true表示成功
		LOGGER.info("delete index ===> indexName={},isSuccess={}", index, deleteIndexResponse.isAcknowledged());
		return deleteIndexResponse.isAcknowledged();
	}

	private XContentBuilder setXContentBuilder(IndexPojo index) throws IOException {
		// 设置mapping
		XContentBuilder xContentBuilder = XContentFactory.jsonBuilder().startObject().startObject(index.getIndexType())// typeName
																														// 索引类型
				.startObject("properties");
		for (IndexFields indexFields : index.getIndexFields()) {
			xContentBuilder.startObject(indexFields.getFieldName()).field("type", indexFields.getDataType())
					.field("store", indexFields.getStore());
			if (indexFields.getAnalyzer().equals("not_analyzed")) {
				xContentBuilder.field("index", "not_analyzed");
			} else {
				xContentBuilder.field("index", "analyzed");
			}
			if (!indexFields.getAnalyzer().equals("not_analyzed")) {
				xContentBuilder.field("analyzer", indexFields.getAnalyzer());
			}

			xContentBuilder.endObject();
		}
		xContentBuilder.endObject().endObject().endObject();
		return xContentBuilder;
	}

	/**
	 * 获取ES服务器的所有索引（包括打开和关闭的索引）
	 * 
	 * @Description:
	 * @return
	 */
	@Override
	public MetaData getMetaData(Client client) {
		ClusterState state = client.admin().cluster().prepareState().execute().actionGet().getState();
		return state.getMetaData();
	}

	/**
	 * This verifies if a type exists on an index. It does however NOT verify if
	 * the type has a mapping defined.
	 */
	@Override
	public boolean indexTypeIsExist(Client client, String indexName, String type) {
		// TODO Auto-generated method stub
		return client.admin().indices().prepareTypesExists(indexName).setTypes(type).execute().actionGet().isExists();
	}

	/**
	 * verifies if an index exists. It does not verify if there is data in the
	 * index.
	 */
	@Override
	public boolean indexIsExist(Client client, String index) {
		// TODO Auto-generated method stub
		return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
	}

	/**
	 * TODO 简单描述该方法的实现功能（可选）.
	 * 
	 * @see com.xinguang.xherald.es.service.aop.IndexService#verifyMapping(org.elasticsearch.client.Client,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public boolean verifyMapping(Client client, String indexName, String type) {
		// TODO Auto-generated method stub
		IndexMetaData imd = null;
		try {
			ClusterState cs = client.admin().cluster().prepareState().setIndices(indexName).execute().actionGet()
					.getState();
			imd = cs.getMetaData().index(indexName);
		} catch (Exception e) {
			// If there is no index, there is no mapping either
			return false;
		}

		if (imd != null) {
			MappingMetaData mdd = imd.mapping(type);
			if (mdd != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean createIndex(Client client, IndexPojo index) {
		// TODO Auto-generated method stub
		CreateIndexResponse createIndexResponse = null;
		try {
			XContentBuilder mappingBuilder = setXContentBuilder(index);
			createIndexResponse = client.admin().indices().prepareCreate(index.getIndexName())
					.setSettings(
							Settings.settingsBuilder()
								.put("number_of_shards", index.getShards())// 分片数
								.put("number_of_replicas",index.getReplicas())//副本数
					 ) 
					.addMapping(index.getIndexType(),mappingBuilder)
					.execute().actionGet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(" create index failed. function={}, input={}, errorMessage={}", "createIndex", index,
					e);
			return false;
		}
		LOGGER.info("create index ===> indexName={}, indexType={},isSuccess={}", index.getIndexName(),
				index.getIndexType(), createIndexResponse.isAcknowledged());
		return createIndexResponse.isAcknowledged();
	}

	@Override
	public boolean deleteIndexDoc(Client client, String index, String type, String id) {
		DeleteResponse response;
    	try {
    	    response = client.prepareDelete(index, type, id).execute().actionGet();
    	} catch (Exception e) {
    	    LOGGER.error("delete document failed. input={},searchErrorMessage={}",id,e);
    	    return false;
    	}
    	/*if (!response.isFound()) {
    	    LOGGER.error("delete failed. no id found.id={}", id);
    	    return false;
    	}else{
    		return true;
    	}*/
    	return true;
	}
}
