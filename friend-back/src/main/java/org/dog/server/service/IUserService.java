package org.dog.server.service;

import org.dog.server.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Odin
 * @since 2023-01-04
 */
public interface IUserService extends IService<User> {

  User login(User user);

  User register(User user);
}
