package org.dog.server.common.enums;

import lombok.Getter;

/**
 * @Author: Odin
 * @Date: 2023/1/9 08:10
 * @Description:
 */

@Getter
public enum EmailCodeEnum {
  REGISTER("REGISTER", "register:"),
  RESET_PASSWORD("RESETPASSWORD", "resetPassword:"),
  LOGIN("LOGIN", "login:"),
  CHANGE_PASSWORD("CHANGEPASSWORD", "changePassword:"),
  UNKNOWN("", "");

  private final String type;
  private final String value;

  EmailCodeEnum(String type, String value) {
    this.type = type;
    this.value = value;
  }

  public static String getValue(String type) {
    EmailCodeEnum[] values = values();
    for (EmailCodeEnum codeEnum : values) {
      if (type.equals(codeEnum.type)) {
        return codeEnum.value;
      }
    }
    return "";
  }

  public static EmailCodeEnum getEnum(String type) {
    EmailCodeEnum[] values = values();
    for (EmailCodeEnum codeEnum : values) {
      if (type.equals(codeEnum.type)) {
        return codeEnum;
      }
    }
    return UNKNOWN;
  }
}
