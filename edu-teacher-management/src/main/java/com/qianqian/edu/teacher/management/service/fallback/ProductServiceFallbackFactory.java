package com.qianqian.edu.teacher.management.service.fallback;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.teacher.management.service.EduCourseService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/3 16:37
 */
@Component
@Slf4j
public class ProductServiceFallbackFactory implements FallbackFactory<EduCourseService> {
    @Override
    public EduCourseService create(Throwable throwable) {
        return ((page, limit) -> {
            log.error("{}",throwable);
            R r=new R();
            r.success(false)
                    .code(10)
                    .message("远程调用课程服务：/admin/edu/course/{page}/{limit}接口时，发生错误，进入了容错逻辑，这是容错提示！！");
            return r;
        });
    }
}
