package org.dog.server.common;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: Odin
 * @Date: 2023/1/4 17:19
 * @Description:
 */
//@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {
//  @Override
//  protected void addInterceptors(InterceptorRegistry registry) {
//    // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
//    registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
//      .addPathPatterns("/**")
//      .excludePathPatterns("/", "/login", "/register", "/email", "/password/reset", "/file/download/**")
//      .excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html", "/favicon.ico");  // 排除 swagger拦截
//  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.
      addResourceHandler("/swagger-ui/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
      .resourceChain(false);
  }

  @Override
  protected void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/swagger-ui/", "/swagger-ui/index.html");
  }
}

