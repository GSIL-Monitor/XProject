/**
 * Project Name:esearchcenter-backend
 * File Name:Config.java
 * Package Name:com.xinguang
 * Date:2016年10月20日下午4:36:28
 * Copyright (c) 2016, All Rights Reserved.
 *
*/

package com.xinguang.esearch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * ClassName:Config <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年10月20日 下午4:36:28 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
@Configuration
@PropertySource({ "file:${configlocation}/config/url.properties" })
//@PropertySource({"file:D:/workspace/esearchcenter/esearchcenter-backend/src/main/resources/config/dev/url.properties"})
@ImportResource({
        "classpath:config/dubbo-provider.xml"
})
public class BackendConfig {
	Logger logger = LoggerFactory.getLogger(BackendConfig.class);
}

