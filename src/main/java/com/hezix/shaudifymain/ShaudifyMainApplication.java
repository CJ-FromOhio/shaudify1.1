package com.hezix.shaudifymain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableCaching
 @EnableRedisRepositories(basePackages = "com.hezix.shaudifymain.repository")
public class ShaudifyMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaudifyMainApplication.class, args);

    }
}
