package com.springbootjpa.codeGod.controller;

import com.google.gson.Gson;
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
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("登录/修改")
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
    public AjaxResult modifyPwd(@RequestBody String entity) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                SysUsersEntity sysUsersEntity = g.fromJson(entity, SysUsersEntity.class);
                if(ObjectUtils.isEmpty(sysUsersEntity.getId())){
                    throw new NullPointerException("Id为空");
                }else if(ObjectUtils.isEmpty(sysUsersEntity.getPassword())){
                    throw new NullPointerException("原密码为空");
                }else if(ObjectUtils.isEmpty(sysUsersEntity.getNewPwd())){
                    throw new NullPointerException("新密码为空");
                }else{
                    sysUsersService.updatePwd(sysUsersEntity);
                }
                return "success";
            }
        });
    }

    @RequestMapping("/loginHtml")
    public ModelAndView loginHtml(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "后台登录接口", httpMethod = "POST", notes = "后台登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{\n" +
                    "\"username\":\"test1\",\n" +
                    "\"password\":\"123\"\n" +
                    "}", example = "sda", required = true, paramType = "body"),
            @ApiImplicitParam(name = "sysUsersEntity", value = "实体", paramType = "body"),
    })
    public AjaxResult login(@RequestBody String sysUsersEntity, HttpServletRequest request) {

        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.out.println(sysUsersEntity);
                SysUsersEntity sysUsersEntity1 = g.fromJson(sysUsersEntity, SysUsersEntity.class);
                String username = sysUsersEntity1.getUsername();
                String password = sysUsersEntity1.getPassword();
                System.out.println(username);
                System.out.println(password);
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                Subject subject = SecurityUtils.getSubject();
                try {
                    subject.login(token);
                    SysUsersEntity ent = (SysUsersEntity) subject.getPrincipal();
                    request.getSession().setAttribute("user", username);
                    logger.info("用户登录成功: " + ent.toString());
                    ent.setPassword("");
                    return ent;
                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("user", username);
                    request.setAttribute("error", "用户名或密码错误！");
                    logger.info("用户名或密码错误" + this.getClass());
                    throw new Exception("用户名或密码错误");
                }

            }
        });

    }

}
