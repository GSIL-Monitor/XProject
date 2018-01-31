package com.xinguang.esearch.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * ClassName: BaseDomain <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月10日 上午10:01:57 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
public class BaseBean implements Serializable {
    private static final long serialVersionUID = 1159712018304699650L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
