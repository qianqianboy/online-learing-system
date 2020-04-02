package com.qianqian.edu.teacher.management.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqian.edu.common.entity.EduTeacher;
import com.qianqian.edu.teacher.management.mapper.EduTeacherMapper;
import com.qianqian.edu.teacher.management.web.service.WebTeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author minsiqian
 * @date 2020/3/16 18:38
 */
@Service
public class WebTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements WebTeacherService {

    @Override
    public Map<String, Object> getTeacherList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
         baseMapper.selectPage(pageTeacher, queryWrapper);
        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();
        long size = pageTeacher.getSize();
        long pages = pageTeacher.getPages();
        long current = pageTeacher.getCurrent();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();
        //选用ConcurrentHashMap支持多线程数据安全
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("records",records);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("current",current);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }
}
