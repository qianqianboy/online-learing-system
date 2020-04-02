package com.qianqian.edu.member.center.controller;

import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.member.center.util.ConstantPropertiesUtil;
import com.qianqian.edu.member.center.util.HttpClientUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author minsiqian
 * @date 2020/3/27 22:11
 */
@RestController
@RequestMapping("/oauth")
public class GithubLoginController {

    //回调地址
    @GetMapping("/callback/github")
    public R callback(String code, String state, HttpServletResponse response) throws Exception{

        if(!StringUtils.isEmpty(code)&&!StringUtils.isEmpty(state)){
            String token_url = ConstantPropertiesUtil.TOKEN_URL.replace("CODE", code);
            //得到的responseStr是一个字符串需要将它解析放到map中
            String responseStr = HttpClientUtils.doGet(token_url);
            // 调用方法从map中获得返回的--》 令牌
            String token = HttpClientUtils.getMap(responseStr).get("access_token");

            //根据token发送请求获取登录人的信息  ，通过令牌去获得用户信息
            String userinfo_url = ConstantPropertiesUtil.USER_INFO_URL.replace("TOKEN", token);
            responseStr = HttpClientUtils.doGet(userinfo_url);//json

            Map<String, String> responseMap = HttpClientUtils.getMapByJson(responseStr);
            // 成功则登陆
            return R.ok().data("responseMap",responseMap).message("GitHub登录成功！");
        }
        // 否则返回到登陆页面
        return R.error().message("GitHub登录失败！");
    }


}
