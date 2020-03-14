package com.qianqian.edu.course.management.vo;

import lombok.Data;

/**
 * 课程发布VO对象
 * @author minsiqian
 * @date 2020/3/14 19:11
 */
@Data
public class CoursePublishVo {

    /**
     * 课程ID
     */
    private String id;
    /**
     * 课程名称
     */
    private String title;

    /**
     * 课程封面
     */
    private String cover;
    /**
     * 课程封面
     */
    private String price;

    /**
     * 课时总数
     */
    private String lessonNums;

    /**
     * 课程描述
     */
    private String description;

    /**
     * 课程所属一级分类
     */
    private String levelOne;

    /**
     * 课程所属二级分类
     */
    private String levelTwo;

    /**
     * 课程所属三级分类
     */
    private String levelThree;

    /**
     * 课程所属讲师姓名
     */
    private String teacherName;

}
