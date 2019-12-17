package com.springbootjpa.codeGod.controller;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import com.springbootjpa.codeGod.repository.SysRolesRermissionsRepository;
import com.springbootjpa.codeGod.repository.SysUsersRolesRepository;
import com.springbootjpa.codeGod.service.baseService.SysUsersService;
import com.springbootjpa.codeGod.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "后台人员Controller")
@Controller
@RequestMapping("/")
public class UserRolesController {
    private static Logger logger = LoggerFactory.getLogger(UserRolesController.class);

    private Gson g  = new Gson();

    @Autowired
    private SysUsersService sysUsersService;


    @PostMapping("/modifyPwd")
    @ResponseBody
    @ApiOperation(value = "后台管理员修改密码接口", httpMethod = "POST", notes = "后台管理员修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entity", required = true ,value = "{\n" +
                    "    \"id\":\"2\",\n" +
                    "    \"newPwd\":\"1234\",\n" +
                    "    \"password\":\"1234\"\n" +
                    "}",paramType = "body")

    })
    public AjaxResult<Object> modifyPwd(@RequestBody String entity) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("URL:/modifyPwd 请求参数: "+entity);
                SysUsersEntity sysUsersEntity = g.fromJson(entity, SysUsersEntity.class);
                if(ObjectUtils.isEmpty(sysUsersEntity.getId())){
                    throw new CodeGodException("Id为空",this.getClass());
                }else if(ObjectUtils.isEmpty(sysUsersEntity.getPassword())){
                    throw new CodeGodException("原密码为空",this.getClass());
                }else if(ObjectUtils.isEmpty(sysUsersEntity.getNewPwd())){
                    throw new CodeGodException("新密码为空",this.getClass());
                }else{
                    sysUsersService.updatePwd(sysUsersEntity);
                }
                return "success";
            }
        });
    }


    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "后台登录接口", httpMethod = "POST", notes = "后台登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUsersEntity", value = "SBmoyfl5sAaTxhvOhsM4xscKPCJNuiqCOZ3iMJnxiRpB53R347reSUKJfXSIo3tb \n{ \"username\":\"test1\", \"password\":\"123\" }",required = true, paramType = "body")
    })
    public AjaxResult<Object> login(@RequestBody String sysUsersEntity, HttpServletRequest request) {

        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("URL:/login 请求参数: "+sysUsersEntity);
                SysUsersEntity sysUsersEntity1 = g.fromJson(sysUsersEntity, SysUsersEntity.class);
                String username = sysUsersEntity1.getUsername();
                String password = sysUsersEntity1.getPassword();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                Subject subject = SecurityUtils.getSubject();
                try {
                    HashMap<String ,Object> hashMap = new HashMap<>();
                    subject.login(token);
                    SysUsersEntity ent = (SysUsersEntity) subject.getPrincipal();
                    request.getSession().setAttribute("user", username);
                    String id = request.getSession().getId();
                    hashMap.put("JSESSIONID",id);
//                    System.out.println(jsessionid.toString());
//                    Cookie[] cookies = request.getCookies();
//                    for (Cookie cookie : cookies) {
//                        System.out.println(cookie.getName() +" : "+cookie.getValue());
//                        hashMap.put(cookie.getName(),cookie.getValue());
//                    }
                    logger.info("用户登录成功: " + ent.toString());
                    ent.setPassword("");
                    hashMap.put("user",ent);
                    return hashMap;
                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("user", username);
                    request.setAttribute("error", "用户名或密码错误！");
                    throw new CodeGodException("用户名或密码错误",this.getClass());
                }

            }
        });

    }

}
