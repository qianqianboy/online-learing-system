package com.qianqian.edu.course.management.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程小节VO
 * @author minsiqian
 * @date 2020/3/11 21:31
 */
@ApiModel(value = "课程小节信息")
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;

    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;

    @ApiModelProperty(value = "云服务器上存储的视频id")
    private String videoSourceId;
}
