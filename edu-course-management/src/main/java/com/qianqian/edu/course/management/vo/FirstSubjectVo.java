package com.qianqian.edu.course.management.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级标题
 * @author minsiqian
 * @date 2020/2/28 0:54
 */
@Data
public class FirstSubjectVo {

    private String id;
    private String title;
    private List<SecondSubjectVo> children = new ArrayList<>();

}
