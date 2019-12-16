package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Api(description = "需求管理Controller")
@RequestMapping("/PmDemandController")
@Controller
public class PmDemandController extends PmDemandBase {
    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);

    @PostMapping(value = "/findAllPmDemand", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "需求管理分页", httpMethod = "POST", notes = "需求分页全查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    ,required = true, paramType = "body")
    })
    public PageResult<PmDemandEntity> doPage(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmDemandController/findAllPmDemand 请求参数"+json);
        //HashMap<String,String> map= gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmDemandEntity>>() {
            @Override
            public Page<PmDemandEntity> invoke(Pageable pages) throws Exception {
                Page<PmDemandEntity> all = pmDemandService.findAll(pages);
                System.out.println(all.getSize());
                System.out.println(all.getTotalElements()+""+all.getTotalPages());
                for (PmDemandEntity pmDemandEntity:all) {
                    System.out.println(pmDemandEntity.getDemandPublisher());
                    System.out.println(pmDemandEntity.getDemandStatus1());
                    System.out.println(pmDemandEntity.getDemandDeliverTime());
                }
                return all;
            }
        });
    }

    @PostMapping(value = "/refuseByIdPmDemand", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "拒绝需求", httpMethod = "POST", notes = "拒绝需求接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1','demandRefusalCause':'做不了太难了'}" +
                    "\nfNE4sE7Clc/CagXQkLu4BpfZMZfbM1KhaVdYzgLGIdTIMCcJIzzU9PC/VPfZSVmmU9tYEMvhpg2yehNpM/cYBg=="
                    ,required = true, paramType = "body")
    })
    public AjaxResult<Object> refuseByIdPmDemand(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmDemandController/refuseByIdPmDemand 请求参数"+json);
                PmDemandEntity pmDemandEntity = gson.fromJson(json, PmDemandEntity.class);
                System.out.println(pmDemandEntity.getDemandRefusalCause()+"原因  id:"+pmDemandEntity.getId());
                int i = pmDemandService.updateDemand(pmDemandEntity.getDemandRefusalCause(), pmDemandEntity.getId());
                if(i>0){
                    return "success";
                }
                return "error";
            }
        });
    }



    @PostMapping(value = "/findAllProjectManager", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "报名产品经理分页", httpMethod = "POST", notes = "报名产品经理分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','demandId':'1'}" +
                    "0Gj755mNLsrxeUhCgPQWXmxctlIuTqSNF5t51HmgRcLBtwIWH3+hb8XC6dctRuKA"
                    ,required = true, paramType = "body")
    })
    public PageResult<PmDemandEntity> findAllProjectManager(@RequestBody String json) throws CodeGodException {
        logger.info("url:/findAllProjectManager/refuseByIdPmDemand 请求参数"+json);



        return null;
    }








}
