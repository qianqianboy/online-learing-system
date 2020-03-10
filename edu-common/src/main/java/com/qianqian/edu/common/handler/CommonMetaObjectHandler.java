package com.qianqian.edu.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author minsiqian
 * @date 2020/2/23 22:42
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        log.info("新增时自动填充创建时间、更新时间、乐观锁版本号-1、逻辑删除-0！");
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        this.setFieldValByName("version",1,metaObject);
        this.setFieldValByName("deleted",false,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
