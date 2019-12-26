package com.springbootjpa.codeGod.controller.HumanResources;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.humanResources.*;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceSkillEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(description = "用户管理接口")
@Controller
@RequestMapping("/memberController")
@Slf4j
public class MemberController extends MemberBase {


    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "查询用户基础信息", httpMethod = "POST", notes = "查询用户基础信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",
                    value = "{'page':'1','rows':'5','memberDisplay':'0','memberStationing':'0','memberSigningPost':'0','memberType':'0' ,'keyWord':'' , 'memebrCityEntityId':'7'}",required = true, paramType = "body")
    })
    public PageResult<MemberEntity> doPage(@RequestBody String json) {
        log.info("URL:/memberController/doPage 参数："+json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = super.gson.fromJson(json, PageRequestParam.class);
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEntity>>() {
            @Override
            public Page<MemberEntity> invoke(Pageable var1) throws Exception {
                HashMap<String , String > hashMap = gson.fromJson(json,HashMap.class);
                return memberService.findAll(var1,hashMap.get("memberDisplay"),hashMap.get("memberStationing"),hashMap.get("memberSigningPost"),hashMap.get("memberType"),hashMap.get("keyWord"),hashMap.get("memebrCityEntityId"));
            }
        });
    }

    @PostMapping( value = "/editMemberEcho", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "点击资料编辑回显(memberId 必须)/点击添加用户(不需要传memberId)", httpMethod = "POST" , notes =
            "回显中取sigEnt 中的memberEndId对象中的数据 具体对象与/doSaveSign接口对应  \n  >>>>>>>>>>>>>>>>>>>>>>>>>  \n" +
                    "  signEnt  签约实体类  \n  memberType  用户类型  \n  memberSigningPost  签约岗位  \n  memberSigningMode  签约方式" +
                    "  \n  memberMedal  勋章  \n  memberStationing  是否驻场  \n  memberStatus  当前状态  \n  memberSigningStatus  签约状态" +
                    "  \n  memberTeam 团队")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json" , value = "{'memberId':'2'}")
    })
    public AjaxResult<HashMap<String , Object >> editMemberEcho(@RequestBody String json){
        log.info("URL:/memberController/editMemberEcho 参数列表: "+json);
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                HashMap<String, Object> hashMap = memberService.allDataFromDictionary();
                HashMap<String , String > params = gson.fromJson(json, HashMap.class);
                if(ObjectUtils.isEmpty(params.get("memberId"))){
                    return hashMap;
                }else{
                    Optional<MemberEntity> optional = memberentityRepository.findById(Long.valueOf(params.get("memberId")));
                    if(ObjectUtils.isEmpty(optional)){
                        throw new CodeGodException("id 为空 /memberController/editMemberEcho",this.getClass());
                    }else {
                        MemberEntity memberEntity = optional.get();
                        hashMap.put("MemberEnt",memberService.doStringConvarToList(memberEntity));
                        return hashMap;
                    }
                }

            }
        });
    }

    @PostMapping(value = "/findKeyWordLikeCity", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "根据城市名字模糊搜索" , httpMethod = "POST" , notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json" , value = "{'cityName':'重'}")
    })
    public AjaxResult<Object> findKeyWordLikeCity(@RequestBody String json){
        log.info("URL:/memberController/findKeyWordLikeCity 参数列表为:"+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String , String> hashMap = gson.fromJson(json,HashMap.class);
                return operationRegionRepository.findAllByCityNameAndDisplay("%"+hashMap.get("cityName")+"%", OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex());
            }
        });
    }

    @PostMapping(value = "/findKeyWordListCompany" , produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "根据企业名称查找企业" , httpMethod = "POST" , notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json" , value = "{'companyName':'酷贝'}")
    })
    public AjaxResult<Object> findKeyWordListCompany(@RequestBody String json){
        log.info("URL:/memberController/findKeyWordListCompany 参数列表 "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String , String > hashMap = gson.fromJson(json,HashMap.class);
                return operationCompanyRepository.findAllByCompanyNameAndCompanyDispay("%"+hashMap.get("companyName")+"%",OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex());
            }
        });
    }



    @PostMapping(value = "/basedSearch", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "分页搜索关键词数据", httpMethod = "POST",  notes = "memberDisplay==>是否公开  \n  memberStationing==>是否驻场  \n  memebrCityEntity==>城市  \n  " +
            "  memberSigningPost==>所有角色  \n  memberType==>所有用户")
    public AjaxResult<HashMap<String,Object>> basedSearch(){
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                HashMap<String , Object> hashMap = new HashMap<>();
                //是否显示
                hashMap.put("memberDisplay",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERDISPLAY));
                //是否驻场
                hashMap.put("memberStationing",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSTATIONING));
                //所有城市
                hashMap.put("memebrCityEntity",operationRegionRepository.findAllByDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex()));
                //所有角色
                hashMap.put("memberSigningPost",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGPOST));
                //所有用户
                hashMap.put("memberType",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERTYPE));

                return hashMap;
            }
        });
    }

    @PostMapping(value = "/doEditMember" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "资料编辑(比/SignContract/doSaveSgin接口多两个参数 )" , httpMethod = "POST" , notes = "多的两个参数：memberCompany >>> 所属公司  \n  memberTeam >>> 所属团队  \n  >>>>>>>>>>>>>>>>>>  " +
            "\n  id >>> memberId(必须) \n  memberMobile >>> 手机号  \n  memberPwd >>> 登录密码  \n  nickName >>> 昵称  \n" +
            "memberRealName >>> 姓名  \n  memberEmail >>> 邮箱  \n  memberQq >>> QQ  \n  memberWechat >>> 微信  \n  memebrCity >>> 所在城市  \n  memberContactAddress >>> 联系地址 " +
            "  \n  memberWord >>> 关键词  \n  memberLongRange >>> 远程开发报价  \n  memberOnSiteDevelopment >>> 驻场开发报价  \n memberCashWithdrawal >>> 提现账户  \n " +
            "memberIntroduce >>> 个人账户  \n  memberType >>> 用户类型  \n  memberCardno >>> 身份证号  \n  memberSigningPost >>> 签约岗位  \n  memberSigningMode >>> 签约方式  \n" +
            "memberMedal >>> 勋章  \n  memberStationing >>> 是否驻场  \n  memberStatus >>> 当前状态  \n  memberDisplay >>> 公开显示  \n  memberSigningStatus >>> 签约状态  \n  " +
            "memberPhotoFileMultipartFile(文件) >>> 形象照  \n  memberPhotoHeadPortraitMultipartFile(文件)  >>> 头像  \n  memberPersonalDataMultipartFile(文件 可多个) >>> 个人资料  \n" +
            "memberCardFrontMultipartFile >>> 身份证正面(文件)  \n  memberCardReverseSideMultipartFile >>> 身份证反面(文件)  \n  siginAgreementMultipartFile >>> 签约协议(文件 可多个)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberSignContractEntity" ),
            @ApiImplicitParam(name = "memberPrivacyEntity" ),
            @ApiImplicitParam(name = "memberEntity" ),
            @ApiImplicitParam(name = "memberPhotoFileMultipartFile" ,  paramType = "formData",value = "形象照"),
            @ApiImplicitParam(name = "memberPhotoHeadPortraitMultipartFile" , paramType = "formData",value = "头像"),
            @ApiImplicitParam(name = "memberPersonalDataMultipartFile" ,  paramType = "formData",value = "个人资料（可多个）"),
            @ApiImplicitParam(name = "memberCardFrontMultipartFile" ,  paramType = "formData",value = "身份证正面"),
            @ApiImplicitParam(name = "memberCardReverseSideMultipartFile" , paramType = "formData",value = "身份证反面"),
            @ApiImplicitParam(name = "siginAgreementMultipartFile" ,  paramType = "formData",value = "签约协议（可多个）")

    })
    @ResponseBody
    public AjaxResult<Object> doEditMember (MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity, String memberCompany,
                                            @RequestParam("memberPhotoFileMultipartFile") MultipartFile memberPhotoFileMultipartFile, @RequestParam("memberPhotoHeadPortraitMultipartFile") MultipartFile memberPhotoHeadPortraitMultipartFile,
                                            @RequestParam("memberPersonalDataMultipartFile") MultipartFile[] memberPersonalDataMultipartFile, @RequestParam("memberCardFrontMultipartFile") MultipartFile memberCardFrontMultipartFile,
                                            @RequestParam("memberCardReverseSideMultipartFile") MultipartFile memberCardReverseSideMultipartFile, @RequestParam("siginAgreementMultipartFile") MultipartFile[] siginAgreementMultipartFile
            , HttpServletRequest request){
        log.info("RUL:/memberController/doEditMember 参数列表: memberSignContractEntity："+memberSignContractEntity.toString() +"  用户私密信息表数据:"+memberPrivacyEntity.toString() + " 用户基本信息："+memberEntity.toString());
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                Optional<MemberEntity> byId = memberentityRepository.findById(memberSignContractEntity.getId());
                if(!ObjectUtils.isEmpty(byId)){
                    memberEntity.setMemberCompany(memberCompany);
                    MemberSignContractEntity byMemberEndId = memberSignContractentityRepository.findByMemberEndId(byId.get());
                    memberSignContractEntity.setId(byMemberEndId.getId());
                    memberSignContractService.signSetting(memberSignContractEntity, memberEntity, memberPrivacyEntity, memberPhotoFileMultipartFile, memberPhotoHeadPortraitMultipartFile, memberPersonalDataMultipartFile, memberCardFrontMultipartFile, memberCardReverseSideMultipartFile, siginAgreementMultipartFile, request);
                }else {
                    throw new CodeGodException("用户Id不存在",this.getClass());
                }
                return "success";
            }
        });
    }

    @PostMapping(value = "/doAddMember" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "添加用户(详细参数 同 doEditMember 不需要传Id)" , httpMethod = "POST" )
    @ResponseBody
    public AjaxResult<Object> doAddMember(MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity, String memberCompany,
                                          @RequestParam("memberPhotoFileMultipartFile") MultipartFile memberPhotoFileMultipartFile, @RequestParam("memberPhotoHeadPortraitMultipartFile") MultipartFile memberPhotoHeadPortraitMultipartFile,
                                          @RequestParam("memberPersonalDataMultipartFile") MultipartFile[] memberPersonalDataMultipartFile, @RequestParam("memberCardFrontMultipartFile") MultipartFile memberCardFrontMultipartFile,
                                          @RequestParam("memberCardReverseSideMultipartFile") MultipartFile memberCardReverseSideMultipartFile, @RequestParam("siginAgreementMultipartFile") MultipartFile[] siginAgreementMultipartFile
            , HttpServletRequest request){
        log.info("URL:/memberController/doAddMember 参数列表: memberSignContractEntity："+memberSignContractEntity.toString() +"  用户私密信息表数据:"+memberPrivacyEntity.toString() + " 用户基本信息："+memberEntity.toString());
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                memberSignContractService.signSetting(memberSignContractEntity, memberEntity, memberPrivacyEntity, memberPhotoFileMultipartFile, memberPhotoHeadPortraitMultipartFile, memberPersonalDataMultipartFile, memberCardFrontMultipartFile, memberCardReverseSideMultipartFile, siginAgreementMultipartFile, request);
                return "success";
            }
        });
    }

    @PostMapping(value = "/findResourceAll" ,produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "技能管理参数列表" , httpMethod = "POST" , notes = "allResourceAndSkill ==> 返回用户和所有资源对应的技术 allResourceAndSkill --> 下的resource 职位 resourceProficiency 对应的熟练度  \n  " +
            "allResourceAndSkill --> operationSkillEntityList 对应的技术 operationSkillEntityList --> skillProficiency 对应的熟练度 为空就为未选中  \n  resourceProficiencyList 对应职位的熟练度集合  \n  " +
            "skillProficiencyList 对应技术的熟练度集合")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "{'memberId':'2'}")
    })
    public AjaxResult<Object> findResourceAll(@RequestBody String json){
        log.info("URL:/memberController/findResourceAll 请求参数:"+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String ,String > hashMap1 = gson.fromJson(json, HashMap.class);
                String memberId = null;
                if(ObjectUtils.isEmpty(hashMap1.get("memberId"))){
                    throw new CodeGodException("memberId 为空",this.getClass());
                }else{
                     memberId = hashMap1.get("memberId");

                }
                HashMap<String , Object> endHash = new HashMap<>();
                List<OperationResourceSkillEntity> allByResourceAnAndOrderBySkill = operationResourceSkillRepository.findAllByResourceAnAndOrderBySkill(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex(), OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex());
                HashMap<Object , Object> hashMap = new HashMap<>();
                for (OperationResourceSkillEntity operationResourceSkillEntity : allByResourceAnAndOrderBySkill) {

                    Object o = hashMap.get(operationResourceSkillEntity.getResource());
                    if(ObjectUtils.isEmpty(o)){
                        List<OperationSkillEntity> list = new ArrayList<>();
                        list.add(operationResourceSkillEntity.getSkill());
                        operationResourceSkillEntity.setOperationSkillEntityList(list);
                        hashMap.put(operationResourceSkillEntity.getResource(),list);
                    }else{
                        List<OperationSkillEntity> list = (ArrayList<OperationSkillEntity>) o;
                        list.add(operationResourceSkillEntity.getSkill());
                        operationResourceSkillEntity.setOperationSkillEntityList(list);
                        hashMap.put(operationResourceSkillEntity.getResource(),list);
                    }
                }
                List<OperationResourceSkillEntity> list = new ArrayList<>();
                for (Map.Entry<Object, Object> objectObjectEntry : hashMap.entrySet()) {
                    OperationResourceSkillEntity os = new OperationResourceSkillEntity();
                    OperationResourceEntity key = (OperationResourceEntity) objectObjectEntry.getKey();
//                    os.setResource();
                    MemberResourceEentity allByMemberIdAndMemberOperationResource = memberResourceentityRepository.findAllByMemberIdAndMemberOperationResource(Long.valueOf(memberId), (OperationResourceEntity) objectObjectEntry.getKey());
                    if(!ObjectUtils.isEmpty(allByMemberIdAndMemberOperationResource)){
                        key.setResourceProficiency(allByMemberIdAndMemberOperationResource.getMemberProficiency());
                    }
                    os.setResource(key);
                    List<OperationSkillEntity> value = (List<OperationSkillEntity>) objectObjectEntry.getValue();
                    for (int i = 0; i < value.size(); i++) {
                        OperationSkillEntity operationSkillEntity = value.get(i);
                        MemberResourceSkillEntity allBySkillIdAndMemberResourceId = memberResourceSkillentityRepository.findAllBySkillIdAndMemberResourceId(operationSkillEntity, allByMemberIdAndMemberOperationResource.getId());
                        if(!ObjectUtils.isEmpty(allBySkillIdAndMemberResourceId)){
                            operationSkillEntity.setSkillProficiency(allBySkillIdAndMemberResourceId.getSkillProficiency());
                        }
                    }
                    os.setOperationSkillEntityList(value);
                    list.add(os);
                }
                list.sort(new Comparator<OperationResourceSkillEntity>() {
                    @Override
                    public int compare(OperationResourceSkillEntity o1, OperationResourceSkillEntity o2) {
                        if(ObjectUtils.isEmpty(o1.getResource().getResourceOrder()) || ObjectUtils.isEmpty(o2.getResource().getResourceOrder())){
                            return -1;
                        }
                        if(o1.getResource().getResourceOrder() > o2.getResource().getResourceOrder()){
                            return 1;
                        }else if (o1.getResource().getResourceOrder() <= o2.getResource().getResourceOrder()){
                            return -1;
                        }
                        return 0;
                    }
                });
                endHash.put("allResourceAndSkill",list);
                endHash.put("skillProficiencyList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBERRESOURCESKILL_SKILLPROFICIENCY));
                endHash.put("resourceProficiencyList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBERRESOURCE_MEMBERPROFICIENCY));

//                memberResourceentityRepository.
                return endHash;
            }
        });
    }

    @ApiOperation(value = "技能管理提交 " , httpMethod = "POST" , notes = "memberId ==> 用户Id 必须  ResourceAndListSkill 值存放集合  \n  " +
            "resourceId ==> 职位Id resourceProficiency ==> 职位对应的熟练度  \n  skillList ==> 存放职位所对应的技能集合  \n  skillId ==> 技能的Id  skillProficiency ==> 技能对应的熟练度")
    @ResponseBody
    @PostMapping(value = "doSaveResourceAndSkill",produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str" , value = "{ \"memberId\":\"2\" , \"ResourceAndListSkill\":[{ \"resourceId\":\"1\", \"resourceProficiency\":\"1\", \"skillList\":[{ \"skillId\":\"20\", \"skillProficiency\":\"2\" }] }]\n" +
                    "\n" +
                    "}")
    })
    public AjaxResult<Object> doSaveResourceAndSkill (@RequestBody String str){
        log.info("URL: /memberController/doSaveResourceAndSkill 参数列表: "+str);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                memberService.doSaveResourceAndSkillList(str);
                return "success";
            }
        });
    }


}
