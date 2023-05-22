package com.shopoo.gateway.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 非对称加密配置文件信息读取
 * @Author: limy66
 * @Date:   2021/1/22 10:17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String url;
    private String[] ignoreUrls;

}
