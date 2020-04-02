package com.qianqian.edu.member.center.web.service;

import com.qianqian.edu.common.vo.CourseWebVo;
import com.qianqian.edu.member.center.web.service.fallback.CourseServiceFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author minsiqian
 * @date 2020/3/17 19:30
 */
@FeignClient(value = "edu-course-management",fallbackFactory = CourseServiceFallbackFactory.class)
public interface CourseService {


    @GetMapping(value = "/web/edu/course/createOrder/{id}")
    CourseWebVo selectById( @ApiParam(name = "id", value = "课程ID", required = true)
                                   @PathVariable("id") String id);
}
