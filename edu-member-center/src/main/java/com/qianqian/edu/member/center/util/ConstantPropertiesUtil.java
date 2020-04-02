package com.qianqian.edu.member.center.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author minsiqian
 * @date 2020/3/16 0:46
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {
    @Value("${github.app_id}")
    private String clientId;

    @Value("${github.app_secret}")
    private String clientSecret;

    @Value("${github.redirect_url}")
    private String redirectUrl;

    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static String REDIRECT_URL;

    //获取code的url
    public static String CODE_URL ;
    //获取token的url
    public static String TOKEN_URL ;
    //获取用户信息的url
    public static String USER_INFO_URL ;

    @Override
    public void afterPropertiesSet() throws Exception {
        CLIENT_ID = clientId;
        CLIENT_SECRET = clientSecret;
        REDIRECT_URL = redirectUrl;
        CODE_URL="https://github.com/login/oauth/authorize?client_id="+CLIENT_ID+"&state=STATE&redirect_uri="+REDIRECT_URL+"";
        TOKEN_URL= "https://github.com/login/oauth/access_token?client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&code=CODE&redirect_uri="+REDIRECT_URL+"";
        USER_INFO_URL="https://api.github.com/user?access_token=TOKEN";
    }
}
