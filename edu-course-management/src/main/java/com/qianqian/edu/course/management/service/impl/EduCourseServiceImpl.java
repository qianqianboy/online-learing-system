package com.qianqian.edu.course.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqian.edu.common.dto.form.CourseInfoForm;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.common.entity.EduCourseDescription;
import com.qianqian.edu.course.management.mapper.EduCourseMapper;
import com.qianqian.edu.course.management.service.EduCourseDescriptionService;
import com.qianqian.edu.course.management.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Transactional
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        //保存课程基本信息
        EduCourse course = new EduCourse();
        course.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean resultCourseInfo = this.save(course);
        if(!resultCourseInfo){
            throw new QianQianException(50001, "课程信息保存失败");
        }

        //保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.save(courseDescription);
        if(!resultDescription){
            throw new QianQianException(50001, "课程详情信息保存失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoForm echoByCourseId(String courseId) {
        if (courseId!=null){
            CourseInfoForm courseInfoForm = new CourseInfoForm();
            EduCourse course = this.getById(courseId);
            EduCourseDescription description = courseDescriptionService.getById(courseId);
            BeanUtils.copyProperties(course,courseInfoForm);
            courseInfoForm.setDescription(description.getDescription());
            return courseInfoForm;
        }else {
            throw new QianQianException(60001,"课程ID不能为空！");
        }
    }


}
