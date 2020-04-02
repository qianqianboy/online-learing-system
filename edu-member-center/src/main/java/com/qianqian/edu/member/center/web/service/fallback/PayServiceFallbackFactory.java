package com.qianqian.edu.member.center.web.service.fallback;

import com.qianqian.edu.member.center.web.service.PayService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/17 19:32
 */
@Slf4j
@Component
public class PayServiceFallbackFactory implements FallbackFactory<PayService> {

    @Override
    public PayService create(Throwable throwable) {
       return null;
    }
}
