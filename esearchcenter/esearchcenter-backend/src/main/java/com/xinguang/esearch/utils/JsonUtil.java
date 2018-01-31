/**
 * 
 */
package com.xinguang.esearch.utils;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: JsonUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月7日 下午3:57:53 <br/>
 *
 * @author hzlengxing
 * @version
 * @since JDK 1.7
 */
public class JsonUtil {

    /**
     * 请求结果返回json
     * 
     * @param bool
     *            操作是否成功
     * @param info
     *            操作是否成功的辅助描述信息
     * @param content
     *            查询时为查询内容(json格式)，其他情况为null
     * @return 请求结果返回json
     */
    public static String getResultJsonStr(int code, String info, Object content) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", info);
        json.put("result", content);
        return json.toJSONString();
    }

    /**
     * getCommomJsonStr:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param map
     *            入参
     * @return json字符串
     * @since JDK 1.7
     */
    public static String getCommomJsonStr(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    /**
     * getUnitJsonStr:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @param key
     *            入参key
     * @param value
     *            入参value
     * @return json字符串
     * @since JDK 1.7
     */
    public static String getUnitJsonStr(String key, Object value) {
        JSONObject json = new JSONObject();
        json.put(key, value);
        return json.toJSONString();
    }
}
