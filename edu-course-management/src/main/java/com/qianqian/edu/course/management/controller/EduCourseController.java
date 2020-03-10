package com.qianqian.edu.course.management.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianqian.edu.common.constants.ResultCodeEnum;
import com.qianqian.edu.common.dto.form.CourseInfoForm;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.course.management.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
@Api(description="课程管理")
@RestController
@RequestMapping("admin/edu/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "查询课程列表(不带条件)")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", defaultValue = "10",required = true)
            @PathVariable(required = false) Long limit) {
        if(page <= 0 || limit <= 0){
            //throw new QianQianException(5001, "参数错误");
            throw new QianQianException(ResultCodeEnum.QUERY_TOTAL_ERROR);
        }
        Page<EduCourse> pageParam = new Page<>(page, limit);

        courseService.page(pageParam, null);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("items", records);
    }


    @ApiOperation(value = "新增课程基本信息")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){

        String courseId = courseService.saveCourseInfo(courseInfoForm);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId", courseId);
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程ID", required = true,type = "path")
            @PathVariable String id){
        EduCourse course = courseService.getById(id);
        return course!=null ? R.ok().data("item", course).message("查询成功！"):R.error().message("查询失败！");
    }

    @ApiOperation(value = "根据课程ID回显课程信息")
    @GetMapping("echo/{courseId}")
    public R echoByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true,type = "path")
            @PathVariable String courseId){
        CourseInfoForm courseInfoForm= courseService.echoByCourseId(courseId);
        return courseInfoForm!=null ? R.ok().data("item", courseInfoForm).message("回显成功！"):R.error().message("回显失败！");
    }

}

