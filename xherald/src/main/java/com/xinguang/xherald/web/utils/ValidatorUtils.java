package com.xinguang.xherald.web.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.xinguang.xherald.web.request.TopologyRequest;

/**
 * 
 * ClassName: ValidatorUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年10月11日 下午5:05:21 <br/>
 *
 * @author Administrator-lengxing
 * @version 
 * @since JDK 1.7
 */
@Component
public class ValidatorUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorUtils.class);
    
    @Value("${vhost}")
    private String vhost;
    
    @Value("${shards}")
    private int shards;
    
    @Value("${replicas}")
    private int replicas;

    /**
     * 
     * notNullParam:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author Administrator-lengxing
     * @param obj
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @since JDK 1.7
     */
    public static String notNullParam(Object obj) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String returnStr = null;
    	Field[] fields = obj.getClass().getDeclaredFields();        //获取实体类的所有属性，返回Field数组  
        StringBuffer stringBuffer = new StringBuffer();
        int flag = 0;
        stringBuffer.append("The following parameters is empty, please check:[");
        for(int j=0 ; j<fields.length ; j++){     //遍历所有属性
        		fields[j].setAccessible(true);
                String name = fields[j].getName();    //获取属性的名字                
                name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
                String type = fields[j].getGenericType().toString();    //获取属性的类型
                if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = obj.getClass().getMethod("get"+name);
                    String value = (String) m.invoke(obj);    //调用getter方法获取属性值
                    if(StringUtils.isBlank(value)){
                    	flag ++;
                       stringBuffer.append(name).append(",");
                    }
                    continue;
                }
                if(type.equals("class java.lang.Integer")){     
                    Method m = obj.getClass().getMethod("get"+name);
                    Integer value = (Integer) m.invoke(obj);
                    if(value == null){
                    	flag ++;
                        stringBuffer.append(name).append(",");
                    }
                    continue;
                }
                if(type.equals("class java.lang.Short")){     
                    Method m = obj.getClass().getMethod("get"+name);
                    Short value = (Short) m.invoke(obj);
                    if(value == null){
                    	flag ++;
                    	stringBuffer.append(name).append(",");                    
                    }
                    continue;
                }       
                if(type.equals("class java.lang.Double")){     
                    Method m = obj.getClass().getMethod("get"+name);
                    Double value = (Double) m.invoke(obj);
                    if(value == null){
                    	flag ++;
                    	stringBuffer.append(name).append(",");  
                    }
                    continue;
                }                  
                if(type.equals("class java.lang.Boolean")){
                    Method m = obj.getClass().getMethod("get"+name);    
                    Boolean value = (Boolean) m.invoke(obj);
                    if(value == null){
                    	flag ++;
                    	stringBuffer.append(name).append(",");
                    }
                    continue;
                }
                if(type.equals("class java.util.Date")){
                    Method m = obj.getClass().getMethod("get"+name);                    
                    Date value = (Date) m.invoke(obj);
                    if(value == null){
                    	flag ++;
                    	stringBuffer.append(name).append(",");
                    }
                    continue;
                }                              
            }
        if(flag > 0){
        	stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(), "]");//最后一个多余的逗号换成]
        	returnStr = stringBuffer.toString();
        	LOGGER.info("check request parameters,function={}, request={}, info={}", "notNullParam", obj, returnStr);
        }
		return returnStr;
    }
    
    public void setDefaultValue(TopologyRequest request){
		if(StringUtils.isBlank(request.getVhost())){
			request.setVhost(vhost);
		}
		if(request.getShards()==0){
			request.setShards(shards);
		}
		if(request.getReplicas()==0){
			request.setReplicas(replicas);
		}
	}
}