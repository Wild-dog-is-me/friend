package org.dog.server.common;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.dog.server.service.IUserService;
import org.dog.server.utils.RedisUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Odin
 * @Date: 2023/1/8 11:13
 * @Description:
 */

@Component
@Slf4j
public class InitRunner implements ApplicationRunner {

  @Resource
  private IUserService userService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    ThreadUtil.execAsync(() -> {
      try {
        RedisUtils.ping();    // redis数据探测，初始化连接
        userService.getById(1); // 数据库探测，帮我在项目启动的时候查询一次数据库，防止数据库的懒加载
        log.info("启动项目web请求查询成功");   // 发送一次异步的web请求，来初始化 tomcat连接
        HttpUtil.get("http://localhost:10000/");
        log.info("启动项目tomcat连接查询成功");   // 发送一次异步的web请求，来初始化 tomcat连接
      } catch (Exception e) {
        log.warn("启动优化失败", e);
      }
    });
  }

}
