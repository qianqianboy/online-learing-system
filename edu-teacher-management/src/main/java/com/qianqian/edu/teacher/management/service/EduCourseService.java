package com.qianqian.edu.teacher.management.service;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.teacher.management.service.fallback.ProductServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author minsiqian
 * @date 2020/3/2 20:52
 */
@FeignClient(value = "edu-course-management",fallbackFactory = ProductServiceFallbackFactory.class)
public interface EduCourseService {
    @GetMapping("/admin/edu/course/{page}/{limit}")
    R selectCourseList(@PathVariable("page") Long page, @PathVariable(required = false,value = "limit") Long limit);
}
