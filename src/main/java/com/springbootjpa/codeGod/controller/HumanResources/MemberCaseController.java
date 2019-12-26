package com.springbootjpa.codeGod.controller.HumanResources;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberCaseEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 用户的案例管理
 * @author NiuGeH
 * @date 2019/12/20
 */
@Api(description = "案例管理")
@Controller
@RequestMapping("/memberCase")
@Slf4j
public class MemberCaseController extends MemberBase{

    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "查询用户案例管理", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",
                    value = "{'page':'1','rows':'5','memberId':'2'}",required = true, paramType = "body")
    })
    public PageResult<MemberCaseEntity> doPage(@RequestBody String json) {
        log.info("URL:/memberCase/doPage 参数："+json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = super.gson.fromJson(json, PageRequestParam.class);
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberCaseEntity>>() {
            @Override
            public Page<MemberCaseEntity> invoke(Pageable var1) throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                return memberCaseService.findAll(var1, (String) hashMap.get("memberId"));
            }
        });
    }

    @PostMapping(value = "/doSaveOrUpdateMemberCase", headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "添加/编辑案例管理",httpMethod = "POST", notes = "编辑==>案例的Id不为空  添加==>Id为空  \n  postMan 直接复制粘贴 -->  \n" +
            "[{\"key\":\"casePlatformProject\",\"value\":\"1\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseProjectName\",\"value\":\"项目名称2\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseCodingAbility\",\"value\":\"1\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseAbilityToCommunicate\",\"value\":\"1\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseSchedulePerformance\",\"value\":\"1\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseTheDevelopmentCycle\",\"value\":\"1\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseProjectContribution\",\"value\":\"1\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseTheSkillsUsed\",\"value\":\"3\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseParticipateInTheRole\",\"value\":\"4\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"caseThePerformanceReview\",\"value\":\"绩效点评\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"casePhotoMultipartFile\",\"description\":\"\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/4ac90853-6734-4258-8e6e-fe90c18b029f.pdf\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberId\",\"value\":\"2\",\"description\":\"\",\"type\":\"text\",\"enabled\":true},{\"key\":\"id\",\"value\":\"12\",\"description\":\"\",\"type\":\"text\",\"enabled\":false}]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberCaseEntity"),
            @ApiImplicitParam(name = "memberPhotoFileMultipartFile" ,  paramType = "formData",value = "形象照"),
    })
    public AjaxResult<Object> doSaveOrUpdateMemberCase(MemberCaseEntity memberCaseEntity , @RequestParam("casePhotoMultipartFile")MultipartFile casePhotoMultipartFile){
        log.info("URL:/memberCase/doSaveOrUpdateMemberCase 参数: "+memberCaseEntity.toString());
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                if(ObjectUtils.isEmpty(memberCaseEntity.getId())){
                    log.info("案例的Id为空  进入添加的方法");
                    MemberCaseEntity memberCaseEntity1 = memberCaseService.doAdd(memberCaseEntity, casePhotoMultipartFile);
                    if(ObjectUtils.isEmpty(memberCaseEntity1)){
                        throw new CodeGodException("用户案例添加失败",this.getClass());
                    }else {
                        return memberCaseEntity1;
                    }
                }else {
                    log.info("案例Id为 :"+memberCaseEntity.getId() +" 进入修改方法");
                    return memberCaseService.doUpdate(memberCaseEntity, casePhotoMultipartFile);
                }
            }
        });
    }

    @PostMapping(value = "/findByCaseIdReturnCaseAndResource" , produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "根据caseId 返回对应的案例信息和用户所选的技术栈", httpMethod = "POST", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "caseId",value = "1",paramType = "body")
    })
    public AjaxResult<Object> findByCaseIdReturnCaseAndResource(@RequestBody String json){
        log.info("URL:/memberCase/findByCaseIdReturnCaseAndResource 参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object caseId = hashMap.get("caseId");
                if(ObjectUtils.isEmpty(caseId)){
                    throw new CodeGodException("caseId 为空 ",this.getClass());
                }else{
                    MemberCaseEntity byId = memberCaseentityRepository.findById(Long.valueOf((String)caseId)).orElseThrow(() -> new CodeGodRunTimExcetion("案例不存在",this.getClass()));
                    System.err.println(byId.getMemberId());
                    List<MemberResourceEentity> byMemberIdReturnResourceAndKill = memberService.findByMemberIdReturnResourceAndKill(byId.getMemberId());
                    HashMap<String , Object> resourceAndSkillMap = new HashMap<>();
                    resourceAndSkillMap.put("caseEntity",byId);
                    resourceAndSkillMap.put("resourceAndKill",byMemberIdReturnResourceAndKill);
                    return resourceAndSkillMap;
                }
            }
        });
    }
}
