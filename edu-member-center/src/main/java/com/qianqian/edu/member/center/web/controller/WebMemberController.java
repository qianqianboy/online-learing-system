package com.qianqian.edu.member.center.web.controller;

import com.qianqian.edu.common.dto.form.OrderInfoForm;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.member.center.web.service.WebMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author minsiqian
 * @date 2020/3/17 18:44
 */
@Api(description = "会员在网站的行为")
@Controller
@RequestMapping("edu/member")
public class WebMemberController {

    @Autowired
    private WebMemberService memberService;


    @ApiOperation(value = "登录")
    @ResponseBody
    @PostMapping(value = "login/{mobile}/{password}")
    public R login(
            @ApiParam(name = "mobile", value = "手机号")
            @PathVariable String mobile,
            @ApiParam(name = "password", value = "密码")
            @PathVariable String password){
        return memberService.checkLogin(mobile,password);
    }

    @ApiOperation("下单")
    @ResponseBody
    @PostMapping("buySingle/{memberId}/{courseId}")
    public R createOrder(@PathVariable String memberId, @PathVariable String courseId, @RequestParam String describe){
        return memberService.buySingle(memberId,courseId,describe);
    }

    @ApiOperation("去支付")
    @PostMapping("toPay")
    public void toPay(
            @ApiParam(name = "orderVo", value = "订单传输对象", required = true)
           @RequestBody OrderInfoForm orderInfoForm) throws IOException {
       memberService.toPay(orderInfoForm);
    }

}
