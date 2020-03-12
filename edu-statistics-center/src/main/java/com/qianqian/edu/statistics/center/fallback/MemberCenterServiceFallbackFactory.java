package com.qianqian.edu.statistics.center.fallback;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.statistics.center.service.MemberCenterService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/3 16:37
 */
@Component
@Slf4j
public class MemberCenterServiceFallbackFactory implements FallbackFactory<MemberCenterService> {

    @Override
    public MemberCenterService create(Throwable throwable) {
        return ((day) -> {
            log.error("{}",throwable);
            R r=new R();
            r.success(false)
                    .code(10)
                    .message("远程调用课程服务：/admin/edu/member/count-register/{day}接口时，发生错误，进入了容错逻辑，这是容错提示！！");
            return r;
        });
    }
}
