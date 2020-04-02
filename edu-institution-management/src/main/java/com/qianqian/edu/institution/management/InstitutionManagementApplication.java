package com.qianqian.edu.institution.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author minsiqian
 * @date 2020/3/23 15:07
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.qianqian.edu.institution.management","com.qianqian.edu.common"})
public class InstitutionManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(InstitutionManagementApplication.class,args);
    }
}
