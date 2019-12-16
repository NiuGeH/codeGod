package com.springbootjpa.codeGod.controller.HumanResources;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(description = "pc 用户表 Controller")
@Controller
@RequestMapping("/memberController")
public class MemberController extends MemberBase {




    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "查询用户基础信息", httpMethod = "POST", notes = "查询用户基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",
                    value = "{'page':'1','rows':'5'}",required = true, paramType = "body")
    })
    public PageResult<MemberEntity> doPage(@RequestBody String json) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = super.gson.fromJson(json, PageRequestParam.class);
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEntity>>() {
            @Override
            public Page<MemberEntity> invoke(Pageable var1) throws Exception {
                return memberService.findAll(var1);
            }
        });
    }

}
