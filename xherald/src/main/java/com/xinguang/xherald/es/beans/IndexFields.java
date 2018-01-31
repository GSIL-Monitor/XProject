
package com.xinguang.xherald.es.beans;

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
public class IndexFields extends BaseBean{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 391891361682892291L;
	private String fieldName;
	private String dataType;
	private String store;
	private String analyzer;
	/**
	 * fieldName.
	 *
	 * @return  the fieldName
	 * @since   JDK 1.7
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * dataType.
	 *
	 * @return  the dataType
	 * @since   JDK 1.7
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * store.
	 *
	 * @return  the store
	 * @since   JDK 1.7
	 */
	public String getStore() {
		return store;
	}
	/**
	 * analyzer.
	 *
	 * @return  the analyzer
	 * @since   JDK 1.7
	 */
	public String getAnalyzer() {
		return analyzer;
	}
	/**
	 * fieldName.
	 *
	 * @param   fieldName    the fieldName to set
	 * @since   JDK 1.7
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * dataType.
	 *
	 * @param   dataType    the dataType to set
	 * @since   JDK 1.7
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * store.
	 *
	 * @param   store    the store to set
	 * @since   JDK 1.7
	 */
	public void setStore(String store) {
		this.store = store;
	}
	/**
	 * analyzer.
	 *
	 * @param   analyzer    the analyzer to set
	 * @since   JDK 1.7
	 */
	public void setAnalyzer(String analyzer) {
		this.analyzer = analyzer;
	}	
}
