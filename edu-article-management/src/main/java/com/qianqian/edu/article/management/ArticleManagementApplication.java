package com.qianqian.edu.article.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author minsiqian
 * @date 2020/3/23 15:07
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.qianqian.edu.article.management","com.qianqian.edu.common"})
public class ArticleManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleManagementApplication.class,args);
    }
}
