package com.springbootjpa.codeGod.controller.HumanResources;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceSkillEntity;
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
                    return memberEmployPersonnelService.doPage(pages,HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_KHYQ.getIndex(),Long.valueOf(employId));
                }
            }
        });
    }

    @PostMapping(value = "/doAddReleaseRequireEcho", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "发布要求（点击邀请回显）",httpMethod = "POST",notes = "ent==>  对应实体  \n  skillList ==>  职务  \n  regionList  ==>  项目地址  \n  employDevelopmentWayList ==>  开发方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "{'id':'1'}",required = true)
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
                    List<MemberResourceEentity> byMemberIdReturnResourceAndKill = memberService.findByMemberIdReturnResourceAndKill(jd_mem.getMemberId().getId());
                    List<MemberResourceSkillEntity> listEnd = new ArrayList<>();
                    for (MemberResourceEentity memberResourceEentity : byMemberIdReturnResourceAndKill) {
                        System.err.println(memberResourceEentity.toString());
                        listEnd.addAll(memberResourceEentity.getMemberResourceSkillEntityList());
                    }
                    HashMap<String , Object> map = new HashMap<>();
                    map.put("ent",jd_mem);
                    map.put("skillList",listEnd);
                    map.put("regionList",operationRegionRepository.findAllByDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex()));
                    map.put("employDevelopmentWayList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_EMPLOY_PERSONNEL_DEVELOPMENT_WAY));
                    return map;
                }
            }
        });
    }


    @PostMapping(value = "/doAddReleaseRequire", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "发布要求（雇佣需求中 邀请中点击提交）",httpMethod = "POST", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "",required = true)
    })
    public AjaxResult<Object> doAddReleaseRequire(@RequestBody String json){
        log.info("URL:/memberEmployPersonnel/doAddReleaseRequire 请求参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {

                return null;
            }
        });
    }

}
