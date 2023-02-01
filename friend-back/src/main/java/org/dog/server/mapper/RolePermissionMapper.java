package org.dog.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dog.server.entity.Role;
import org.dog.server.entity.RolePermission;

/**
 * @Author: Odin
 * @Date: 2023/1/31 23:01
 * @Description:
 */

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}

