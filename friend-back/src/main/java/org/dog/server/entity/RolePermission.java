package org.dog.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Odin
 * @Date: 2023/1/31 22:57
 * @Description:
 */

@TableName("sys_role_permission")
@Data
public class RolePermission {

  @TableId(type = IdType.AUTO)
  private Integer id;

  private Integer roleId;

  private Integer permissionId;

}
