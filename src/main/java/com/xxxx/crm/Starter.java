package com.xxxx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author xiaokaixin
 * @Date 2021/8/30 17:08
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.xxxx.crm.dao")
@EnableScheduling //启用定时任务
public class Starter {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }
}
