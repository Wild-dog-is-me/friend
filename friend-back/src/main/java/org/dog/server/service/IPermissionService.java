package org.dog.server.service;

import org.dog.server.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Odin
 * @since 2023-01-20
 */
public interface IPermissionService extends IService<Permission> {

  List<Permission> tree();

}
