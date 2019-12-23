package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationCaseEntity;
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
 * @date 2019/12/16 15:42
 */
@Api(value = "/case", description = "案例类型管理Controller")
@RequestMapping("/case")
@RestController
@Slf4j
public class OperationCaseController extends OperationBase {

    @PostMapping(value = "/addCase", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加案例类型", httpMethod = "POST", notes = "caseName/案例名称  \n  caseOrder/案例排序  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'caseName':'电子商务','caseOrder':'4','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addCase(@RequestBody String json){
        log.info("URL:/case/addCase 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCaseEntity caseEntity = operationCaseService.addCase(
                        String.valueOf(hashMap.get("caseName")),
                        ObjectUtils.isEmpty(hashMap.get("caseOrder")) ? null : Long.valueOf(hashMap.get("caseOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return caseEntity;
            }
        });
    }


    @PostMapping(value = "/updateCase", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改案例类型", httpMethod = "POST", notes = "id/案例id newCaseName/案例新名称  \n  caseOrder/案例排序  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'5','newCaseName':'社交1','caseOrder':'5','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateCase(@RequestBody String json){
        log.info("URL:/case/updateCase 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCaseEntity caseEntity = operationCaseService.updateCase(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("newCaseName")),
                        ObjectUtils.isEmpty(hashMap.get("caseOrder")) ? null : Long.valueOf(hashMap.get("caseOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return caseEntity;
            }
        });
    }


    @PostMapping(value = "/findAllCase", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "案例类型分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationCaseEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/case/findAllCase 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "caseOrder");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationCaseEntity>>() {
            @Override
            public Page<OperationCaseEntity> invoke(Pageable page) throws Exception {
                Page<OperationCaseEntity> all = operationCaseService.findAll(page);
                return all;
            }
        });
    }
}
