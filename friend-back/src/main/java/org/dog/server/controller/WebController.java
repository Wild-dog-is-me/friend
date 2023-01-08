package org.dog.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WebController {

  @Resource
  private IUserService userService;

  @GetMapping(value = "/")
  @ApiOperation(value = "版本校验接口")
  public String version() {
    String ver = "friend-back-0.0.1-SNAPSHOT";  // 应用版本号
    Package aPackage = WebController.class.getPackage();
    String title = aPackage.getImplementationTitle();
    String version = aPackage.getImplementationVersion();
    if (title != null && version != null) {
      ver = String.join("-", title, version);
    }
    return ver;
  }

  @ApiOperation(value = "用户登陆")
  @PostMapping("/login")
  public Result login(@RequestBody UserRequest user) {
    long startTime = System.currentTimeMillis();
    User login = userService.login(user);
    log.info("登陆花费的时间{}ms", System.currentTimeMillis() - startTime);
    return Result.success(login);
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
