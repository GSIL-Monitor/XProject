/**
 * Project Name:esearch
 * File Name:YougoPoiService.java
 * Package Name:com.xinguang.esearch.dubbo
 * Date:2016年9月29日下午5:34:05
 *
*/

package com.xinguang.esearchcenter.inter;

import com.xinguang.esearchcenter.request.GeoRequest;

/**
 * ClassName:YougouGeoService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年9月29日 下午5:34:05 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 * @see
 */
public interface YougoPoiService {
    String PoiSearch(String index, String type, GeoRequest request);
}
