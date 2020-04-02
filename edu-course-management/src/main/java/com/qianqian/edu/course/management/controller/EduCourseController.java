package com.qianqian.edu.course.management.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianqian.edu.common.dto.form.CourseInfoForm;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.course.management.query.CourseQuery;
import com.qianqian.edu.course.management.service.EduCourseService;
import com.qianqian.edu.course.management.service.EduVideoService;
import com.qianqian.edu.course.management.vo.CoursePublishVo;
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

    @Autowired
    private EduVideoService videoService;

//    @ApiOperation(value = "查询课程列表(不带条件)")
//    @GetMapping("{page}/{limit}")
//    public R pageList(
//            @ApiParam(name = "page", value = "当前页码", required = true)
//            @PathVariable Long page,
//            @ApiParam(name = "limit", value = "每页记录数", defaultValue = "10",required = true)
//            @PathVariable(required = false) Long limit) {
//        if(page <= 0 || limit <= 0){
//            //throw new QianQianException(5001, "参数错误");
//            throw new QianQianException(ResultCodeEnum.QUERY_TOTAL_ERROR);
//        }
//        Page<EduCourse> pageParam = new Page<>(page, limit);
//
//        courseService.page(pageParam, null);
//        List<EduCourse> records = pageParam.getRecords();
//        long total = pageParam.getTotal();
//        return R.ok().data("total", total).data("items", records);
//    }

    @ApiOperation(value = "分页查询课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
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

    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info")
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){
        return courseService.updateCourseInfoById(courseInfoForm) ? R.ok():R.error().message("修改失败！");
    }


    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }



    @ApiOperation(value = "分页查询待审核课程列表")
    @GetMapping("check-pending-list/{page}/{limit}")
    public R getCheckPendingList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象")
                    CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.getCheckPendingList(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "发布课程")
    @PutMapping("publish-course/{id}")
    public R publish(@PathVariable String id){
        return courseService.publishCourse(id) ? R.ok() : R.error().message("发布失败！");
    }

    @ApiOperation(value = "驳回课程")
    @PutMapping("fail-course/{id}")
    public R fail(@PathVariable String id){
        return courseService.failedCourse(id) ? R.ok() : R.error().message("驳回失败！");
    }


    @ApiOperation(value = "分页查询讲师自己的课程列表")
    @GetMapping("my-course-list/{teacherId}/{page}/{limit}")
    public R getCourseByTeacherId(
            @ApiParam(name = "page", value = "讲师ID", required = true)
            @PathVariable String teacherId,
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象")
                    CourseQuery courseQuery){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.getCourseByTeacherId(teacherId,pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
            videoService.removeByCourseId(id);
        boolean result = courseService.removeCourseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

