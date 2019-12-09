package com.springbootjpa.codeGod.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CodeGodHandlerInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(CodeGodHandlerInterceptor.class);

    //之后拦截作用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        Object user = request.getSession().getAttribute("TF_MEMBER");
//        if(user == null){
//            response.sendRedirect("/tianfuhealth/rep/project/appEnd/demo.html");
//            String ip = request.getRemoteAddr();
//            logger.warn("未授权登录 访问IP : "+ip + " 时间 "+ DateTimeUtils.getNowTimeNormalString());
//            return false;
//        }else {
//            return true;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
