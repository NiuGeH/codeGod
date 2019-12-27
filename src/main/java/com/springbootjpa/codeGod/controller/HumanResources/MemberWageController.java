package com.springbootjpa.codeGod.controller.HumanResources;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberWageEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Api(description = "人力外包管理-->发工资Controller")
@RequestMapping("/wageController")
@Controller
@Slf4j
public class MemberWageController  extends MemberBase{

    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "发工资分页", httpMethod = "POST", notes = "contractId 合同Id(必须)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",
                    value = "{'page':'1','rows':'5','contractId':'3'}", required = true, paramType = "body")
    })
    public PageResult<MemberWageEntity> doPage(@RequestBody String json) {
        log.info("URL:/wageController/doPage 参数：" + json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = super.gson.fromJson(json, PageRequestParam.class);
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberWageEntity>>() {
            @Override
            public Page<MemberWageEntity> invoke(Pageable var1) throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object contractId = hashMap.get("contractId");
                if(ObjectUtils.isEmpty(contractId)) throw new CodeGodException("合同Id 为空 ",this.getClass());
                return memberWageService.doPageByContractId(var1,Long.valueOf(String.valueOf(contractId)));
            }
        });
    }

    @PostMapping(value = "/findWageById", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "根据Id 返回工资实体",httpMethod = "POST",notes ="employPersonnel.employMonthMoney==>工资  \n  "+
            "settlementCycleStart==>结算周期 Start  \n" +
            "settlementCycleEnd==>结算周期结束时间  \n" +
            "wageSocialSecurity==>社保缴纳  \n" +
            "wageAccumulationFund==>公积金缴纳  \n" +
            "wagePersonalTaxes==>个人税  \n" +
            "wageBonus==>奖金  \n" +
            "wageRealWages==>实发工资  \n" +
            "wagePaymentNote==>付款备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "{'id':'3'}",required = true,paramType = "body")
    })
    public AjaxResult<Object> findWageById(@RequestBody String json){
        log.info("URL: /wageController/findWageById 参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object id = hashMap.get("id");
                if(ObjectUtils.isEmpty(id)) throw new CodeGodException("id为空",this.getClass());
                MemberWageEntity memberWageEntity = memberWageentityRepository.findById(Long.valueOf(String.valueOf(id))).orElseThrow(() -> new CodeGodException("/wageController/findWageById ==> id 不存在", this.getClass()));
                MemberPrivacyEntity memberPricacy = memberWageEntity.getEmployPersonnel().getMemberId().getMemberPricacy();
                String memberRealName = memberPricacy.getMemberRealName();
//                HashMap>
                return null;
            }
        });
    }




}
