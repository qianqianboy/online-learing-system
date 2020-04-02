package com.qianqian.edu.member.center.web.service.fallback;

import com.qianqian.edu.common.entity.EduOrder;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.member.center.web.service.OrderService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/17 19:32
 */
@Slf4j
@Component
public class OrderServiceFallbackFactory implements FallbackFactory<OrderService> {

    @Override
    public OrderService create(Throwable throwable) {
        return new OrderService() {
            @Override
            public R createOrder(EduOrder order) {
                return R.error().message("订单创建失败，这里是容错！");
            }
        };
    }
}
