package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/23 16:33
 */
@Api(value = "/skill", description = "技术栈管理Controller")
@RequestMapping("/skill")
@RestController
@Slf4j
public class OperationSkillController extends OperationBase {

    @PostMapping(value = "/addSkill", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加技术栈", httpMethod = "POST", notes = "skillName/技术栈名称  \n  skillOrder/技术栈排序  \n  display/是否显示：0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'skillName':'','skillOrder':'','display':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addSkill(@RequestBody String json){
        log.info("URL:/skill/addSkill 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationSkillEntity skillEntity = operationSkillService.addSkill(
                        String.valueOf(hashMap.get("skillName")),
                        ObjectUtils.isEmpty(hashMap.get("skillOrder")) ? null : Long.valueOf(hashMap.get("skillOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return skillEntity;
            }
        });
    }


    @PostMapping(value = "/updateSkill", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改技术栈", httpMethod = "POST", notes = "id/技术栈id newName/技术栈新名称  \n  skillOrder/技术栈排序  \n  display/是否显示：0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'','newName':'','skillOrder':'','display':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateCase(@RequestBody String json){
        log.info("URL:/skill/updateSkill 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationSkillEntity skillEntity = operationSkillService.updateSkill(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("newName")),
                        ObjectUtils.isEmpty(hashMap.get("skillOrder")) ? null : Long.valueOf(hashMap.get("skillOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return skillEntity;
            }
        });
    }


    @PostMapping(value = "/findAllSkill", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "技术栈分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationSkillEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/skill/findAllSkill 请求参数：" + json);
        List<String> list = new ArrayList();
        list.add("skillOrder");
        list.add("id");
        Sort sort = new Sort(Sort.Direction.ASC, list);
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationSkillEntity>>() {
            @Override
            public Page<OperationSkillEntity> invoke(Pageable page) throws Exception {
                Page<OperationSkillEntity> all = operationSkillService.findAll(page);
                return all;
            }
        });
    }


    @PostMapping(value = "/findSkill", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询全部技术栈id和名称", httpMethod = "POST", notes = "供添加资源类型时调用")
    public AjaxResult<Object> findSkill() {
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                return operationSkillService.findAll();
            }
        });
    }
}
