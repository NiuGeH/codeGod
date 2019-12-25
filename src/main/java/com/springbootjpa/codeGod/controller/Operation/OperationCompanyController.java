package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
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
 * @date 2019/12/25 10:59
 */
@Api(value = "/company", description = "合作企业管理Controller")
@RequestMapping("/company")
@RestController
@Slf4j
public class OperationCompanyController extends OperationBase {

    @PostMapping(value = "/addCompany", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加合作企业", httpMethod = "POST", notes = "companyName 企业名称  \n  companyAddress 企业地址  \n  " +
            "teamPhone 企业电话  \n  accountName 开户名称  \n  bank 开户行  \n  accountNumber 收款账户  \n  companyDisplay/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'companyName':'酷贝科技','companyAddress':'重庆沙坪坝汉语路','teamPhone':'15922873234','accountName':'重庆酷贝科技发展有限公司','bank':'工行××支行','accountNumber':'53784732874328','companyDisplay':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addCompany(@RequestBody String json){
        log.info("URL:/company/addCompany 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCompanyEntity companyEntity = operationCompanyService.addCompany(
                        String.valueOf(hashMap.get("companyName")),
                        String.valueOf(hashMap.get("companyAddress")),
                        String.valueOf(hashMap.get("teamPhone")),
                        String.valueOf(hashMap.get("accountName")),
                        String.valueOf(hashMap.get("bank")),
                        String.valueOf(hashMap.get("accountNumber")),
                        ObjectUtils.isEmpty(hashMap.get("companyDisplay")) ? null : Integer.valueOf(hashMap.get("companyDisplay")));
                return companyEntity;
            }
        });
    }


    @PostMapping(value = "/updateCompany", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改合作企业", httpMethod = "POST", notes = "id 被修改的合作企业id  \n  companyName 企业名称  \n  companyAddress 企业地址  \n  " +
            "teamPhone 企业电话  \n  accountName 开户名称  \n  bank 开户行  \n  accountNumber 收款账户  \n  companyDisplay/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'1','companyName':'酷贝科技','companyAddress':'重庆沙坪坝汉语路','teamPhone':'15922873234','accountName':'重庆酷贝科技发展有限公司','bank':'工行××支行','accountNumber':'53784732874328','companyDisplay':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addComupdateCompanypany(@RequestBody String json){
        log.info("URL:/company/updateCompany 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCompanyEntity companyEntity = operationCompanyService.updateCompany(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("companyName")),
                        String.valueOf(hashMap.get("companyAddress")),
                        String.valueOf(hashMap.get("teamPhone")),
                        String.valueOf(hashMap.get("accountName")),
                        String.valueOf(hashMap.get("bank")),
                        String.valueOf(hashMap.get("accountNumber")),
                        ObjectUtils.isEmpty(hashMap.get("companyDisplay")) ? null : Integer.valueOf(hashMap.get("companyDisplay")));
                return companyEntity;
            }
        });
    }


    @PostMapping(value = "/findAllCompany", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "合作企业分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationCompanyEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/company/findAllCompany 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationCompanyEntity>>() {
            @Override
            public Page<OperationCompanyEntity> invoke(Pageable page) throws Exception {
                Page<OperationCompanyEntity> all = operationCompanyService.findAll(page);
                return all;
            }
        });
    }
}
