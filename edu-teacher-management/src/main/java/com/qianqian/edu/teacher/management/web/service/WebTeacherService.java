package com.qianqian.edu.teacher.management.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianqian.edu.common.entity.EduTeacher;

import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/16 17:45
 */

public interface WebTeacherService extends IService<EduTeacher> {

    /**
     * 门户网站获取讲师列表
     * @param pageTeacher
     * @return
     */
    Map<String, Object> getTeacherList(Page<EduTeacher> pageTeacher);


}
