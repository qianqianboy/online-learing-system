package com.qianqian.edu.course.management.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级标题
 * @author minsiqian
 * @date 2020/2/28 0:54
 */
@Data
public class SecondSubjectVo {

    private String id;
    private String title;
    private List<SubjectVo> children = new ArrayList<>();

}
