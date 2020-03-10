package com.qianqian.edu.teacher.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianqian.edu.common.entity.EduTeacher;
import com.qianqian.edu.teacher.management.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 分页条件查询
     * @param pageParam 分页后的结果对象
     * @param teacherQuery 查询条件对象
     */
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    /**
     * 批量删除
     * @param ids 被删除的讲师ids
     * @return 删除是否成功
     */
    boolean deleteBatchByIds(String ids);
}
