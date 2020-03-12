package com.qianqian.edu.course.management.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author minsiqian
 * @date 2020/3/12 23:07
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectGrandParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "三级类别id")
    private String subjectId;

}
