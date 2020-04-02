package com.qianqian.edu.teacher.management.web.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianqian.edu.common.entity.EduTeacher;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.teacher.management.service.EduCourseService;
import com.qianqian.edu.teacher.management.web.service.WebTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/16 17:31
 */
@Api(description="讲师模块")
@RestController
@RequestMapping("web/edu/teacher")
public class WebTeacherController {

    @Autowired
    private WebTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "讲师列表")
    @GetMapping("{page}/{limit}")
    public R getTeacherList(@PathVariable Integer page,
                            @PathVariable Integer limit){
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherList(pageTeacher);
        return  map.size()>0 ? R.ok().data(map) : R.error().message("讲师列表查询失败！");
    }


    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);
        //根据讲师id查询这个讲师的课程列表
        return courseService.getCourseByTeacherId(id).data("teacher", teacher);

    }
}
