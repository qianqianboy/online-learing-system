package com.qianqian.edu.course.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianqian.edu.common.entity.EduCourse;
import com.qianqian.edu.course.management.vo.CoursePublishVo;
import com.qianqian.edu.common.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 关联查询课程预览信息
     * @param id 课程ID
     * @return 课程发布传输对象
     */
    CoursePublishVo selectCoursePublishVoById(String id);

    /**
     * 关联查询课程详情
     * @param courseId 课程ID
     * @return 课程详情传输对象
     */
    CourseWebVo selectInfoWebById(String courseId);
}
