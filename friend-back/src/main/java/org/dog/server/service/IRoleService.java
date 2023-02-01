package org.dog.server.service;

import org.dog.server.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dog.server.entity.RolePermission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Odin
 * @since 2023-01-20
 */
public interface IRoleService extends IService<Role> {

  void savePermission(Integer roleId, List<Integer> permissionIds);
}
