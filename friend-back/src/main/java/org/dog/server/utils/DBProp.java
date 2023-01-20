package org.dog.server.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Odin
 * @Date: 2023/1/19 13:45
 * @Description:
 */

@Data
@Builder
public class DBProp {
  private String url;
  private String username;
  private String password;
}

