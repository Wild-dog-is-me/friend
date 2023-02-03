package org.dog.server.common;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Odin
 * @Date: 2023/1/4 17:14
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

  private static final long serialVersionUID = 42L;

  private static final String CODE_SUCCESS = "200";
  private static final String CODE_SYS_ERROR = "500";

  private String code;
  private String msg;
  private Object data;

  public static Result success() {
    return new Result(CODE_SUCCESS, "操作成功", null);
  }

  public static Result success(Object data) {
    return new Result(CODE_SUCCESS, "操作成功", data);
  }

  public static Result error(String code, String msg) {
    return new Result(code, msg, null);
  }

  public static Result error(String msg) {
    return new Result(CODE_SYS_ERROR, msg, null);
  }

  public static Result error() {
    return new Result(CODE_SYS_ERROR, "系统错误", null);
  }

}
