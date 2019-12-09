package com.springbootjpa.codeGod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean //将组件注册在容器中
    public WebMvcConfigurer webMvcConfigurerAdapter(){
        return new WebMvcConfigurer(){

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                // /**  表示拦截所有路径下的所有请求
                registry.addInterceptor(new CodeGodHandlerInterceptor()).addPathPatterns("/AppUser/*")
                        .excludePathPatterns("/appIndex/IoReadImage/**");
            }
        };
    }

}
