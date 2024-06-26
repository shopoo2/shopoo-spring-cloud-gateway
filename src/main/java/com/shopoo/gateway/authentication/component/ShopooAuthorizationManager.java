//package com.shopoo.gateway.authentication.component;
//
//import com.nimbusds.jose.JWSObject;
//import com.shopoo.gateway.authentication.config.JwtProperties;
//import com.shopoo.gateway.authentication.constant.AuthConstant;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import org.springframework.util.StringUtils;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @description: 其作用是在访问受保护的资源之前，对用户进行身份验证和授权，以决定用户是否有权访问该资源。
// * @author MaoYuan.Li
// * @date: 2023/3/16 14:21
// */
//@Component
//public class ShopooAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    private JwtProperties jwtProperties;
//
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
//        URI uri = request.getURI();
//        PathMatcher pathMatcher = new AntPathMatcher();
//        //白名单路径直接放行
//        List<String> ignoreUrls = Arrays.asList(jwtProperties.getIgnoreUrls());
//        for (String ignoreUrl : ignoreUrls) {
//            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
//                return Mono.just(new AuthorizationDecision(true));
//            }
//        }
//        //对应跨域的预检请求直接放行
//        if(request.getMethod()==HttpMethod.OPTIONS){
//            return Mono.just(new AuthorizationDecision(true));
//        }
//        //不同用户体系登录不允许互相访问
//        try {
//            String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
//            if(!StringUtils.hasText(token)){
//                return Mono.just(new AuthorizationDecision(false));
//            }
//            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
//            JWSObject jwsObject = JWSObject.parse(realToken);
//            String userStr = jwsObject.getPayload().toString();
////            UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
////            if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
////                return Mono.just(new AuthorizationDecision(false));
////            }
////            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
////                return Mono.just(new AuthorizationDecision(false));
////            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return Mono.just(new AuthorizationDecision(false));
//        }
//        //非管理端路径直接放行
//        if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
//            return Mono.just(new AuthorizationDecision(true));
//        }
//        //管理端路径需校验权限
//        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
//        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
//        List<String> authorities = new ArrayList<>();
//        while (iterator.hasNext()) {
//            String pattern = (String) iterator.next();
//            if (pathMatcher.match(pattern, uri.getPath())) {
//                authorities.add(String.valueOf(resourceRolesMap.get(pattern)));
//            }
//        }
//        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
//        //认证通过且角色匹配的用户可访问当前路径
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authorities::contains)
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//    }
//
//}
