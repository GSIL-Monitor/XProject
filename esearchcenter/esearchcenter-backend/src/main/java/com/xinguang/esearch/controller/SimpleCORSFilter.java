package com.xinguang.esearch.controller;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * CORS协议:配置目标服务器的CORS 
 * 增加一个Filter，在HTTP响应中增加一些头信息，我们通过SimpleCORSFilter来实现
 * SimpleCORSFilter在响应中增加了一些Access-Control-*头。在上面的例子中，设置的头信息表示允许来自任何域的客户端访问POST, GET, OPTIONS和 DELETE请求，请求的结果将缓存至多3600秒。
 * 当然，这只是一个很简单的跨域支持filter，大家可以根据需要进行更多的设置，比如只支持来自特定域的请求访问特定的资源。
 * ClassName: SimpleCORSFilter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月21日 下午4:24:11 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
//@Component
public class SimpleCORSFilter {//implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
        HttpServletResponse request = (HttpServletResponse) req;
        HttpServletResponse response = (HttpServletResponse) res;

     /*   //设置需要跨域请求的域名集合
        List<String> domainList = new ArrayList<>();
        domainList.add("http://www.domain1.com");
        domainList.add("http://www.domain2.com");
        domainList.add("http://localhost:8088");
        String requestDomain = request.getHeader("origin");
        if(domainList.contains(requestDomain)){
            response.addHeader("Access-Control-Allow-Origin", requestDomain);
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");//DELETE
            response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        }*/
        
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");//DELETE
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        
        
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}