package com.qtdbp.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 权限管理启动类
 *
 * @author: caidchen
 * @create: 2017-05-11 9:14
 * To change this template use File | Settings | File Templates.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.qtdbp.security.repository")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}