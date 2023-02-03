package org.dog.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.dog.server.common.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dog.server.common.Result;
import org.springframework.web.multipart.MultipartFile;
import org.dog.server.service.IUserService;
import org.dog.server.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Odin
 * @since 2023-01-04
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Resource
  private IUserService userService;

  // 新增或者更新
  @PostMapping
  @SaCheckPermission("user.add")
  public Result save(@RequestBody User user) {
    userService.saveUser(user);
    return Result.success();
  }

  @PutMapping
  @SaCheckPermission("user.edit")
  public Result update(@RequestBody User user) {
    userService.updateById(user);
    return Result.success();
  }

  @DeleteMapping("/{id}")
  @SaCheckPermission("user.delete")
  public Result delete(@PathVariable Integer id) {
    userService.removeById(id);
    return Result.success();
  }

  @PostMapping("/del/batch")
  @SaCheckPermission("user.deleteBatch")
  public Result deleteBatch(@RequestBody List<Integer> ids) {
    userService.removeByIds(ids);
    return Result.success();
  }

  @GetMapping
  @SaCheckPermission("user.list")
  public Result findAll() {
    return Result.success(userService.list());
  }

  @GetMapping("/{id}")
  @SaCheckPermission("user.list")
  public Result findOne(@PathVariable Integer id) {
    return Result.success(userService.getById(id));
  }

  @GetMapping("/page")
  @SaCheckPermission("user.list")
  public Result findPage(@RequestParam(defaultValue = "") String name,
                         @RequestParam(defaultValue = "") String address,
                         @RequestParam Integer pageNum,
                         @RequestParam Integer pageSize) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
    queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
    // and
    queryWrapper.like(StrUtil.isNotBlank(address), "address", address);
    return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
  }

  /**
   * 导出接口
   */
  @GetMapping("/export")
  @SaCheckPermission("user.export")
  public void export(HttpServletResponse response) throws Exception {
    // 从数据库查询出所有的数据
    List<User> list = userService.list();
    // 在内存操作，写出到浏览器
    ExcelWriter writer = ExcelUtil.getWriter(true);

    // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
    writer.write(list, true);

    // 设置浏览器响应的格式
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
    String fileName = URLEncoder.encode("User信息表", "UTF-8");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

    ServletOutputStream out = response.getOutputStream();
    writer.flush(out, true);
    out.close();
    writer.close();
  }

  /**
   * excel 导入
   * @param file
   * @throws Exception
   */
  @PostMapping("/import")
  @SaCheckPermission("user.import")
  public Result imp(MultipartFile file) throws Exception {
    // 文件上传
    InputStream inputStream = file.getInputStream();
    ExcelReader reader = ExcelUtil.getReader(inputStream);
    // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
    List<User> list = reader.readAll(User.class);

//        userService.saveBatch(list);
    for (User user : list) {
      userService.saveUser(user);
    }
    return Result.success();
  }

}
