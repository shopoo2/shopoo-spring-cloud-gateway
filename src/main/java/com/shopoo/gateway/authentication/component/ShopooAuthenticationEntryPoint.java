package com.shopoo.gateway.authentication.component;

import com.google.gson.Gson;
import com.shopoo.dto.Response;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/** 
 * @description: 其作用是在用户访问受保护资源但未通过身份验证时，将用户重定向到身份验证页面或返回身份验证错误信息。
 * 它通常与 ServerSecurityContextRepository 和 ServerAuthenticationConverter 一起使用，以实现身份验证功能的完整流程
 * @author MaoYuan.Li
 * @date: 2023/3/16 14:33
 */
@Component
public class ShopooAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");
        String body= new Gson().toJson(Response.buildFailure(String.valueOf(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer));
    }
    
}
