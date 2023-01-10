package org.dog.server.controller.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dog.server.entity.User;

import java.io.Serializable;

/**
 * @Author: Odin
 * @Date: 2023/1/10 13:53
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private User user;
  private String token;
}
