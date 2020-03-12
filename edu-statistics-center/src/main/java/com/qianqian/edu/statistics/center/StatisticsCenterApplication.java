package com.qianqian.edu.statistics.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author minsiqian
 * @date 2020/3/12 16:47
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.qianqian.edu.statistics.center","com.qianqian.edu.common"})
@MapperScan("com.qianqian.edu.statistics.center.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class StatisticsCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsCenterApplication.class,args);
    }
}
