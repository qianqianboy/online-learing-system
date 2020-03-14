package com.qianqian.edu.course.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.course.management.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo selectCoursePublishVoById(String id);
}
