package org.dog.server.common;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Odin
 * @Date: 2023/1/10 13:25
 * @Description:
 */
@Configuration
public class SaTokenConfigure {
  // Sa-Token 整合 jwt (Simple 简单模式)
  @Bean
  public StpLogic getStpLogicJwt() {
    return new StpLogicJwtForSimple();
  }
}

