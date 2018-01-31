package com.xinguang.mq.util;
/**
 * 
 * ClassName: XStringUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年3月3日 上午11:06:13 <br/>
 *
 * @author lengxing
 * @version 
 * @since JDK 1.7
 */
public class XStringUtils {
	public final static String TRUE = "true";
	
	//逗号
	public final static String COMMAS = ",";
	
	//竖杠(要转义)
	public final static String VERTICALBAR = "\\|";

	
	public static boolean checkifNotNull(String str){
		if(str != null && !"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	public static String[] splitCommas(String str){
		if(checkifNotNull(str)){
			return str.split(COMMAS);
		}else{
			return null;
		}
	}
	
	public static String[] splitCommon(String str,String separator){
		if(checkifNotNull(str)){
			return str.split(separator);
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		/*String[] paramList = XStringUtils.splitCommon("xsearch|xsearchTEST|/xsearch|lx-exchange|topic|lx-queue|lx-queue",
				XStringUtils.VERTICALBAR);
		for(String ss:paramList){
			System.out.println(ss);
		}*/
		
		Integer i = null;
		System.out.println(i.intValue());
	}

}

