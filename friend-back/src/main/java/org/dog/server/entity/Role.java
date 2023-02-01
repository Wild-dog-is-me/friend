package org.dog.server.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.dog.server.common.LDTConfig;

/**
 * <p>
 *
 * </p>
 *
 * @author Odin
 * @since 2023-01-20
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  // 名称
  @ApiModelProperty("名称")
  @Alias("名称")
  private String name;

  // 唯一标识
  @ApiModelProperty("唯一标识")
  @Alias("唯一标识")
  private String flag;

  // 逻辑删除
  @ApiModelProperty("逻辑删除")
  @Alias("逻辑删除")
  @TableLogic(value = "0", delval = "id")
  private Integer deleted;

  // 创建时间
  @ApiModelProperty("创建时间")
  @Alias("创建时间")
  @TableField(fill = FieldFill.INSERT)
  @JsonDeserialize(using = LDTConfig.CmzLdtDeSerializer.class)
  @JsonSerialize(using = LDTConfig.CmzLdtSerializer.class)
  private LocalDateTime createTime;

  // 更新时间
  @ApiModelProperty("更新时间")
  @Alias("更新时间")
  private LocalDateTime updateTime;

  @TableField(exist = false)
  private List<Integer> permissionIds;

}
