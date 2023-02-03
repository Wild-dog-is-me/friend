package org.dog.server.service.impl;

/**
 * @Author: Odin
 * @Date: 2023/2/4 01:42
 * @Description:
 */

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dog.server.entity.Permission;
import org.dog.server.entity.User;
import org.dog.server.service.IUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

  @Resource
  private IUserService userService;

  /**
   * 返回一个账号所拥有的权限码集合
   */
  @Override
  public List<String> getPermissionList(Object loginId, String loginType) {
    User user = userService.getOne(new QueryWrapper<User>().eq("uid", loginId));
    String roleFlag = user.getRole();
    List<Permission> permissions = userService.getPermissions(roleFlag);
    return permissions.stream().map(Permission::getAuth).filter(StrUtil::isNotBlank)
      .collect(Collectors.toList());
  }

  /**
   * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
   */
  @Override
  public List<String> getRoleList(Object loginId, String loginType) {
    User user = userService.getOne(new QueryWrapper<User>().eq("uid", loginId));
    String roleFlag = user.getRole();
    return StrUtil.isBlank(roleFlag) ? new ArrayList<>() : CollUtil.newArrayList(roleFlag);
  }

}
