package org.dog.server.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dog.server.common.Constants;
import org.dog.server.common.enums.EmailCodeEnum;
import org.dog.server.controller.domain.LoginDTO;
import org.dog.server.controller.domain.UserRequest;
import org.dog.server.entity.User;
import org.dog.server.exception.ServiceException;
import org.dog.server.mapper.UserMapper;
import org.dog.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dog.server.utils.EmailUtils;
import org.dog.server.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.dog.server.common.Constants.DATE_RULE_YYYYMMDD;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Odin
 * @since 2023-01-04
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

  private static final long TIME_IN_MS5 = 5 * 60 * 1000;  // 表示5分钟的毫秒数

  @Resource
  private EmailUtils emailUtils;

  /**
   * 用户登陆
   * @param user
   * @return
   */
  @Override
  public LoginDTO login(UserRequest user) {
    User dbUser = null;
    try {
      dbUser = getOne(new UpdateWrapper<User>().eq("username", user.getUsername())
        .or().eq("email", user.getUsername()));
    } catch (Exception e) {
      throw new RuntimeException("系统异常");
    }
    if (dbUser == null) {
      throw new ServiceException("未找到用户");
    }
    String securePass = SaSecureUtil.aesEncrypt(Constants.PASSWORD_KEY, user.getPassword());
    if (!securePass.equals(dbUser.getPassword())) {
      throw new ServiceException("用户名或密码错误");
    }
    StpUtil.login(dbUser.getUid());
    StpUtil.getSession().set(Constants.LOGIN_USER_KEY, dbUser);
    SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
    String tokenValue = StpUtil.getTokenValue();
    log.info("token信息:{}", tokenInfo);
    return LoginDTO.builder().user(dbUser).token(tokenValue).build();
  }

  @Override
  public User register(UserRequest user) {
    String key = Constants.EMAIL_CODE + EmailCodeEnum.REGISTER.getValue() + user.getEmail();
    String emailCode = user.getEmailCode();
    validateEmail(key ,emailCode);
    try {
      User saveUser = new User();
      BeanUtils.copyProperties(user, saveUser);
      return saveUser(saveUser);
    } catch (Exception e) {
      throw new RuntimeException("数据库异常",e);
    }
  }

  /**
   * 邮箱发送验证码
   * @param email
   * @param type
   */
  @Override
  public void sendEmail(String email, String type) {
    String emailPrefix = EmailCodeEnum.getValue(type);
    if (StrUtil.isBlank(emailPrefix)) {
      throw new ServiceException("不支持的邮箱验证类型");
    }
    String key = Constants.EMAIL_CODE + emailPrefix + email;
    Long expireTime = RedisUtils.getExpireTime(key);
    // 限制超过1分钟才可以继续发送邮件，判断过期时间是否大于4分钟
    if (expireTime != null && expireTime > 4 * 60) {
      throw new ServiceException("发送邮箱验证过于频繁");
    }

    Integer code = Integer.valueOf(RandomUtil.randomNumbers(6));
    log.info("本次验证的code是：{}", code);
    String context = "<b>尊敬的用户：</b><br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，" +
      "Odin交友网提醒您本次的验证码是：<b>{}</b>，" +
      "有效期5分钟。<br><br><br><b>Odin交友网</b>";
    String html = StrUtil.format(context, code);
    User dbUser = getOne(new QueryWrapper<User>().eq("email", email));
    if (EmailCodeEnum.REGISTER.equals(EmailCodeEnum.getEnum(type))) {
      if (dbUser != null) {
        throw new ServiceException("邮箱已经注册！");
      }
    } else if (EmailCodeEnum.RESET_PASSWORD.equals(EmailCodeEnum.getEnum(type))) {
      if (dbUser == null) {
        throw new ServiceException("当前用户不存在");
      }
    }
    // 线程异步执行，防止网络阻塞
    ThreadUtil.execAsync(() -> {
      emailUtils.sendHtml("【Odin交友网】验证提醒", html, email);
      RedisUtils.setCacheObject(key, code, TIME_IN_MS5, TimeUnit.MILLISECONDS);
    });
  }

  /**
   * 重置密码功能
   * @param userRequest
   * @return
   */
  @Override
  public String passwordReset(UserRequest userRequest) {
    String email = userRequest.getEmail();
    String key = Constants.EMAIL_CODE + EmailCodeEnum.RESET_PASSWORD.getValue() + email;
    User dbUser = getOne(new UpdateWrapper<User>().eq("email", email));
    if (dbUser == null) {
      throw new ServiceException("未找到当前用户");
    }
    validateEmail(key,userRequest.getEmailCode());
    String newPass = "admin";
    dbUser.setPassword(newPass);
    try {
      updateById(dbUser);   // 设置到数据库
    } catch (Exception e) {
      throw new RuntimeException("重置失败", e);
    }
    return newPass;
  }

  /**
   * 用户注册
   * @param user 前端传入用户
   * @return 注册用户信息
   */
  private User saveUser(User user) {
    User dbUser = getOne(new UpdateWrapper<User>().eq("username", user.getUsername()));
    if (dbUser != null) {
      throw new ServiceException("用户已存在");
    }
    if (StrUtil.isBlank(user.getName())) {
      String name = Constants.USER_NAME_PREFIX + DateUtil.format(new Date(), DATE_RULE_YYYYMMDD)
        + RandomUtil.randomString(4);
      user.setName(name);
    }
    if (StrUtil.isBlank(user.getPassword())) {
      user.setPassword("admin"); // 设置默认密码
    }
    // 加密用户密码
    user.setPassword(SaSecureUtil.aesEncrypt(Constants.PASSWORD_KEY, user.getPassword()));
    // 设置用户唯一uid
    user.setUid(IdUtil.fastSimpleUUID());
    boolean save = save(user);
    if (!save) {
      throw new RuntimeException("注册失败");
    }
    return user;
  }

  /**
   * 邮箱验证码验证功能
   * @param emailKey Redis中存储的key
   * @param emailCode 前端传入的验证码
   */
  private void validateEmail(String emailKey,String emailCode) {
    // 从Redis中获取验证码
    Integer code = RedisUtils.getCacheObject(emailKey);
    if (code == null) {
      throw new ServiceException("验证码失效");
    }
    if (!emailCode.equals(code.toString())) {
      throw new ServiceException("验证码错误");
    }
    // 验证通过后立即删除
    RedisUtils.deleteObject(emailKey);
  }
}
