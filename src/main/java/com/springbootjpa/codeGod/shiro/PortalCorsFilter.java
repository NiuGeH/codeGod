package com.springbootjpa.codeGod.shiro;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class PortalCorsFilter extends CorsFilter{
    private static Logger logger = LoggerFactory.getLogger(PortalCorsFilter.class);
    public PortalCorsFilter() {
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        logger.info("init CorsFilter...");
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
//        config.addAllowedOrigin("允许跨域访问额地址");
//        config.addAllowedOrigin("允许跨域访问额地址");
//        config.addAllowedOrigin("允许跨域访问额地址");
//        config.addAllowedHeader("*");
        config.setMaxAge(36000L);
//        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}