package com.springbootjpa.codeGod.controller;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/AppUser")
public class AppUserController extends SysBase{
    private static Logger logger = LoggerFactory.getLogger(UserRolesController.class);




    @PostMapping(value = "login")
    @ResponseBody
    //POST 必须大写
//    @ApiOperation(value = "登录接口" , httpMethod = "POST" , notes = "说明")
    @ApiImplicitParams({
            //参数介绍 方便前端测试
            //POST(dataForm) paramType必须为query
            @ApiImplicitParam(name = "mb", value = "张三" ,example = "sda", required = true, paramType = "query"),
            @ApiImplicitParam(name = "Obj", value = "你好" , paramType="query")
    })
    public AjaxResult login(String deCode,String mb,String Obj , HttpServletRequest request) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.out.println(mb+" aaa");
                System.out.println(Obj);
                return "mb";
            }
        });
    }



    @RequestMapping("/findDataTest")
    @ResponseBody
    public AjaxResult findTest(){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                List<BaseDataDictionaryEntity> byColumNameRetrunDirctionaryAryList = baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList("member.member_display");
                System.out.println(byColumNameRetrunDirctionaryAryList.toString());
                return byColumNameRetrunDirctionaryAryList;
            }
        });
    }
}
