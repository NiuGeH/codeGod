package com.springbootjpa.codeGod.controller;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.sys.SysRolesRermissionsEntity;
import com.springbootjpa.codeGod.entity.sys.SysUsersRolesEntity;
import com.springbootjpa.codeGod.repository.SysRolesRermissionsRepository;
import com.springbootjpa.codeGod.repository.SysUsersRolesRepository;
import com.springbootjpa.codeGod.utils.RedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserRolesController {
    private static Logger logger = LoggerFactory.getLogger(UserRolesController.class);

    @Resource
    RedisUtils redisUtils = new RedisUtils();

    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;

    @Autowired
    private SysRolesRermissionsRepository sysRolesRermissionsRepository;

    private  Map<String, Object> keyMapVal = new HashMap<String, Object>();



    @RequestMapping("tex")
    @ResponseBody
    public AjaxResult tex(){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
//                try {
                    Object one = redisUtils.get("one");
                    if(!(ObjectUtils.isEmpty(one))){
                        System.err.println("Redis");
                        return one;
                    }else{
                        System.err.println("No    Redis");
                        List<SysUsersRolesEntity> all = sysUsersRolesRepository.findAll();
                        SysUsersRolesEntity sysUsersRolesEntity = all.get(0);
                        List<SysRolesRermissionsEntity> byRoleId = sysRolesRermissionsRepository.findByRoleId(sysUsersRolesEntity.getRoleId());
                        redisUtils.set("one",byRoleId,20000);
                        return byRoleId;
                    }
//                }catch (RedisConnectionFailureException e){
//                    logger.error("Redis 异常");
//                    e.printStackTrace();
//                    return "Redis 异常";
//                }

//                Object  one = redisUtils.get("one");
//                return byRoleId;
            }
        });
    }

    @RequestMapping("/loginHtml")
    public ModelAndView loginHtml(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("login.html");
        return modelAndView;
    }

    @PostMapping("login")
    public String login(String username,String password,HttpServletRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            // 开始认证，这一步会跳到我们自定义的 Realm 中
            subject.login(token);
            request.getSession().setAttribute("user", username);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("user", username);
            request.setAttribute("error", "用户名或密码错误！");
            return "login";
        }

    }

}
