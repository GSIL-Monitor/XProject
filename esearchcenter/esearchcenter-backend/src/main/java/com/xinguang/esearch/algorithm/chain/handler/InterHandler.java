/**
 * 
 */
package com.xinguang.esearch.algorithm.chain.handler;

/**
 * 
 * ClassName: InterHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:27:23 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public interface InterHandler {
	
	/**
	 * 
	 * isHit:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author hzlengxing
	 * @param request 入参
	 * @return 是否命中
	 * @since JDK 1.7
	 */
    boolean isHit(String request);

}
