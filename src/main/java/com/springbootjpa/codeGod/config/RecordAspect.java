package com.springbootjpa.codeGod.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.utils.AesUtils;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;


@Aspect
@Configuration
public class RecordAspect {
    private static Logger logger = LoggerFactory.getLogger(RecordAspect.class);
    // 定义切点Pointcut
    @Pointcut("execution(* com.springbootjpa.codeGod.*controller..*.*(..))")
    public void excudeService() {
    }

    private AesUtils aesUtils = new AesUtils();
    @Around("excudeService()")
    public Object before(ProceedingJoinPoint pjp) throws Throwable {
        Object[] obj = pjp.getArgs();
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] instanceof String){
                obj[i] = aesUtils.deCode(obj[i].toString(), AjaxUtils.RSA_PUBLICAKEY);
            }
        }
        return pjp.proceed(obj);
    }
}

