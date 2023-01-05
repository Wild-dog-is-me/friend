package org.dog.server.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author: Odin
 * @Date: 2023/1/4 17:19
 * @Description:
 */
@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
    corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
    corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
    corsConfiguration.setMaxAge(1800L);
    source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
    return new CorsFilter(source);
  }
}
