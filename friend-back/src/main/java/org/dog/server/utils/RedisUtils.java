package org.dog.server.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 **/
@SuppressWarnings(value = {"unchecked"})
@Component
@Slf4j
public class RedisUtils {
  private static RedisTemplate<String, Object> staticRedisTemplate;

  private final RedisTemplate<String, Object> redisTemplate;

  public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  // Springboot启动成功之后会调用这个方法
  @PostConstruct
  public void initRedis() {
    // 初始化设置 静态staticRedisTemplate对象，方便后续操作数据
    staticRedisTemplate = redisTemplate;
  }

  /**
   * 缓存基本的对象，Integer、String、实体类等
   *
   * @param key   缓存的键值
   * @param value 缓存的值
   */
  public static <T> void setCacheObject(final String key, final T value) {
    staticRedisTemplate.opsForValue().set(key, value);
  }

  /**
   * 缓存基本的对象，Integer、String、实体类等
   *
   * @param key      缓存的键值
   * @param value    缓存的值
   * @param timeout  时间
   * @param timeUnit 时间颗粒度
   */
  public static <T> void setCacheObject(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
    staticRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
  }

  /**
   * 获得缓存的基本对象。
   *
   * @param key 缓存键值
   * @return 缓存键值对应的数据
   */
  public static <T> T getCacheObject(final String key) {
    return (T) staticRedisTemplate.opsForValue().get(key);
  }

  /**
   * 删除单个对象
   *
   * @param key
   */
  public static boolean deleteObject(final String key) {
    return Boolean.TRUE.equals(staticRedisTemplate.delete(key));
  }

  /**
   * 获取单个key的过期时间
   *
   * @param key
   * @return
   */
  public static Long getExpireTime(final String key) {
    return staticRedisTemplate.getExpire(key);
  }

  /**
   * 发送ping命令
   * redis 返回pong
   */
  public static void ping() {
    String res = staticRedisTemplate.execute(RedisConnectionCommands::ping);
    log.info("Redis ping ==== {}", res);
  }

}
