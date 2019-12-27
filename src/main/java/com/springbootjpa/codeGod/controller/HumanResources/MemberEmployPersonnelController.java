package com.springbootjpa.codeGod.controller.HumanResources;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.*;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import io.swagger.annotations.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api(description = "指定人员")
@Controller
@RequestMapping("/memberEmployPersonnel")
@Slf4j
public class MemberEmployPersonnelController extends MemberBase{

    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "指定人员分页(需求管理中的指定用户分页)", httpMethod = "POST", notes = "employId 必须 对应的需求Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','employId':'1'}",required = true, paramType = "body")
    })
    public PageResult<MemberEmployPersonnelEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/memberEmployPersonnel/doPage 请求参数: "+json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEmployPersonnelEntity>>() {
            @Override
            public Page<MemberEmployPersonnelEntity> invoke(Pageable pages) throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                String employId = (String)hashMap.get("employId");
                if(ObjectUtils.isEmpty(employId)){
                    throw new CodeGodException("指定人员分页的需求Id为空 ",this.getClass());
                }else{
                    return memberEmployPersonnelService.doPage(pages,Long.valueOf(employId),HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_KHYQ.getIndex());
                }
            }
        });
    }


    @PostMapping(value = "/doPageAll")
    @ResponseBody
    @ApiOperation(value = "人力外包中人员管理(所有人员)",httpMethod = "POST",notes = "人力外包管理   employId==>需求Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','employId':'1'}", required = true, paramType = "body")
    })
    public PageResult<MemberEmployPersonnelEntity> doPageAll(@RequestBody String json)throws CodeGodException{
        log.info("URL:/memberEmployPersonnel/doPageAll 请求参数: "+json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEmployPersonnelEntity>>() {
            @Override
            public Page<MemberEmployPersonnelEntity> invoke(Pageable pages) throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                String employId = (String)hashMap.get("employId");
                return memberEmployPersonnelService.doPage(pages,Long.valueOf(employId));

            }
        });
    }

    @PostMapping(value = "/doAddReleaseRequireEcho", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "发布要求（点击邀请回显）",httpMethod = "POST",notes = "id==>分页中的id  ent==>  对应实体  \n  resourceList ==>  职务  \n  regionList  ==>  项目地址  \n  employDevelopmentWayList ==>  开发方式" +
            "\n  employWorkStatusList==> 状态(人力外包管理-->人员管理-->设置)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "{'id':'1'} ",required = true)
    })
    public AjaxResult<Object> doAddReleaseRequireEcho(@RequestBody String json){
        log.info("URL:/memberEmployPersonnel/doAddReleaseRequireEcho 请求参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object id = hashMap.get("id");
                if(ObjectUtils.isEmpty(id)){
                    throw new CodeGodException("参数id为空",this.getClass());
                }else{
                    MemberEmployPersonnelEntity jd_mem = memberEmployPersonnelentityRepository.findById(Long.valueOf(String.valueOf(id))).orElseThrow(() -> new CodeGodRunTimExcetion("雇佣信息不存在", this.getClass()));
                    List<OperationResourceEntity> byMemberIdReturnResourceAndKill = memberService.findByMemberIdReturnResource(jd_mem.getMemberId().getId());
                    HashMap<String , Object> map = new HashMap<>();
                    map.put("ent",jd_mem);
                    map.put("resourceList",byMemberIdReturnResourceAndKill);
                    map.put("regionList",operationRegionRepository.findAllByDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex()));
                    map.put("employDevelopmentWayList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY));
                    map.put("employWorkStatusList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_EMPLOY_PERSONNEL_WORK_STATUS));
                    return map;
                }
            }
        });
    }


    @PostMapping(value = "/doAddReleaseRequire", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "发布要求（雇佣需求中 指定人员 邀请中点击提交 或 人力外包管理中人员管理-->邀请人员(需传入employMemberIdForm id 不传)）",httpMethod = "POST", notes = "id==>(同/doAddReleaseRequireEcho传入的id)  \n  employIdFrom==>需求Id  \n  employPersionnelEntrustName==>客户名称  \n  employWorkContent==>工作内容  \n  " +
            "employPersionnelComeToStartTime==>到岗日  \n  employPersionnelAbortTime==>截止日  \n  employPersionnelSkillId==>  职务  \n  employMonthMoney==>月薪  \n  " +
            "employProjectAddressId==>地址  \n  employDevelopmentWay==>开发方式  \n  employPersionnelSiginType==>签约类型  \n  employMemberIdForm ==> 邀请人员Id(人力外包管理中的人员管理-->邀请人员 中需要)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "雇佣需求中 指定人员 -->邀请中点击提交  \n  {\n" +
                    "\t\"id\":\"1\",\n" +
                    "\t\"employIdFrom\":\"1\",\n" +
                    "\t\"employPersionnelEntrustName\":\"客户名称\",\n" +
                    "\t\"employWorkContent\":\"工作内容 1111\",\n" +
                    "\t\"employPersionnelComeToStartTime\":\"2019-1-1 12:12:00\",\n" +
                    "\t\"employPersionnelAbortTime\":\"2019-1-1 12:12:00\",\n" +
                    "\t\"employPersionnelSkillId\":\"1,1\",\n" +
                    "\t\"employMonthMoney\":\"2000\",\n" +
                    "\t\"employProjectAddressId\":\"1\",\n" +
                    "\t\"employDevelopmentWay\":\"1\",\n" +
                    "\t\"employPersionnelSiginType\":\"0\"\n" +
                    "}  \n  人力外包管理中人员管理-->邀请人员  \n  {\n" +
                    "\t\"employIdFrom\":\"1\",\n" +
                    "\t\"employPersionnelEntrustName\":\"客户名称\",\n" +
                    "\t\"employWorkContent\":\"工作内容 1111\",\n" +
                    "\t\"employPersionnelComeToStartTime\":\"2019-1-1 12:12:00\",\n" +
                    "\t\"employPersionnelAbortTime\":\"2019-1-1 12:12:00\",\n" +
                    "\t\"employPersionnelSkillId\":\"1,1\",\n" +
                    "\t\"employMonthMoney\":\"2000\",\n" +
                    "\t\"employProjectAddressId\":\"1\",\n" +
                    "\t\"employDevelopmentWay\":\"1\",\n" +
                    "\t\"employPersionnelSiginType\":\"0\",\n" +
                    "\t\"employMemberIdForm\":\"1\"\n" +
                    "}",required = true)
    })
    public AjaxResult<Object> doAddReleaseRequire(@RequestBody String json){
        log.info("URL:/memberEmployPersonnel/doAddReleaseRequire 请求参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object employIdFrom = hashMap.get("employIdFrom");
                Object employProjectAddressId = hashMap.get("employProjectAddressId");
                if(ObjectUtils.isEmpty(employIdFrom)) throw new CodeGodException("需求Id为空",this.getClass());
                if(ObjectUtils.isEmpty(employProjectAddressId)) throw new CodeGodException("地址Id为空",this.getClass());
                MemberEmployPersonnelEntity memberEmployPersonnelEntity = gson.fromJson(json, MemberEmployPersonnelEntity.class);
//                if(ObjectUtils.isEmpty(memberEmployPersonnelEntity.getId())) throw new CodeGodException("Id为空",this.getClass());
                MemberEmployEntity memberEmployEntity = memberEmployentityRepository.findById(Long.valueOf(String.valueOf(employIdFrom))).orElseThrow(() -> new CodeGodException("需求Id不存在", this.getClass()));
                OperationRegionEntity operationRegionEntity = operationRegionRepository.findById(Long.valueOf(String.valueOf(employProjectAddressId))).orElseThrow(() -> new CodeGodException("地址id不存在", this.getClass()));
                memberEmployPersonnelEntity.setEmployId(memberEmployEntity);
                memberEmployPersonnelEntity.setEmployProjectAddress(operationRegionEntity);
                Object employMemberIdForm = hashMap.get("employMemberIdForm");
                if(!(ObjectUtils.isEmpty(employMemberIdForm))){
                    MemberEntity aThrow = memberentityRepository.findById(Long.valueOf(String.valueOf(employMemberIdForm))).orElseThrow(() -> new CodeGodException("需邀请的人员不存在", this.getClass()));
                    memberEmployPersonnelEntity.setMemberId(aThrow);
                }
                return memberEmployPersonnelService.doAddReleaseRequire(memberEmployPersonnelEntity);
            }
        });
    }

    @PostMapping(value = "/doSetting", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "人力外包管理==>人员管理-->设置(提交 回显接口为/doAddReleaseRequireEcho)",httpMethod = "POST",notes = "\n" +
            "id==>分页中的id  \n" +
            "employPersionnelComeToStartTime==>入场时间  \n" +
            "employPersionnelAbortTime==>离场时间  \n" +
            "employPersionnelSkillId==>职务  \n" +
            "employWorkStatus==>状态  \n" +
            "employUnitPrice==>单价  \n" +
            "employMonthMoney==>月薪工资  \n" +
            "employProjectAddressId==>项目地址  \n" +
            "employDevelopmentWay==>开发方式")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(value = "{\n" +
                    "\t\"id\":\"1\", \n" +
                    "\t\"employPersionnelComeToStartTime\":\"2019-11-1 12:12:00\",\n" +
                    "\t\"employPersionnelAbortTime\":\"2019-11-2 12:12:00\",\n" +
                    "\t\"employPersionnelSkillId\":\"2,1\",\n" +
                    "\t\"employWorkStatus\":\"0\",\n" +
                    "\t\"employUnitPrice\":\"999\",\n" +
                    "\t\"employMonthMoney\":\"9999\",\n" +
                    "\t\"employProjectAddressId\":\"2\",\n" +
                    "\t\"employDevelopmentWay\":\"0\"\n" +
                    "}\n" +
                    "\n",name = "json",paramType = "body")
    })
    public AjaxResult<Object> doSetting(@RequestBody String json){
        log.info("URL: /memberEmployPersonnel/doSetting 参数："+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                MemberEmployPersonnelEntity memberEmployPersonnelEntity = gson.fromJson(json, MemberEmployPersonnelEntity.class);
                System.out.println(memberEmployPersonnelEntity.toString());
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object employProjectAddressId = hashMap.get("employProjectAddressId");
                if(ObjectUtils.isEmpty(employProjectAddressId)) throw new CodeGodException("地址Id为空",this.getClass());
                OperationRegionEntity operationRegionEntity = operationRegionRepository.findById(Long.valueOf(String.valueOf(employProjectAddressId))).orElseThrow(() -> new CodeGodException("地址id不存在", this.getClass()));
                memberEmployPersonnelEntity.setEmployProjectAddress(operationRegionEntity);
                return memberEmployPersonnelService.doSetting(memberEmployPersonnelEntity);
            }
        });
    }



    @PostMapping(value = "/doMakeSalary", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "造工资",notes = "contractIdForm==>合同Id  \n" +
            "employPersonnelForm==>雇佣人员Id  \n" +
            "settlementCycleStart==>结算周期 Start  \n" +
            "settlementCycleEnd==>结算周期结束时间  \n" +
            "wageSocialSecurity==>社保缴纳  \n" +
            "wageAccumulationFund==>公积金缴纳  \n" +
            "wagePersonalTaxes==>个人税  \n" +
            "wageBonus==>奖金  \n" +
            "wageRealWages==>实发工资  \n" +
            "wagePaymentNote==>付款备注",httpMethod = "POST")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "")
    })
    public AjaxResult<Object> doMakeSalary(@RequestBody String json){
        log.info("URL: /memberEmployPersonnel/doMakeSalary 参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                MemberWageEntity memberWageEntity = gson.fromJson(json, MemberWageEntity.class);
                System.err.println(memberWageEntity.toString());
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object contractIdForm = hashMap.get("contractIdForm");
                Object employPersonnelForm = hashMap.get("employPersonnelForm");
                if(ObjectUtils.isEmpty(contractIdForm)) throw new CodeGodException("合同Id为空",this.getClass());
                if(ObjectUtils.isEmpty(employPersonnelForm)) throw new CodeGodException("雇佣人员Id为空",this.getClass());
                memberWageEntity.setContractId(memberContractEntityRepository.findById(Long.valueOf(String.valueOf(contractIdForm))).orElseThrow(() -> new CodeGodException("合同 不存在",this.getClass())));
                memberWageEntity.setEmployPersonnel(memberEmployPersonnelentityRepository.findById(Long.valueOf(String.valueOf(employPersonnelForm))).orElseThrow(() -> new CodeGodException("雇佣人员不存在",this.getClass())));
                return memberWageService.doMakeSalary(memberWageEntity);
            }
        });
    }

}
