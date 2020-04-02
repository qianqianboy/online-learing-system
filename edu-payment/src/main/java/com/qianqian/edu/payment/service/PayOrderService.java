package com.qianqian.edu.payment.service;

import com.qianqian.edu.common.entity.EduOrder;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.payment.service.fallbcak.PayOrderServiceFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author minsiqian
 * @date 2020/3/17 22:35
 */
@FeignClient(value = "edu-order",fallbackFactory = PayOrderServiceFallbackFactory.class)
public interface PayOrderService {

    @PostMapping(value = "/edu/order/updateOrder",consumes = "application/json")
    R updateOrder(@ApiParam(name = "order", value = "订单对象", required = true)
                         @RequestBody EduOrder order);
}
