package com.qianqian.edu.aliyun.oss.controller;

import com.qianqian.edu.aliyun.oss.service.FileService;
import com.qianqian.edu.common.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/admin/oss/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(name = "host", value = "以此修改图片服务器存放路径")
            @RequestParam("host")String host) {

        String uploadUrl = fileService.upload(file,host);
        //返回r对象
        return R.ok().message("文件上传成功").data("url", uploadUrl);

    }
}
