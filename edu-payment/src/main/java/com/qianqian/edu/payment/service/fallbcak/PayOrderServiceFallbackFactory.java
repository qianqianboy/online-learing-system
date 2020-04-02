package com.qianqian.edu.payment.service.fallbcak;

import com.qianqian.edu.common.entity.EduOrder;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.payment.service.PayOrderService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/17 22:36
 */
@Slf4j
@Component
public class PayOrderServiceFallbackFactory implements FallbackFactory<PayOrderService> {

    @Override
    public PayOrderService create(Throwable throwable) {
        return new PayOrderService() {
            @Override
            public R updateOrder(EduOrder order) {
                return R.error().message("这里是支付出现异常的容错界面！");
            }
        };
    }
}
