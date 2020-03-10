package com.qianqian.edu.teacher.management.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianqian.edu.common.constants.ResultCodeEnum;
import com.qianqian.edu.common.entity.EduTeacher;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.teacher.management.query.TeacherQuery;
import com.qianqian.edu.teacher.management.service.EduCourseService;
import com.qianqian.edu.teacher.management.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-22
 */
@Slf4j
@RestController

@RequestMapping("/admin/edu/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;


    @ApiOperation(value = "查询讲师列表(不带条件)")
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", defaultValue = "10",required = true)
            @PathVariable(required = false) Long limit) {
        if(page <= 0 || limit <= 0){
            //throw new QianQianException(5001, "参数错误");
            throw new QianQianException(ResultCodeEnum.QUERY_TOTAL_ERROR);
        }
        Page<EduTeacher> pageParam = new Page<>(page, limit);

        teacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("items", records);
    }

    @ApiOperation(value = "查询讲师列表(带条件)")
    @PostMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable @RequestParam(defaultValue = "10") Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象")
            @RequestBody TeacherQuery teacherQuery){

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        teacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("items", records);
    }

    @ApiOperation(value = "删除讲师")
    @DeleteMapping("{id}")
    public R delTeacher(
            @ApiParam(name = "id",value = "被删除讲师的id",required = true)
            @PathVariable(value = "id") String id){
        boolean ret = teacherService.removeById(id);
        return ret ? R.ok().message("删除讲师操作成功！"):R.error().message("删除讲师操作失败！");
    }

//    @ApiOperation(value = "批量删除讲师")
//    @DeleteMapping("{ids}")
//    public R delBatchTeacher(
//            @ApiParam(name = "ids",value = "被删除讲师们的ids",required = true)
//            @PathVariable(value = "ids") String ids){
//        boolean ret = teacherService.deleteBatchByIds(ids);
//        return ret ? R.ok().message("批量删除讲师操作成功！"):R.error().message("批量删除讲师操作失败！");
//    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        teacherService.save(teacher);
        return R.ok().message("新增讲师成功！");
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher).message("查询成功！");
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        teacher.setId(id);
        teacher.setVersion(teacherService.getById(id).getVersion());
        boolean ret = teacherService.updateById(teacher);
        return ret ? R.ok().message("修改讲师信息成功！") :R.error().code(4003).message("修改讲师信息失败！");
    }

    @ApiOperation(value = "测试Spring Cloud Alibaba微服务Fegin调用")
    @GetMapping("getCourse/{page}/{limit}")
    public R test(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", defaultValue = "10",required = true)
            @PathVariable(required = false,value = "limit") Long limit){
        R r = courseService.selectCourseList(page, limit);
//        log.info(JSON.toJSONString(r));
        return r;
    }
}

