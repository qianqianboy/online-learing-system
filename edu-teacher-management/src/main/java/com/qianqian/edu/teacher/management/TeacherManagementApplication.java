package com.qianqian.edu.teacher.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author minsiqian
 * @date 2020/2/22 16:22
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages={"com.qianqian.edu.teacher.management","com.qianqian.edu.common"})
public class TeacherManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherManagementApplication.class, args);
    }
}
