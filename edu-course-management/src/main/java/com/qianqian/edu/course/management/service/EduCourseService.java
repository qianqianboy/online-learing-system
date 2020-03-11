package com.qianqian.edu.course.management.service;

import com.qianqian.edu.common.dto.form.CourseInfoForm;
import com.qianqian.edu.common.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm 课程基本信息表单对象
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 回显课程基本信息
     * @param courseId 课程ID
     * @return 课程基本信息表单对象
     */
    CourseInfoForm echoByCourseId(String courseId);

    /**
     *修改课程基本信息
     * @param courseInfoForm 课程基本信息表单对象
     */
    boolean updateCourseInfoById(CourseInfoForm courseInfoForm);
}
