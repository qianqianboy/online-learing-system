package com.qianqian.edu.course.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqian.edu.common.dto.form.CourseInfoForm;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.common.entity.EduCourseDescription;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.course.management.mapper.EduCourseMapper;
import com.qianqian.edu.course.management.query.CourseQuery;
import com.qianqian.edu.course.management.service.EduCourseDescriptionService;
import com.qianqian.edu.course.management.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Override
    public boolean updateCourseInfoById(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, course);
        boolean resultCourseInfo = this.updateById(course);
        if (!resultCourseInfo) {
            throw new QianQianException(20002, "课程信息保存失败");
        }
        //保存课程详情信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        boolean resultDescription = courseDescriptionService.updateById(courseDescription);
        if (!resultDescription) {
            throw new QianQianException(20002, "课程详情信息保存失败");
        }
        return true;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }
}
