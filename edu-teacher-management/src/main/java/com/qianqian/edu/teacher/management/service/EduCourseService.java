package com.qianqian.edu.teacher.management.service;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.teacher.management.service.fallback.CourseServiceFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author minsiqian
 * @date 2020/3/2 20:52
 */
@FeignClient(value = "edu-course-management",fallbackFactory = CourseServiceFallbackFactory.class)
public interface EduCourseService {
    /**
     * 根据讲师ID查询名下课程列表
     * @param teacherId 讲师ID
     * @return 统一结果对象R
     */
    @GetMapping("/web/edu/course/courseList/{id}")
    R getCourseByTeacherId(
            @ApiParam(name = "page", value = "讲师ID", required = true)
            @PathVariable("id") String teacherId);

    @GetMapping("/admin/edu/course/{page}/{limit}")
    R selectCourseList(@PathVariable("page") Long page, @PathVariable(required = false,value = "limit") Long limit);
}
