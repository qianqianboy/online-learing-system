package com.qianqian.edu.course.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqian.edu.common.constants.ResultCodeEnum;
import com.qianqian.edu.common.entity.EduSubject;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.common.util.ExcelImportHSSFUtil;
import com.qianqian.edu.common.util.ExceptionUtil;
import com.qianqian.edu.course.management.mapper.EduSubjectMapper;
import com.qianqian.edu.course.management.service.EduSubjectService;
import com.qianqian.edu.course.management.vo.FirstSubjectVo;
import com.qianqian.edu.course.management.vo.SecondSubjectVo;
import com.qianqian.edu.course.management.vo.SubjectVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author minsiqian
 * @since 2020-02-27
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Transactional
    @Override
    public List<String> batchImport(MultipartFile file) {

        List<String> msg = new ArrayList<>();
        try {

            ExcelImportHSSFUtil excelHSSFUtil = new ExcelImportHSSFUtil(file.getInputStream());
            Sheet sheet = excelHSSFUtil.getSheet();

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                msg.add("亲，Excel中没有任何数据噢~");
                return msg;
            }
            for (int rowNum = 1; rowNum < rowCount; rowNum++) {

                Row rowData = sheet.getRow(rowNum);
                if (rowData != null) {// 行不为空

                    //一级分类名称
                    String levelOneValue = "";
                    //获取每一行的第一列单元格
                    Cell levelOneCell = rowData.getCell(0);
                    if (levelOneCell == null){
                        msg.add("第" + ++rowNum + "行一级分类内容为空");
                        continue;
                    }
                    else {
                        //获取每一行第一列单元格的内容==一级分类名称
                        levelOneValue = excelHSSFUtil.getCellValue(levelOneCell,1);
                        if (StringUtils.isEmpty(levelOneValue)) {
                            msg.add("第" + ++rowNum + "行一级分类内容为空");
                            continue;
                        }
                    }

                    EduSubject subject = this.getByTitle(levelOneValue);
                    EduSubject subjectLevelOne = null;
                    String parentId = null;
                    if(subject == null){//创建一级分类
                        subjectLevelOne = new EduSubject();
                        subjectLevelOne.setTitle(levelOneValue);
                        subjectLevelOne.setSort(0);
                        baseMapper.insert(subjectLevelOne);//添加
                        parentId = subjectLevelOne.getId();
                    }else{
                        parentId = subject.getId();
                    }

                    //二级分类名称
                    String levelTwoValue = "";
                    Cell levelTwoCell = rowData.getCell(1);
                    if (levelTwoCell == null){
                        msg.add("第" + ++rowNum + "行二级分类内容为空");
                        continue;
                    }
                    else {
                        levelTwoValue = excelHSSFUtil.getCellValue(levelTwoCell,1);
                        if (StringUtils.isEmpty(levelTwoValue)) {
                            msg.add("第" + ++rowNum + "行二级分类内容为空");
                            continue;
                        }
                    }

                    EduSubject subjectSub = this.getSubByTitle(levelTwoValue, parentId);
                    EduSubject subjectLevelTwo = null;
                    if(subjectSub == null){//创建二级分类
                        subjectLevelTwo = new EduSubject();
                        subjectLevelTwo.setTitle(levelTwoValue);
                        subjectLevelTwo.setParentId(parentId);
                        subjectLevelTwo.setSort(0);
                        baseMapper.insert(subjectLevelTwo);//添加
                        parentId=subjectLevelTwo.getId();
                    }else {
                       parentId=subjectSub.getId();
                    }




                    //三级分类名称
                    String levelThreeValue = "";
                    Cell levelThreeCell = rowData.getCell(2);
                    if (levelThreeCell == null){
                        msg.add("第" + ++rowNum + "行三级分类内容为空");
                        continue;
                    }
                    else {
                        levelThreeValue = excelHSSFUtil.getCellValue(levelThreeCell,1);
                        if (StringUtils.isEmpty(levelThreeValue)) {
                        msg.add("第" + ++rowNum + "行三级分类内容为空");
                        continue;
                        }
                    }
//                    levelThreeValue = excelHSSFUtil.getCellValue(levelThreeCell,1);
                    EduSubject subjectSubSub = this.getSubByTitle(levelThreeValue, parentId);
                    EduSubject subjectLevelThree = null;
                    if(subjectSubSub == null){//创建三级分类
                        subjectLevelThree = new EduSubject();
                        subjectLevelThree.setTitle(levelThreeValue);
                        subjectLevelThree.setParentId(parentId);
                        subjectLevelThree.setSort(0);
                        baseMapper.insert(subjectLevelThree);//添加
                    }
                }
            }

        }catch (Exception e){
            log.error(ExceptionUtil.getMessage(e));
            throw new QianQianException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }

        return msg;
    }

    @Override
    public List<FirstSubjectVo> nestedList() {

        //最终要的到的数据列表
        ArrayList<FirstSubjectVo> allSubjectVoArrayList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<EduSubject> subjects = baseMapper.selectList(queryWrapper);
//        System.out.println(subjects);
        //获取二级分类数据记录
        QueryWrapper<EduSubject> queryWrapper2=new QueryWrapper<>();
        ArrayList<String> ids = new ArrayList<>();
        subjects.forEach(subject-> ids.add(subject.getId()));
        queryWrapper2.in("parent_id",ids);
        queryWrapper2.orderByAsc("sort", "id");
        List<EduSubject> subSubjects = baseMapper.selectList(queryWrapper2);
//        System.out.println(subSubjects);
        //获取三级分类数据记录
        ArrayList<String> ids2 = new ArrayList<>();
        QueryWrapper<EduSubject> queryWrapper3 = new QueryWrapper<>();
        subSubjects.forEach(subject-> ids2.add(subject.getId()));
        queryWrapper3.in("parent_id",ids2);
        queryWrapper3.orderByAsc("sort", "id");
        List<EduSubject> subsubSubjects = baseMapper.selectList(queryWrapper3);
//        System.out.println(subsubSubjects);

        //填充一级分类vo数据
        int count = subjects.size();
        for (int i = 0; i < count; i++) {
            EduSubject subject = subjects.get(i);

            //创建一级类别vo对象
            FirstSubjectVo firstSubjectVo = new FirstSubjectVo();
            BeanUtils.copyProperties(subject, firstSubjectVo);
            allSubjectVoArrayList.add(firstSubjectVo);

            //填充二级分类vo数据
            ArrayList<SecondSubjectVo> secondSubjectVoArrayList = new ArrayList<>();
            int count2 = subSubjects.size();
            for (int j = 0; j < count2; j++) {

                EduSubject subSubject = subSubjects.get(j);
                if(subject.getId().equals(subSubject.getParentId())){

                    //创建二级类别vo对象
                    SecondSubjectVo secondSubjectVo = new SecondSubjectVo();
                    BeanUtils.copyProperties(subSubject, secondSubjectVo);
                    secondSubjectVoArrayList.add(secondSubjectVo);
                    //填充三级分类vo数据
                    ArrayList<SubjectVo> threeSubjectVoArrayList = new ArrayList<>();
                    int count3 = subsubSubjects.size();
                    for (int k = 0; k<count3; k++) {
                        EduSubject subsubSubject = subsubSubjects.get(k);
                        if(subSubject.getId().equals(subsubSubject.getParentId())){
                            //创建三级标题
                            SubjectVo subjectVo = new SubjectVo();
                            BeanUtils.copyProperties(subsubSubject, subjectVo);
                            threeSubjectVoArrayList.add(subjectVo);
                        }

                    }
                    secondSubjectVo.setChildren(threeSubjectVoArrayList);
                }
            }
            firstSubjectVo.setChildren(secondSubjectVoArrayList);
        }
//        System.err.println(allSubjectVoArrayList);
        return allSubjectVoArrayList;
    }

    @Override
    public boolean deleteSubjectById(String id) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        //若当前标题下没有子标题，则可以删除并返回删除成功，否则删除失败
        if(count>0){
            return false;
        }else {
            int ret = baseMapper.deleteById(id);
            return ret > 0;
        }
    }


    /**
     * 根据分类名称查询这个一级分类中否存在
     * @param title
     * @return
     */
    private EduSubject getByTitle(String title) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据分类名称和父id查询这个二级分类中否存在
     * @param title
     * @return
     */
    private EduSubject getSubByTitle(String title, String parentId) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean saveLevelOne(EduSubject subject) {
        EduSubject subjectLevelOne = this.getByTitle(subject.getTitle());
        if(subjectLevelOne == null){
            return super.save(subject);
        }
        return false;
    }

    @Override
    public boolean saveLevelTwo(EduSubject subject) {
        EduSubject subjectLevelTwo = this.getSubByTitle(subject.getTitle(), subject.getParentId());
        if(subjectLevelTwo == null){
            return this.save(subject);
        }else{
            throw new QianQianException(30001, "类别已存在");
        }
    }
}
