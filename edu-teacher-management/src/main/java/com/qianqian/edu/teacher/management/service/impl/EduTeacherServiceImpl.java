package com.qianqian.edu.teacher.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqian.edu.common.entity.EduTeacher;
import com.qianqian.edu.teacher.management.mapper.EduTeacherMapper;
import com.qianqian.edu.teacher.management.query.TeacherQuery;
import com.qianqian.edu.teacher.management.service.EduTeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-22
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");//按sort进行升序排列，sort越小，优先级越高

        if (teacherQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        return baseMapper.deleteById(id) >= 1;
    }

    @Override
    public boolean deleteBatchByIds(String ids) {
        String[] strs = ids.split(",");
        List<String> list = Arrays.asList(strs);
        Integer i = baseMapper.deleteBatchIds(list);
        return null!=i && i==strs.length;
    }
}
