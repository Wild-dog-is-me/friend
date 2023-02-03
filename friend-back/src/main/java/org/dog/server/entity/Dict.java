package org.dog.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import cn.hutool.core.annotation.Alias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;
import org.dog.server.common.LDTConfig;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
*
* </p>
*
* @author Odin
* @since 2023-02-03
*/
@Getter
@Setter
@TableName("sys_dict")
@ToString
@ApiModel(value = "Dict对象", description = "")
public class Dict implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 编码
    @ApiModelProperty("编码")
    @Alias("编码")
    private String code;

    // 文本
    @ApiModelProperty("文本")
    @Alias("文本")
    private String value;

    // 类型
    @ApiModelProperty("类型")
    @Alias("类型")
    private String type;

    // 删除
    @ApiModelProperty("删除")
    @Alias("删除")
    @TableLogic(value = "0", delval = "id")
    private Integer deleted;
}
