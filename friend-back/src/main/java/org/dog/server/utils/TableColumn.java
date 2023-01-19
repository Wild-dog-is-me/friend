package org.dog.server.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Odin
 * @Date: 2023/1/19 14:52
 * @Description:
 */

@Data
@Builder
public class TableColumn {
  private String columnName;
  private String dataType;
  private String columnComment;

}
