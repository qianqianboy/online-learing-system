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
import com.qianqian.edu.course.management.service.EduChapterService;
import com.qianqian.edu.course.management.service.EduCourseDescriptionService;
import com.qianqian.edu.course.management.service.EduCourseService;
import com.qianqian.edu.course.management.service.EduVideoService;
import com.qianqian.edu.course.management.vo.CoursePublishVo;
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

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

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
        queryWrapper.notIn("status",EduCourse.COURSE_DRAFT);
        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectGrandParentId = courseQuery.getSubjectGrandParentId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectGrandParentId)) {
            if (!StringUtils.isEmpty(subjectParentId)) {
                if (!StringUtils.isEmpty(subjectId)) {
                    queryWrapper.eq("subject_id", subjectId);
                }
                queryWrapper.eq("subject_parent_id", subjectParentId);
            }
            queryWrapper.eq("subject_grand_parent_id", subjectGrandParentId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void getCheckPendingList(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("status",EduCourse.COURSE_Pending);

        if (courseQuery == null){

            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectGrandParentId = courseQuery.getSubjectGrandParentId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectGrandParentId)) {
            if (!StringUtils.isEmpty(subjectParentId)) {
                if (!StringUtils.isEmpty(subjectId)) {
                    queryWrapper.eq("subject_id", subjectId);
                }
                queryWrapper.eq("subject_parent_id", subjectParentId);
            }
            queryWrapper.eq("subject_grand_parent_id", subjectGrandParentId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean publishCourse(String id) {
        return baseMapper.updateById(baseMapper.selectById(id).setStatus(EduCourse.COURSE_NORMAL))==1;
    }

    @Override
    public boolean failedCourse(String id) {
        return baseMapper.updateById(baseMapper.selectById(id).setStatus(EduCourse.COURSE_Failed))==1;
    }

    @Override
    public void getCourseByTeacherId(String teacherId,Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("teacher_id",teacherId);

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String subjectGrandParentId = courseQuery.getSubjectGrandParentId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(subjectGrandParentId)) {
            if (!StringUtils.isEmpty(subjectParentId)) {
                if (!StringUtils.isEmpty(subjectId)) {
                    queryWrapper.eq("subject_id", subjectId);
                }
                queryWrapper.eq("subject_parent_id", subjectParentId);
            }
            queryWrapper.eq("subject_grand_parent_id", subjectGrandParentId);
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeCourseById(String courseId) {

        //根据id删除所有章节
        chapterService.removeByCourseId(courseId);

        Integer result = baseMapper.deleteById(courseId);
        return null != result && result > 0;
    }

}
