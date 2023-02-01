package org.dog.server.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.dog.server.entity.Role;
import org.dog.server.entity.RolePermission;
import org.dog.server.exception.ServiceException;
import org.dog.server.mapper.RoleMapper;
import org.dog.server.mapper.RolePermissionMapper;
import org.dog.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Odin
 * @since 2023-01-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

  @Resource
  private RolePermissionMapper rolePermissionMapper;

  @Transactional
  @Override
  public void savePermission(Integer roleId, List<Integer> permissionIds) {
    if (CollUtil.isEmpty(permissionIds) || roleId == null) {
      throw new ServiceException("数据不能为空");
    }
    rolePermissionMapper.delete(new UpdateWrapper<RolePermission>()
      .eq("role_id", roleId));
    permissionIds.forEach(v -> {
      RolePermission rolePermission = new RolePermission();
      rolePermission.setRoleId(roleId);
      rolePermission.setPermissionId(v);
      rolePermissionMapper.insert(rolePermission);
    });
  }
}
