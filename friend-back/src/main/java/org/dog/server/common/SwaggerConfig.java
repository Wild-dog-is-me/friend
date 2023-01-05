package org.dog.server.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author: Odin
 * @Date: 2023/1/4 17:14
 * @Description:
 */
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {


  @Bean
  public Docket createRestAPI() {
    return new Docket(DocumentationType.OAS_30)
      .apiInfo(apiInfo())
      .select()
      .apis(RequestHandlerSelectors.basePackage("org.dog.server.controller"))
      .paths(PathSelectors.any())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("RestAPI接口文档")
      .description("Restful 后台接口汇总")
      .contact(new Contact("Odin", "https://5w.fit/fogXs", "1427774041@qq.com"))
      .version("1.0")
      .build();
  }

}
