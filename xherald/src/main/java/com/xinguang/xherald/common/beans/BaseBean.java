package com.xinguang.xherald.common.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 
 * ClassName: BaseBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年9月13日 下午5:04:13 <br/>
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
