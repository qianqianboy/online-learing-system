package com.qianqian.edu.institution.management.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author minsiqian
 * @date 2020/3/23 15:52
 */
@ApiModel(value = "Article查询对象", description = "文章查询对象封装")
@Data
public class InstitutionQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
