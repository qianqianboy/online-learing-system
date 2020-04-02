package com.qianqian.edu.api.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器：统一认证中心
 * @author minsiqian
 * @date 2020/3/3 21:12
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final String AUTHORITARIAN_TOKEN="Authoritarian";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
//        //获取令牌信息
//        //先从请求头中获取
//        String token = request.getHeaders().getFirst(AUTHORITARIAN_TOKEN);
//        //说明token在请求头中
//        boolean hasToken=true;
//        //前者没有，再从参数中获取
//        if(StringUtils.isEmpty(token)){
//            token = request.getQueryParams().getFirst(AUTHORITARIAN_TOKEN);
//            //说明token不在请求头中
//            hasToken=false;
//        }
//        //前者还没有，再从Cookie中获取
//        if(StringUtils.isEmpty(token)){
//            HttpCookie cookie = request.getCookies().getFirst(AUTHORITARIAN_TOKEN);
//            if(cookie!=null){
//                token=cookie.getValue();
//            }
//        }
//        //前面都没有令牌，拦截
//        if(StringUtils.isEmpty(token)){
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
//        //如果有令牌，校验令牌签名
//        try {
//            JwtUtil.parseJWT(token);
//        } catch (Exception e) {
//            //签名失败出现异常，拦截
//            log.info("快去登录啦！！！");
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
//        //将token存入请求头中,以便OAuth2
//        request.mutate().header(AUTHORITARIAN_TOKEN,token);
//        //签名成功，放行
        return chain.filter(exchange);
    }
    //顺序,数值越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
