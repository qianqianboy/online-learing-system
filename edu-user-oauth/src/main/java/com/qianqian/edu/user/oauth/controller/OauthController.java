package com.qianqian.edu.user.oauth.controller;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.user.oauth.service.AuthService;
import com.qianqian.edu.user.oauth.util.AuthToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author minsiqian
 * @date 2020/4/1 1:03
 */
@RestController
@RequestMapping("/edu/oauth")
public class OauthController {

    @Autowired
    private AuthService authService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @ApiOperation("登录认证")
    @PostMapping("/login")
    public R login(@ApiParam("手机号") String mobile,
                   @ApiParam("密码") String password){
        //申请令牌
        AuthToken token =authService.login(mobile,password,clientId,clientSecret);
        //存储令牌到Cookie
        authService.addCookie(token.getJti());
        return R.ok().data("jtiToken",token.getJti()).message("登录成功！");
    }


    @ApiOperation("退出登录")
    public R logout(){
        
        return R.ok().message("成功退出");
    }
}
