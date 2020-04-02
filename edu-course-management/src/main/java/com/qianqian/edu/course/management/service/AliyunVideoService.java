package com.qianqian.edu.course.management.service;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.course.management.service.fallback.AliyunVideoServiceFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author minsiqian
 * @date 2020/3/15 19:10
 */
@FeignClient(value = "edu-video",fallbackFactory = AliyunVideoServiceFallbackFactory.class)
public interface AliyunVideoService {
    @DeleteMapping("/admin/edu/aliyun-video/{videoId}")
    R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                  @PathVariable String videoId);

    @DeleteMapping("/admin/edu/aliyun-video/removeMoreVideo")
    R removeMoreVideo(@ApiParam(name = "videoIds", value = "需要被删除的云端视频ids", required = true)
                      @RequestParam("videoIds") List<String> videoIds);
}
