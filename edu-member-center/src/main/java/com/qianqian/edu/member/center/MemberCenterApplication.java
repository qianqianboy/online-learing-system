package com.qianqian.edu.member.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author minsiqian
 * @date 2020/3/12 0:12
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.qianqian.edu.member.center","com.qianqian.edu.common"})
@MapperScan("com.qianqian.edu.member.center.mapper")
@EnableDiscoveryClient
public class MemberCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberCenterApplication.class,args);
    }
}
