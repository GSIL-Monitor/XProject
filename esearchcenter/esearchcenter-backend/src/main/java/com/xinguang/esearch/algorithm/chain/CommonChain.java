package com.xinguang.esearch.algorithm.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConHandler:责任链模式中的责任类 负责处理业务逻辑
 * ClassName: CommonChain <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午1:59:30 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class CommonChain{
    /**
     * logger:日志对象(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonChain.class);
    
    /**
     * name:每个责任节点应该有个名字(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private String name;
    
    /**
     * nextHandler:责任链的下一个节点(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private CommonChain nextHandler;
    
    /**
     * HANDLER:TODO(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final String HANDLER = "Handler";

    /**
     * ISHIT:TODO(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final String ISHIT = "isHit";

    /**
     * PACKAGEPATH:反射对象所在的path(用一句话描述这个变量表示什么).
     * @since JDK 1.7
     */
    private static final String PACKAGEPATH = "com.netease.esearch.algorithm.chain.handler";

    /**
     * name.
     *
     * @return  the name
     * @since   JDK 1.7
     */
    public String getName() {
        return name;
    }

    /**
     * nextHandler.
     *
     * @return  the nextHandler
     * @since   JDK 1.7
     */
    public CommonChain getNextHandler() {
        return nextHandler;
    }

    /**
     * name.
     *
     * @param   name    the name to set
     * @since   JDK 1.7
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * nextHandler.
     *
     * @param   nextHandler    the nextHandler to set
     * @since   JDK 1.7
     */
    public void setNextHandler(CommonChain commonChain) {
        this.nextHandler = commonChain;
    }

    public CommonChain(String name) {
        this.setName(name);  
    }
    
    public CommonChain() {
    }
    
    /**
     * TODO 简单描述该方法的实现功能（可选）.
     * @param abstractChain 
     * @see com.netease.esearch.algorithm.chain.AbstractChain#setNextHandler(com.netease.esearch.algorithm.chain.AbstractChain)
     */
    public void setNextHandlerOfChain(CommonChain commonChain) {
        this.setNextHandler(commonChain);
    }

    public String getNextName() {
        return this.getNextHandler().getName();
    }
}