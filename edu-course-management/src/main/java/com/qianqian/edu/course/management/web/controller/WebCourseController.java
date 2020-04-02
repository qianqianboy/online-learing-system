package com.qianqian.edu.course.management.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.course.management.service.EduChapterService;
import com.qianqian.edu.course.management.service.EduSubjectService;
import com.qianqian.edu.course.management.vo.ChapterVo;
import com.qianqian.edu.common.vo.CourseWebVo;
import com.qianqian.edu.course.management.vo.FirstSubjectVo;
import com.qianqian.edu.course.management.web.service.WebCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/16 20:43
 */
@RestController
@RequestMapping("web/edu/course")
public class WebCourseController {

    @Autowired
    private WebCourseService courseService;

    @ApiOperation(value = "查询指定讲师的课程列表")
    @GetMapping("courseList/{id}")
    public R getCourseByTeacherId(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        List<EduCourse> courses=courseService.getCourseByTeacherId(id);
        return courses.size()>0 ? R.ok().data("courses", courses) : R.error().message("他还没有发布任何课程喔~");
    }


    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        //查询课程分类信息
        List<FirstSubjectVo> subjectVoList = subjectService.nestedList();
        Map<String, Object> map = courseService.pageCourseList(pageParam);
        return  map.size()>0 ? R.ok().data(map).data("subjectVoList",subjectVoList) : R.error().message("课程列表获取失败！");
    }


    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation(value = "课程详情")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);
        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "createOrder/{id}")
    public CourseWebVo selectById( @ApiParam(name = "courseId", value = "课程ID", required = true)
                             @PathVariable String id){
        return courseService.getCourseById(id);
    }
}
