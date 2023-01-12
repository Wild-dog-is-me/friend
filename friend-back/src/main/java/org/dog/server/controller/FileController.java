package org.dog.server.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.dog.server.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件控制器
 *
 * @Author: Odin
 * @Date: 2023/1/12 12:43
 * @Description:
 */

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

  // path: //Users//odin//Documents//code-study//friend//friend-back//src//main//resources//file//
  @Value("${files.upload.path}")
  private String uploadPath;

  @Value("${server.port:10000}")
  private String port;

  @Value("${file.download.ip:localhost}")
  private String downloadIp;

  /**
   * 上传
   *
   * @param file 文件
   * @return {@link Result}
   */
  @PostMapping("/upload")
  public Result upload(@RequestParam MultipartFile file) {
    String originalFilename = file.getOriginalFilename(); // 文件完整名称
    String fileName = FileUtil.mainName(originalFilename); // 文件主名称
    String extName = FileUtil.extName(originalFilename); // 文件后缀名
    String uniFileFlag = IdUtil.fastSimpleUUID();
    String fileFullName = uniFileFlag + StrUtil.DOT + extName;
    // 封装完整的文件路径获取方法
    String fileUploadPath = uploadPath + fileFullName;

    try {
      File uploadFile = new File(fileUploadPath);
      File parentFile = uploadFile.getParentFile();
      if (!parentFile.exists()) {
        parentFile.mkdirs();
      }
      file.transferTo(uploadFile);
    } catch (Exception e) {
      log.info("文件上传失败", e);
      return Result.error("文件上传失败");
    }
    return Result.success("http://" + downloadIp + ":" + port + "/file/download/" + fileFullName);
  }

  /**
   * 文件下载
   * @param fileFullName
   * @param response
   * @throws IOException
   */
  @GetMapping("/download/{fileFullName}")
  public void downloadFile(@PathVariable String fileFullName, @RequestParam(required = false) String loginId,
                           @RequestParam(required = false) String token,
                           HttpServletResponse response) throws IOException {

    String extName = FileUtil.extName(fileFullName);
    String fileUploadPath = getFileUploadPath(fileFullName);
    byte[] bytes = FileUtil.readBytes(fileUploadPath);
    response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileFullName, "UTF-8"));  // 预览
    List<String> attachmentFileExtNames = CollUtil.newArrayList("docx", "doc", "xlsx", "xls", "mp4", "mp3");
    if (attachmentFileExtNames.contains(extName)) {
      response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileFullName, "UTF-8"));  // 附件下载
    }
    OutputStream os = response.getOutputStream();
    os.write(bytes);
    os.flush();
    os.close();
  }

  private String getFileUploadPath(String fileFullName) {
    return uploadPath + fileFullName;
  }
}
