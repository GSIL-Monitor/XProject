package com.xinguang.xherald.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({ "file:${configlocation}/config/url.properties" })
//@PropertySource({ "file:E:/Projects/search/xherald/src/main/resources/config/dev/url.properties" })
public class BaseConfig {
	
	static Logger logger=LoggerFactory.getLogger("BaseConfig");
	@Autowired
	Environment env;
	
    public  String getString(String str) {
         return env.getProperty(str);
    }

}