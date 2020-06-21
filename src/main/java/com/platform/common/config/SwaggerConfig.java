package com.platform.common.config;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jianghy
 * @Description: swagger配置文件
 * @date 2020/4/23 16:28
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", name = "swagger-ui-open", havingValue = "true")
public class SwaggerConfig {
    /**
     * @Description: 启动服务访问：http://服务名:端口号/swagger-ui.html
     * @param
     * @return springfox.documentation.spring.web.plugins.Docket
     * @throws
     * @author jianghy
     * @date 2020/4/23 16:30
     */

    @Value("${swagger.host}")
    private String swaggerHost;

    @Bean
    public Docket createRestApi(){
        //添加header参数
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("token").defaultValue("cd8dd5d4fe684becaa2741ad0cd219f8").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的token参数
        pars.add(ticketPar.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerHost)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }


    /**
     * @Description: api信息
     * @param
     * @return springfox.documentation.service.ApiInfo
     * @throws
     * @author jianghy
     * @date 2020/4/23 16:32
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("api开放接口")
                .description("Rest API接口")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

}
