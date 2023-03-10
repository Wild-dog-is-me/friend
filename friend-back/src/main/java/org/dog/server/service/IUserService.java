package org.dog.server.service;

import org.dog.server.controller.domain.LoginDTO;
import org.dog.server.controller.domain.UserRequest;
import org.dog.server.entity.Permission;
import org.dog.server.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Odin
 * @since 2023-01-04
 */
public interface IUserService extends IService<User> {

  LoginDTO login(UserRequest user);

  void register(UserRequest user);

  void sendEmail(String email, String type);

  String passwordReset(UserRequest userRequest);

  void logout(String uid);

  User saveUser(User user);

  List<Permission> getPermissions(String roleFlag);

}
