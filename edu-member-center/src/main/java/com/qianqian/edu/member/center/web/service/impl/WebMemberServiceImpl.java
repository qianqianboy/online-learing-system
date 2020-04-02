package com.qianqian.edu.member.center.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqian.edu.common.dto.form.OrderInfoForm;
import com.qianqian.edu.common.entity.EduMember;
import com.qianqian.edu.common.entity.EduOrder;
import com.qianqian.edu.common.util.JwtUtil;
import com.qianqian.edu.common.vo.CourseWebVo;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.member.center.mapper.EduMemberMapper;
import com.qianqian.edu.member.center.web.service.CourseService;
import com.qianqian.edu.member.center.web.service.OrderService;
import com.qianqian.edu.member.center.web.service.PayService;
import com.qianqian.edu.member.center.web.service.WebMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/17 18:49
 */
@Service
@Slf4j
public class WebMemberServiceImpl extends ServiceImpl<EduMemberMapper, EduMember> implements WebMemberService {


    @Autowired
    private OrderService orderService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PayService payService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Override
    public R checkLogin(String mobile, String password) {
        QueryWrapper<EduMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        EduMember member = baseMapper.selectOne(wrapper);
        if (member!=null){
            if (BCrypt.checkpw(password,member.getPassword())){
                //创建令牌信息
                Map<String, Object> map = new HashMap<>();

                map.put("role","USER");
                map.put("success","SUCCESS");
                map.put("moblie",mobile);
                try {
                    String token=JwtUtil.createJWT(member.getId(), JSON.toJSONString(map),null,null);
                    Cookie cookie=new Cookie("Authoritarian",token);
                    cookie.setDomain("localhost");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return R.ok().message("登录成功！").data("token",token);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            return R.error().message("手机号或密码错误，请核对正确后登录！");
        }
        return  R.error().message("手机号或密码错误，请核对正确后登录！");
    }

    @Override
    public R buySingle(String memberId,String courseId,String describe) {
        //1.拿到购买的课程信息和买家信息
        CourseWebVo course =courseService.selectById(courseId);
        EduMember member = baseMapper.selectById(memberId);
        //2.初始化订单信息
        EduOrder order = new EduOrder();
        order.setBuyerId(memberId);
        order.setOrderName(course.getTitle());
        order.setBossName(course.getTeacherName());
        order.setBuyerName(member.getNickname());
        order.setDescription(describe);
        order.setTotalPrice(course.getPrice());
        order.setPaymentWay(OrderInfoForm.ALI_PAY);

        //3.返回创建的订单
         return orderService.createOrder(order);
    }

    @Override
    public void toPay(OrderInfoForm orderVo) throws  IOException{
        //向支付微服务提交订单信息
        payService.alipay(orderVo);
    }
}
