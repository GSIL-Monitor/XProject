package com.xinguang.xherald.es.beans;


import java.util.List;

import com.xinguang.xherald.common.beans.BaseBean;

/**
 * 索引字段的定义
 * ClassName: IndexFields <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年7月11日 下午3:29:52 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class IndexPojo extends BaseBean{
	
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -872225792690577759L;
	private String indexName;
	private String indexType;
	private int shards;
	private int replicas;
	private List<IndexFields> indexFields;
	/**
	 * indexName.
	 *
	 * @return  the indexName
	 * @since   JDK 1.7
	 */
	public String getIndexName() {
		return indexName;
	}
	/**
	 * shards.
	 *
	 * @return  the shards
	 * @since   JDK 1.7
	 */
	public int getShards() {
		return shards;
	}
	/**
	 * replicas.
	 *
	 * @return  the replicas
	 * @since   JDK 1.7
	 */
	public int getReplicas() {
		return replicas;
	}
	/**
	 * indexFields.
	 *
	 * @return  the indexFields
	 * @since   JDK 1.7
	 */
	public List<IndexFields> getIndexFields() {
		return indexFields;
	}
	/**
	 * indexName.
	 *
	 * @param   indexName    the indexName to set
	 * @since   JDK 1.7
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	/**
	 * shards.
	 *
	 * @param   shards    the shards to set
	 * @since   JDK 1.7
	 */
	public void setShards(int shards) {
		this.shards = shards;
	}
	/**
	 * replicas.
	 *
	 * @param   replicas    the replicas to set
	 * @since   JDK 1.7
	 */
	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}
	/**
	 * indexFields.
	 *
	 * @param   indexFields    the indexFields to set
	 * @since   JDK 1.7
	 */
	public void setIndexFields(List<IndexFields> indexFields) {
		this.indexFields = indexFields;
	}
	/**
	 * indexType.
	 *
	 * @return  the indexType
	 * @since   JDK 1.7
	 */
	public String getIndexType() {
		return indexType;
	}
	/**
	 * indexType.
	 *
	 * @param   indexType    the indexType to set
	 * @since   JDK 1.7
	 */
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

}
