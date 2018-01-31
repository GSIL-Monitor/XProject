package com.xinguang.esearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ClassName: Swagger2 <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年5月9日 下午1:49:09 <br/>
 *
 * @author hzlengxing
 * @version 
 * @since JDK 1.7
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	/**
	 * BASEPACKAGE:监控路径(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
    private static final String BASEPACKAGE = "com.xinguang.esearch.controller";

    @Value("${swagger.show}")
    private boolean swaggerShow;
    
    /**
     * createRestApi:创建Swagger2的配置类(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @return 配置类对象
     * @since JDK 1.7
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(this.swaggerShow)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASEPACKAGE))//要监控的api，默认 对所有api进行监控,RequestHandlerSelectors.any()
                .paths(PathSelectors.any()) //对所有路径进行监控
                .build();
    }

    /**
     * apiInfo:(这里用一句话描述这个方法的作用). <br/>
     * TODO(这里描述这个方法适用条件 – 可选).<br/>
     * TODO(这里描述这个方法的执行流程 – 可选).<br/>
     * TODO(这里描述这个方法的使用方法 – 可选).<br/>
     * TODO(这里描述这个方法的注意事项 – 可选).<br/>
     *
     * @author hzlengxing
     * @return ApiInfo：api文档注释部分
     * @since JDK 1.7
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("欢迎来到新光互联，请关注：http://www.neoglory.cn/")
                .termsOfServiceUrl("http://www.neoglory.cn/")
                .contact(
                        new Contact(
                                "共享技术中心", 
                                "http://springfox.github.io/springfox/",
                                "hzlengxing@corp.netease.com")
                        )
                .version("1.0")
                .license("The Apache License, Version 2.0")
                .build();
    }

}