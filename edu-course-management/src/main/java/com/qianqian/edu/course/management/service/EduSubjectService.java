package com.qianqian.edu.course.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianqian.edu.common.entity.EduSubject;
import com.qianqian.edu.course.management.vo.FirstSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 通过Excel批量导入一二三级课程类别
     * @param file Excel文档
     * @return 错误信息集合
     */
    List<String> batchImport(MultipartFile file);

    /**
     * 一二三类课程类别列表
     * @return
     */
    List<FirstSubjectVo> nestedList();

    /**
     * 根据课程类别id删除类别
     * @param id
     * @return
     */
    boolean deleteSubjectById(String id);

    boolean saveLevelOne(EduSubject subject);

    boolean saveLevelTwo(EduSubject subject);
}
