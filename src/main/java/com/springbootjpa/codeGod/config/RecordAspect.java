package com.springbootjpa.codeGod.config;

import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.utils.AesUtils;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.thymeleaf.util.ArrayUtils;


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
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] obj = pjp.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            System.out.println(parameterNames[i]);
            if(parameterNames[i].equals("deCode")){
                if(obj[i].equals("Y") || obj[i].equals("y")){
                    for (int j = 0; j < obj.length; j++) {
                        if(obj[j] instanceof String){
                            if (obj[j].equals("y") || obj[j].equals("Y")){
                                continue;
                            }
                            obj[j] = aesUtils.deCode(obj[j].toString(), AjaxUtils.RSA_PUBLICAKEY);
                        }
                    }
                }
            }

        }


        return pjp.proceed(obj);
    }
}

