/**
 * 
 */
package com.xinguang.esearch.algorithm.chain.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * ClassName: UrlHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午2:28:44 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
public class UrlHandler implements InterHandler{

    @Override
    public boolean isHit(String request) {		
    	//url 正则表达式
    	String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
    	
    	Pattern patt = Pattern. compile(regex );
    	Matcher matcher = patt.matcher(request);
    	return matcher.matches();
    }
    
    public static void main(String[] args) {
        UrlHandler urlHandler = new UrlHandler();
        System.out.println(urlHandler.isHit("http://www.xiupin.com/"));
    }
}
