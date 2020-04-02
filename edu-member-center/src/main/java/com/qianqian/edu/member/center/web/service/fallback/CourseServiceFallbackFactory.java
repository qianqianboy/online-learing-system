package com.qianqian.edu.member.center.web.service.fallback;

import com.qianqian.edu.common.vo.CourseWebVo;
import com.qianqian.edu.member.center.web.service.CourseService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/17 19:32
 */
@Slf4j
@Component
public class CourseServiceFallbackFactory implements FallbackFactory<CourseService> {

    @Override
    public CourseService create(Throwable throwable) {
       return new CourseService() {
           @Override
           public CourseWebVo selectById(String id) {
               CourseWebVo vo = new CourseWebVo();
               vo.setDescription("失败！");
               return vo;
           }
       };
    }
}
