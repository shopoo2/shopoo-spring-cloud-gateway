package com.shopoo.gateway.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author MaoYuan.Li
 * @Date 2023/2/28 16:51
 * @Version 1.0
 */
@Slf4j
@Configuration
public class GatewayConfig {
    
    @Bean
    public GlobalFilter requestLoggerFilter() {
        return (exchange, chain) -> {
            log.info("Request URI: {}", exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }

}
