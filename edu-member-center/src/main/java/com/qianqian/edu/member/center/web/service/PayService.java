package com.qianqian.edu.member.center.web.service;

import com.qianqian.edu.common.dto.form.OrderInfoForm;
import com.qianqian.edu.member.center.web.service.fallback.PayServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

/**
 * @author minsiqian
 * @date 2020/3/17 21:49
 */
@FeignClient(value = "edu-payment",fallbackFactory = PayServiceFallbackFactory.class)
public interface PayService {

    @PostMapping(value = "alipay",consumes="application/json")
    void alipay( OrderInfoForm orderInfoForm) throws IOException;

}
