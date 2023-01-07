package org.dog.server.controller.domain;

import lombok.Data;

/**
 * @Author: Odin
 * @Date: 2023/1/7 09:58
 * @Description:
 */

@Data
public class UserRequest {

  private String username;

  private String password;

  private String email;

  private String name;

  private String emailCode;

}
