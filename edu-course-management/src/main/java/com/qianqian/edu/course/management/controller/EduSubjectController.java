package com.qianqian.edu.course.management.controller;


import com.qianqian.edu.common.entity.EduSubject;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.course.management.service.EduSubjectService;
import com.qianqian.edu.course.management.vo.FirstSubjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
@Api(description="课程分类管理")
@RestController
@RequestMapping("admin/edu/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("import")
    public R addUser(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {

        List<String> msg = subjectService.batchImport(file);
        if(msg.size() == 0){
            return R.ok().message("批量导入成功");
        }else{
            return R.error().message("部分数据导入失败").data("messageList", msg);
        }
    }


    @ApiOperation(value = "获取一二三级分类列表")
    @GetMapping("")
    public R nestedList(){
        List<FirstSubjectVo> subjectVos= subjectService.nestedList();
        return R.ok().data("items", subjectVos);
    }

    @ApiOperation(value = "根据ID删除课程类别")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(name = "id",value = "课程类别ID",required = true,paramType = "path")
    public R removeById(@PathVariable(value = "id") String id){
        boolean ret=subjectService.deleteSubjectById(id);
        return ret ? R.ok().message("删除课程类别操作成功！"):R.error().message("删除课程类别操作失败！");
    }

    @ApiOperation(value = "新增一级分类")
    @PostMapping("save-level-one")
    public R saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){
        boolean result = subjectService.saveLevelOne(subject);
        if(result){
            return R.ok();
        }else{
            return R.error().message("新增课程一级分类失败");
        }
    }

    @ApiOperation(value = "新增二级分类")
    @PostMapping("save-level-two")
    public R saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveLevelTwo(subject);
        if(result){
            return R.ok();
        }else{
            return R.error().message("新增课程二级分类失败");
        }
    }

    @ApiOperation(value = "根据ID查询课程类别")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程类别ID", required = true,type = "path")
            @PathVariable String id){
        EduSubject subject = subjectService.getById(id);
        return subject!=null ? R.ok().data("item", subject).message("查询成功！"):R.error().message("查询失败！");
    }
}

