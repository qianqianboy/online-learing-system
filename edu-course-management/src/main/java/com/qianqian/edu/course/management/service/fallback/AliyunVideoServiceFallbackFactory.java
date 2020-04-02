package com.qianqian.edu.course.management.service.fallback;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.course.management.service.AliyunVideoService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author minsiqian
 * @date 2020/3/15 19:11
 */
@Component
@Slf4j
public class AliyunVideoServiceFallbackFactory implements FallbackFactory<AliyunVideoService> {

    @Override
    public AliyunVideoService create(Throwable throwable) {
        return new AliyunVideoService() {
            @Override
            public R removeVideo(String videoId) {
                return R.error().message("远程调用课程服务：/admin/edu/aliyun-video/{videoId}接口时，发生错误，进入了容错逻辑，这是容错提示！！");
            }

            @Override
            public R removeMoreVideo(List videoIds) {
                return R.error().message("远程调用课程服务：/admin/edu/aliyun-video/removeMoreVideo接口时，发生错误，进入了容错逻辑，这是容错提示！！");
            }
        };
    }
}
