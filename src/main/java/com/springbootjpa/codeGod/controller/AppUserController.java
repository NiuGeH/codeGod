package com.springbootjpa.codeGod.controller;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.repository.SysUsersRolesRepository;
import com.springbootjpa.codeGod.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/AppUser")
public class AppUserController extends InitBinderController {
    private static Logger logger = LoggerFactory.getLogger(UserRolesController.class);


    @Resource
    RedisUtils redisUtils = new RedisUtils();

    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;

    @RequestMapping("login")
    @ResponseBody
    public AjaxResult login(String mb,HttpServletRequest request) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.out.println(mb+" aaa");
                return "mb";

            }
        });
    }
}
