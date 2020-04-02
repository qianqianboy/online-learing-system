package com.qianqian.edu.user.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.user.oauth.service.AuthService;
import com.qianqian.edu.user.oauth.util.AuthToken;
import com.qianqian.edu.user.oauth.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author minsiqian
 * @date 2020/4/2 2:03
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    private HttpServletResponse response;

    @Value("${auth.ttl}")
    private Long ttl;

    @Value("${auth.cookieDomain}")
    private  String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private  int cookieMaxAge;


    @Override
    public AuthToken login(String mobile, String password, String clientId, String clientSecret) {
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new QianQianException(2070,"账号或密码不能为空！");
        }

        AuthToken token = applyToken(mobile, password, clientId, clientSecret);
        if (token==null){
            throw new QianQianException(2080,"账号或密码错误！申请令牌失败！");
        }
        if (!saveToken(token)){
            throw new QianQianException(2081,"存储令牌到Redis操作失败！");
        }
        return token;
    }

    @Override
    public void addCookie(String jtiToken) {
        CookieUtil.addCookie(response,cookieDomain,"/","token",jtiToken,cookieMaxAge,false);
    }

    private AuthToken applyToken(String mobile, String password, String clientId, String clientSecret){
        ServiceInstance serviceInstance = loadBalancerClient.choose("edu-user-oauth");
        URI uri = serviceInstance.getUri();
        String authUrl=uri+"/oauth/token";

        //定义Header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization",getHttpBasic("changgou","changgou"));
        //定义Body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",mobile);
        body.add("password",password);
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<>(body,header);

        //账号密码错误不让报错
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode()!=400&&response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });
        ResponseEntity<Map> responseEntity = restTemplate.exchange(authUrl, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
        Map bodyMap = responseEntity.getBody();
        if (bodyMap==null
                ||bodyMap.get("access_token")==null
                ||bodyMap.get("refresh_token")==null
                ||bodyMap.get("jti")==null){
            return null;
        }
        AuthToken token = new AuthToken();
        token.setAccessToken(bodyMap.get("access_token").toString());
        token.setRefreshToken(bodyMap.get("refresh_token").toString());
        token.setJti(bodyMap.get("jti").toString());
        return token;
    }

    private boolean saveToken(AuthToken token){
        String key="user_token:"+token.getJti();
        String value = JSON.toJSONString(token);
        stringRedisTemplate.boundValueOps(key).set(value,ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key);
        return expire!=null&&expire>=0;
    }

    private String getHttpBasic(String clientId,String clientSecret){
        String str=clientId+":"+clientSecret;
        byte[] encode = Base64Utils.encode(str.getBytes());
        return "Basic "+new String(encode);
    }
}
