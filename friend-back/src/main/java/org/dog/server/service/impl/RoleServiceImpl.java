package org.dog.server.service.impl;

import org.dog.server.entity.Role;
import org.dog.server.mapper.RoleMapper;
import org.dog.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Odin
 * @since 2023-01-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
