package org.dog.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dog.server.common.Result;
import org.dog.server.controller.domain.UserRequest;
import org.dog.server.entity.User;
import org.dog.server.service.IUserService;
import org.springframework.web.bind.annotation.*;

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
  public Result login(@RequestBody UserRequest user) {
    return Result.success(userService.login(user));
  }

  @ApiOperation(value = "用户注册")
  @PostMapping("/register")
  public Result register(@RequestBody UserRequest user) {
    User res = userService.register(user);
    return Result.success(res);
  }

  @ApiOperation(value = "邮箱验证接口")
  @GetMapping("/email")
  public Result sendEmail(@RequestParam String email, @RequestParam String type) {
    userService.sendEmail(email, type);
    return Result.success();
  }

  @ApiOperation(value = "重置密码接口")
  @PostMapping("/password/reset")
  public Result passwordReset(@RequestBody UserRequest userRequest) {
    String newPass = userService.passwordReset(userRequest);
    return Result.success(newPass);
  }

}
