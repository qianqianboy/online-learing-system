package com.qianqian.edu.permission.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author minsiqian
 * @date 2020/4/1 16:48
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.qianqian.edu")
@MapperScan("com.qianqian.edu.permission.management.mapper")
public class PermissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class,args);
    }
}
