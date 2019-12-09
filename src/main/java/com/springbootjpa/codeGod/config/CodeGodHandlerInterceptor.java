package com.springbootjpa.codeGod.config;

import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.utils.AesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@PropertySource({"classpath:application.properties","classpath:application-dev.properties"})
@Component
public class CodeGodHandlerInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(CodeGodHandlerInterceptor.class);

    //之后拦截作用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        for (String s : parameterMap.keySet()) {
//            System.out.println(s);
//            String[] strings = parameterMap.get(s);
//            for (int i = 0; i < strings.length; i++) {
//                System.out.println(strings[i]);
//                strings[i] = aesUtils.deCode(strings[i], AjaxUtils.RSA_PUBLICAKEY);
//                System.out.println(strings[i]);
//            }
//            parameterMap.put(s,strings);
//        }
//        parameterServletRequestWrapper.addParameter("","");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
