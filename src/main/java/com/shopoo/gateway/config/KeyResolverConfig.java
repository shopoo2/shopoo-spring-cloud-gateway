package com.shopoo.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * @Author MaoYuan.Li
 * @Date 2023/3/1 17:21
 * @Version 1.0
 */
@Configuration
public class KeyResolverConfig {
    /**
     * @description: 根据IP请求进行限流
     * @param
     * @return: org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @author MaoYuan.Li
     * @date: 2023/3/1 17:22
     */
    @Bean
    @Primary
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostString());
    }
    
    /**
     * @description: 根据Principal请求限流
     * @param
     * @return: org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @author MaoYuan.Li
     * @date: 2023/3/2 16:25
     */
    @Bean
    KeyResolver principalKeyResolver() {
        return exchange -> exchange.getPrincipal().map(Principal::getName).switchIfEmpty(Mono.empty());
    }
}
