package com.qianqian.edu.video.controller;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author minsiqian
 * @date 2020/3/15 16:09
 */
@Api(description="阿里云视频点播微服务")
@RestController
@RequestMapping("/admin/edu/aliyun-video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }

    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId){

        return videoService.removeVideo(videoId) ? R.ok().message("视频删除成功！") : R.error().message("视频删除失败！");
    }

    @ApiOperation("根据课程ID删除阿里云多个视频")
    @DeleteMapping("removeMoreVideo")
    public R removeMoreVideo(@ApiParam(name = "videoIds", value = "需要被删除的云端视频ids", required = true)
                         @RequestParam("videoIds") List videoIds){

        return videoService.removeMoreVideo(videoIds) ? R.ok().message("视频删除成功！") : R.error().message("视频删除失败！");
    }

    @ApiOperation("根据视频ID获取视频播放凭证")
    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        return videoService.getVideoPlayAuth(videoId);
    }
}
