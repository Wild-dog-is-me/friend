package org.dog.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dog.server.common.Result;
import org.dog.server.entity.User;
import org.dog.server.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Odin
 * @Date: 2023/1/4 17:58
 * @Description:
 */

@Api(tags = "无权限接口")
@RestController
public class WebController {

  @Resource
  private IUserService userService;

  @ApiOperation(value = "用户登陆")
  @PostMapping("/login")
  public Result login(@RequestBody User user) {
    return Result.success(userService.login(user));
  }
}
