package org.dog.server.common;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.dog.server.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Odin
 * @Date: 2023/1/11 10:27
 * @Description:
 */

@Component
@Slf4j
public class MyFilter implements Filter {


  private static volatile long startTime = System.currentTimeMillis();

  // 时间窗口
  private static final long windowTime = 1000L;

  // 1s只能通过200个请求
  private static final int door = 200;

  private static final AtomicInteger bear = new AtomicInteger(0);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    int count = bear.incrementAndGet();
    if (count == 1) {
      startTime = System.currentTimeMillis();
    }
    long now = System.currentTimeMillis();
//    log.info("拦截了请求， count： {}", count);
//    log.info("时间窗口: {}ms, count: {}", (now - startTime), count);
    if (now - startTime <= windowTime) {
      if (count > door) {  // 超过了阈值
        //   限流操作
        log.info("限流， 拦截了请求, count: {}", count);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().print(JSONUtil.toJsonStr(Result.error("402", "接口请求过于频繁")));
        return;
      }
    } else {
      startTime = System.currentTimeMillis();
      bear.set(1);
    }
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    filterChain.doFilter(servletRequest, servletResponse);
//    log.info("接口请求的路径：{}", request.getServletPath());
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }

}
