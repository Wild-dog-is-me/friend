package org.dog.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dog.server.common.Constants;
import org.dog.server.controller.domain.UserRequest;
import org.dog.server.entity.User;
import org.dog.server.exception.ServiceException;
import org.dog.server.mapper.UserMapper;
import org.dog.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dog.server.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

  private static final Map<String, Long> CODE_MAP = new ConcurrentHashMap<>();

  private static final long TIME_IN_MS5 = 5 * 60 * 1000;  // 表示5分钟的毫秒数

  @Resource
  private EmailUtils emailUtils;

  @Override
  public User login(UserRequest user) {
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
    if (!user.getPassword().equals(dbUser.getPassword())) {
      throw new ServiceException("用户名或密码错误");
    }
    return dbUser;
  }

  @Override
  public User register(UserRequest user) {
    String emailCode = user.getEmailCode();
    validateEmail(emailCode);
    try {
      User saveUser = new User();
      BeanUtils.copyProperties(user, saveUser);
      return saveUser(saveUser);
    } catch (Exception e) {
      throw new RuntimeException("数据库异常",e);
    }
  }

  @Override
  public void sendEmail(String email, String type) {
    String code = RandomUtil.randomString(6);
    log.info("本次验证的code是：{}", code);
    String context = "<b>尊敬的用户：</b><br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，" +
      "Odin交友网提醒您本次的验证码是：<b>{}</b>，" +
      "有效期5分钟。<br><br><br><b>Odin交友网</b>";
    String html = StrUtil.format(context, code);
    if ("REGISTER".equals(type) ) {
      User dbUser = getOne(new QueryWrapper<User>().eq("email", email));
      if (dbUser != null) {
        throw new ServiceException("邮箱已经注册！");
      }
    }

    ThreadUtil.execAsync(() -> {
      emailUtils.sendHtml("【Odin交友网】验证提醒", html, email);
    });
    CODE_MAP.put(code, System.currentTimeMillis());
  }

  @Override
  public String passwordReset(UserRequest userRequest) {
    String email = userRequest.getEmail();
    User dbUser = getOne(new UpdateWrapper<User>().eq("email", email));
    if (dbUser == null) {
      throw new ServiceException("未找到当前用户");
    }
    validateEmail(userRequest.getEmailCode());
    String newPass = "admin";
    dbUser.setPassword(newPass);
    try {
      updateById(dbUser);   // 设置到数据库
    } catch (Exception e) {
      throw new RuntimeException("重置失败", e);
    }
    return newPass;
  }

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
    // 设置用户唯一uid
    user.setUid(IdUtil.fastSimpleUUID());
    boolean save = save(user);
    if (!save) {
      throw new RuntimeException("注册失败");
    }
    return user;
  }

  private void validateEmail(String emailCode) {
    Long timestamp = CODE_MAP.get(emailCode);
    if (timestamp == null) {
      throw new ServiceException("请先验证邮箱");
    }
    if (timestamp + TIME_IN_MS5 < System.currentTimeMillis()) {
      throw new ServiceException("验证码过期");
    }
    CODE_MAP.remove(emailCode); // 清除缓存
  }
}
