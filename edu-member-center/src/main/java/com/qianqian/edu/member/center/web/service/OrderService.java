package com.qianqian.edu.member.center.web.service;

import com.qianqian.edu.common.entity.EduOrder;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.member.center.web.service.fallback.OrderServiceFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author minsiqian
 * @date 2020/3/17 19:30
 */
@FeignClient(value = "edu-order",fallbackFactory = OrderServiceFallbackFactory.class)
public interface OrderService {

    @RequestMapping("/edu/order/createOrder")
    R createOrder(@ApiParam(name = "order", value = "订单对象", required = true)
                  @RequestBody EduOrder order);

}
