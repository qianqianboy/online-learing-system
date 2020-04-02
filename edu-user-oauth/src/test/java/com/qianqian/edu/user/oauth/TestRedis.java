package com.qianqian.edu.user.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author minsiqian
 * @date 2020/4/1 0:29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    public void testRedis(){
        String key="user_token:825fd1ea-ee03-4c0e-a713-3b5c679d1914";
        HashMap<String, String> value = new HashMap<>();
        value.put("access_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4NTcxMTM2OSwianRpIjoiODI1ZmQxZWEtZWUwMy00YzBlLWE3MTMtM2I1YzY3OWQxOTE0IiwiY2xpZW50X2lkIjoiY2hhbmdnb3UiLCJ1c2VybmFtZSI6ImNoYW5nZ291In0.GkNC1Uf1in7bS_qIIyTt3aw5qKpx6WPz8naSlBAdO_UlqB-fx4lhBVMazpCyUIULOIPfU87uFSIAWGulhhhpGb3pO5d2g2zdyBxWjcem_5eOE-JocmbFh1P-7D5EuFVD8VVAJF32irPBat6MMjfKXeCYtA9FH7laDzSWVMAL4fD8SK743zzIbDyxpF7zry57Og_V9DByenOD72IW9Rr-iDI0qc_9fMHft5efnRYi9i7YrXqX-GRUn5dVwgGqMR3lauL1tQQ95lCAlCKildwmREQpALktedoffKIHSn8bl1MIIXjsVbgC5diKyC6ZJMuNTdO7GaV0mzYgNXQFIY2e5g");
        value.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiYXRpIjoiODI1ZmQxZWEtZWUwMy00YzBlLWE3MTMtM2I1YzY3OWQxOTE0IiwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4NTcxMTM2OSwianRpIjoiYTdlMmU0OTQtOWE0MS00MGQ2LTg1MmItZTRjNDVkNjE4Y2Y1IiwiY2xpZW50X2lkIjoiY2hhbmdnb3UiLCJ1c2VybmFtZSI6ImNoYW5nZ291In0.BKIjbDaRNrEuAa1QHJEWSmC1yRmG8KxaDiASDUmbek4_tXBCWC60JsV7SEKhzmib97yhe2jrrIwgDFOCkQwQEUHgUMvkcViHUpepRkabF-XAv6k52gad3QgS8KqYHmu4lvg3mgpbQWrqkU4YRbyp6290BvjrMrm5vvz-qq21qrgn8KH6MjH8ezJEh880ntLQtFZa4i6EYv3R02prz9AsGN4bRbE35XETo3UovhiofoZWgQIyINqiIJ2CS6uMu-h8C3YruNzzkJXAOZk0QNryo_04SEDK3H87CjOpk70z8MxFelHzXpkoAbmYqxnoITqmXLjePFmyC5WVcmRv5zlnZA");
        String jsonString = JSON.toJSONString(value);
        stringRedisTemplate.boundValueOps(key).set(jsonString,90, TimeUnit.SECONDS);
        String s = stringRedisTemplate.opsForValue().get(key);
        System.out.println(s);
    }
    @Test
    public void testClient(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("edu-user-oauth");
        URI uri = serviceInstance.getUri();
        String authUrl=uri+"/oauth/token";

        //定义Header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization",getHttpBasic("changgou","changgou"));
        //定义Body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username","qianqiawqeqn");
        body.add("password","qianqian");
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
        System.out.println(bodyMap);
    }

    private String getHttpBasic(String clientId,String clientSecret){
        String str=clientId+":"+clientSecret;
        byte[] encode = Base64Utils.encode(str.getBytes());
        return "Basic "+new String(encode);
    }
}
