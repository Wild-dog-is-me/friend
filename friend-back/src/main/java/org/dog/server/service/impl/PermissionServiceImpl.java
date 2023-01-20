package org.dog.server.service.impl;

import org.dog.server.entity.Permission;
import org.dog.server.mapper.PermissionMapper;
import org.dog.server.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Odin
 * @since 2023-01-20
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

  @Override
  public List<Permission> tree() {
    List<Permission> allData = list();

    return childrenTree(null, allData); // 从第一级开始往下递归获取树
  }

  // 递归生成树
  private List<Permission> childrenTree(Integer pid, List<Permission> allData) {
    List<Permission> list = new ArrayList<>();
    for (Permission permission : allData) {
      if (Objects.equals(permission.getPid(), pid)) {  // null, 一级
        list.add(permission);
        List<Permission> childrenTree = childrenTree(permission.getId(), allData);  // 递归调用
        permission.setChildren(childrenTree);
      }
    }
    return list;
  }
}
