package com.qianqian.edu.member.center.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianqian.edu.common.dto.form.OrderInfoForm;
import com.qianqian.edu.common.entity.EduMember;
import com.qianqian.edu.common.vo.R;

import java.io.IOException;

/**
 * @author minsiqian
 * @date 2020/3/17 18:48
 */
public interface WebMemberService extends IService<EduMember> {

    /**
     * 验证登录
     * @param username
     * @param password
     * @return
     */
    R checkLogin(String username, String password);

    /**
     * 下单
     * @param courseId
     * @return
     */
    R buySingle(String memberId,String courseId,String describe);

    /**
     * 去支付
     * @param orderVo
     * @return
     */
    void toPay(OrderInfoForm orderVo) throws IOException;
}
