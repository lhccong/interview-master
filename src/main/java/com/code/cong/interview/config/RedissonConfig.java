package com.code.cong.interview.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 *
 * @author 86188
 * @date 2023/05/26
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {
    private Integer database;

    private String host;

    private Integer port;

    private String password;
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+host+":"+port)
                .setDatabase(database)
                .setPassword(password);
        return Redisson.create(config);
    }
}
