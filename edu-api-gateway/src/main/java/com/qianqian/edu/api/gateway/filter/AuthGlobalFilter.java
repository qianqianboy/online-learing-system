package com.qianqian.edu.api.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器：统一鉴权
 * @author minsiqian
 * @date 2020/3/3 21:12
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String token = exchange.getRequest().getQueryParams().getFirst("token");
//        if (StringUtils.isBlank(token)||!"admin".equals(token)) {
//            log.info("鉴权失败");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }//调用chain.filter继续向下游执行
        return chain.filter(exchange);
    }
    //顺序,数值越小,优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
