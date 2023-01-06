package org.dog.server.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.dog.server.common.Constants;
import org.dog.server.entity.User;
import org.dog.server.exception.ServiceException;
import org.dog.server.mapper.UserMapper;
import org.dog.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

  @Override
  public User login(User user) {
    User dbUser = null;
    try {
      dbUser = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
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
  public User register(User user) {
    try {
      return saveUser(user);
    } catch (Exception e) {
      throw new RuntimeException("数据库异常");
    }
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
}
