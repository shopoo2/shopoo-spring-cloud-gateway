package com.shopoo.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
