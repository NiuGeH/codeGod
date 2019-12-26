package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationTeamEntity;
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

import java.util.HashMap;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/26 10:13
 */
@Api(value = "/team", description = "服务团队管理Controller")
@RequestMapping("/team")
@RestController
@Slf4j
public class OperationTeamController extends OperationBase {

    @PostMapping(value = "/addTeam", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加服务团队", httpMethod = "POST", notes = "teamName 团队名称  \n  cityId 所属城市id  \n  " +
            "teamPhone 团队电话  \n  teamEmail 团队邮箱  \n  teamAddress 团队地址  \n  longitude 经度  \n  latitude 纬度  \n  state 状态  \n  remark 备注或口号")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'teamName':'战狼团队','cityId':'1','teamPhone':'15922873234','teamEmail':'123@123.com'," +
                            "'teamAddress':'重庆沙坪坝汉语路','longitude':'123.3434','latitude':'23.343243','state':'0','remark':'1111111111111111'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addTeam(@RequestBody String json){
        log.info("URL:/team/addTeam 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationTeamEntity teamEntity = operationTeamService.addTeam(
                        ObjectUtils.isEmpty(hashMap.get("cityId")) ? null : Long.valueOf(hashMap.get("cityId")),
                        String.valueOf(hashMap.get("teamName")),
                        String.valueOf(hashMap.get("teamPhone")),
                        String.valueOf(hashMap.get("teamEmail")),
                        String.valueOf(hashMap.get("teamAddress")),
                        String.valueOf(hashMap.get("longitude")),
                        String.valueOf(hashMap.get("latitude")),
                        ObjectUtils.isEmpty(hashMap.get("state")) ? null : Integer.valueOf(hashMap.get("state")),
                        String.valueOf(hashMap.get("remark")));
                return teamEntity;
            }
        });
    }


    @PostMapping(value = "/updateTeam", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改服务团队", httpMethod = "POST", notes = "teamId 团队id  \n  teamName 团队名称  \n  cityId 所属城市id  \n  " +
            "teamPhone 团队电话  \n  teamEmail 团队邮箱  \n  teamAddress 团队地址  \n  longitude 经度  \n  latitude 纬度  \n  state 状态  \n  remark 备注或口号")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'teamId':'2','teamName':'战狼团队','cityId':'1','teamPhone':'15922873234','teamEmail':'123@123.com'," +
                            "'teamAddress':'重庆沙坪坝汉语路','longitude':'123.3434','latitude':'23.343243','state':'0','remark':'1111111111111111'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateTeam(@RequestBody String json){
        log.info("URL:/team/updateTeam 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationTeamEntity teamEntity = operationTeamService.updateTeam(
                        ObjectUtils.isEmpty(hashMap.get("teamId")) ? null : Long.valueOf(hashMap.get("teamId")),
                        ObjectUtils.isEmpty(hashMap.get("cityId")) ? null : Long.valueOf(hashMap.get("cityId")),
                        String.valueOf(hashMap.get("teamName")),
                        String.valueOf(hashMap.get("teamPhone")),
                        String.valueOf(hashMap.get("teamEmail")),
                        String.valueOf(hashMap.get("teamAddress")),
                        String.valueOf(hashMap.get("longitude")),
                        String.valueOf(hashMap.get("latitude")),
                        ObjectUtils.isEmpty(hashMap.get("state")) ? null : Integer.valueOf(hashMap.get("state")),
                        String.valueOf(hashMap.get("remark")));
                return teamEntity;
            }
        });
    }


    @PostMapping(value = "/findAllTeam", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "服务团队分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationTeamEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/team/findAllTeam 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationTeamEntity>>() {
            @Override
            public Page<OperationTeamEntity> invoke(Pageable page) throws Exception {
                Page<OperationTeamEntity> all = operationTeamService.findAll(page);
                return all;
            }
        });
    }
}
