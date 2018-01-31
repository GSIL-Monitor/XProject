/**
 * 
 */
package com.xinguang.esearch.algorithm.chain.handler;

import com.xinguang.esearch.init.FilesHelper;

/**
 * ClassName: BrandHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:26:10 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class BrandHandler implements InterHandler {

    @Override
	public boolean isHit(String request) {
		// TODO Auto-generated method stub
        return FilesHelper.getBRANDS().contains(request);
    }

}
