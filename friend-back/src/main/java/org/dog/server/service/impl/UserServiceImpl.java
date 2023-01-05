package org.dog.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dog.server.entity.User;
import org.dog.server.exception.ServiceException;
import org.dog.server.mapper.UserMapper;
import org.dog.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
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
}
