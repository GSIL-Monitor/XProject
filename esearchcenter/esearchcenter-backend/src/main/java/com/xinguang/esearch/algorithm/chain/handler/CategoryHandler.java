/**
 * 
 */
package com.xinguang.esearch.algorithm.chain.handler;

import com.xinguang.esearch.init.FilesHelper;

/**
 * 
 * ClassName: CategoryHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:27:02 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class CategoryHandler implements InterHandler {

    @Override
    public boolean isHit(String request) {		
    	return FilesHelper.getCATEGORYS().contains(request);
    }

}
