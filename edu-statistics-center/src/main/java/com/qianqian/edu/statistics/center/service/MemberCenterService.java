package com.qianqian.edu.statistics.center.service;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.statistics.center.fallback.MemberCenterServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author minsiqian
 * @date 2020/3/12 16:52
 */
@FeignClient(value = "edu-member-center",fallbackFactory = MemberCenterServiceFallbackFactory.class)
public interface MemberCenterService {

    /**
     * 根据指定日期查询当日注册用户数量
     * @param day 指定日期
     * @return 统一返回结果对象
     */
    @GetMapping("/admin/edu/member/count-register/{day}")
    R registerCount(@PathVariable(value = "day") String day);
}
