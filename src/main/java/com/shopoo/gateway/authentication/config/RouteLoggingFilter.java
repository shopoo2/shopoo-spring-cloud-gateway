package com.shopoo.gateway.authentication.config;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/9/25 22:56
 */
@Slf4j
@Component
public class RouteLoggingFilter implements GlobalFilter, Ordered {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Incoming request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

		// Continue processing the request
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			log.info("Outgoing response: {} {}", exchange.getResponse().getStatusCode(), exchange.getResponse().getStatusCode());
		}));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
