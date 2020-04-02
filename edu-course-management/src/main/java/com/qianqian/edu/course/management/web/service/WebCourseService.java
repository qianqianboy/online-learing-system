package com.qianqian.edu.course.management.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.common.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/16 20:45
 */
public interface WebCourseService extends IService<EduCourse> {


    /**
     * 根据讲师ID查询他的课程
     * @param teacherId 讲师ID
     * @return 课程集合
     */
    List<EduCourse> getCourseByTeacherId(String teacherId);

    /**
     * 获取课程列表
     * @param pageParam
     * @return
     */
    Map<String, Object> pageCourseList(Page<EduCourse> pageParam);


    /**
     * 获取课程信息(查看课程详情需要)
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);

    /**
     * 获取课程信息(下单需要)
     * @param id
     * @return
     */
    CourseWebVo getCourseById(String id);
}
