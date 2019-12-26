package com.springbootjpa.codeGod.controller.HumanResources;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Api(description = "推荐签约审核Controller")
@RequestMapping("/SignContract")
@Controller
public class MemberSignContractController extends MemberBase {

    private static Logger logger = LoggerFactory.getLogger(MemberSignContractController.class);

    @PostMapping("/findMapSign")
    @ResponseBody
    @ApiOperation(value = "验证码和签约结果对应的 key/value",notes = "返回值 sigin_results 对应 签约结果 siginVerificationCode 对应 验证码")
    public AjaxResult<Object> findMapSign(){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String ,Object > hashMap = new HashMap<>();
                hashMap.put("sigin_results",baseDataDirctionaryService.
                        findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBERSIGNCONTRACT_SIGIN_RESULTS));
                hashMap.put("siginVerificationCode",baseDataDirctionaryService.
                        findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBERSIGNCONTRACT_SIGNVERIFICATIONCODE));
                return hashMap;
            }
        });
    }


    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "签约审核分页", httpMethod = "POST", notes = "签约审核分页 validationCode==验证码   siginEnd==签约结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','validationCode':'0','siginEnd':'0'}" +
                    ""
                    ,required = true, paramType = "body")
    })
    public PageResult<MemberSignContractEntity> doPage(@RequestBody String json) throws CodeGodException {
        logger.info("URL:/SignContract/doPage 请求参数: "+json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberSignContractEntity>>() {
            @Override
            public Page<MemberSignContractEntity> invoke(Pageable pages) throws Exception {
                Page<MemberSignContractEntity> memberSignContractEntities = memberSignContractService.doPageByValidationCodeAndSiginEnd(
                        pages, ObjectUtils.isEmpty(hashMap.get("validationCode")) ? null : Integer.valueOf(hashMap.get("validationCode"))
                        , ObjectUtils.isEmpty(hashMap.get("siginEnd")) ? null : Integer.valueOf(hashMap.get("siginEnd")));
                for (MemberSignContractEntity memberSignContractEntity : memberSignContractEntities) {
                    BaseDataDictionaryEntity byDataKeyAndColumReturnDataValue = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(memberSignContractEntity.getSiginResults().toString(), DataBaseFinal.MEMBERSIGNCONTRACT_SIGIN_RESULTS);
                    memberSignContractEntity.setSiginResultsData(ObjectUtils.isEmpty(byDataKeyAndColumReturnDataValue) ? "未申请" : byDataKeyAndColumReturnDataValue.getDataValue());
                    BaseDataDictionaryEntity bd2 = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(memberSignContractEntity.getSiginVerificationCode().toString(), DataBaseFinal.MEMBERSIGNCONTRACT_SIGNVERIFICATIONCODE);
                    memberSignContractEntity.setSiginVerificationCodeData(ObjectUtils.isEmpty(bd2) ? "" : bd2.getDataValue());

                }
                return memberSignContractEntities;
            }
        });
    }

    @ApiOperation(value = "推荐签约审核删除接口" , notes = "签约结果未申请且验证码已过期 / 签约结果拒绝 删除")
    @PostMapping("/deleteSignMember")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json" ,value = "{'id':'1'}" , required = true , paramType = "body")
    })
    public AjaxResult<Object> deleteSignMember(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("URL:/SignContract/deleteSignMember 请求参数为:"+json);
                HashMap<String,String> map = gson.fromJson(json, HashMap.class);
                if(ObjectUtils.isEmpty(map.get("id")))
                    throw new CodeGodException("id为空",this.getClass());
                memberSignContractService.byIdDelMemberSignContract(Long.valueOf(map.get("id")));
                return "success";
            }
        });
    }

    @ApiOperation(value = "签约设置 回显用户pc端填写的数据" , httpMethod = "POST" , notes =
            "回显中取sigEnt 中的memberEndId对象中的数据 具体对象与/doSaveSign接口对应  \n  >>>>>>>>>>>>>>>>>>>>>>>>>  \n" +
                    "  signEnt  签约实体类  \n  memberType  用户类型  \n  memberSigningPost  签约岗位  \n  memberSigningMode  签约方式" +
                    "  \n  memberMedal  勋章  \n  memberStationing  是否驻场  \n  memberStatus  当前状态  \n  memberSigningStatus  签约状态  \n  memberTeam 团队")
    @PostMapping(value = "/saveSignEcho", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signId" , paramType = "body" ,required = true, value = "签约审核Id  \n  {signId:'2'}")
    })
    public AjaxResult<HashMap<String,Object>> saveSignEcho(@RequestBody String signId){
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                logger.info("URL:/SignContract/signId 请求参数为: "+signId);
                HashMap<String,String> hashMap1 = gson.fromJson(signId, HashMap.class);
                String signId1 = hashMap1.get("signId");
                HashMap<String , Object> hashMap = new HashMap<>();
                Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(Long.valueOf(signId1));
                if(ObjectUtils.isEmpty(byId)){
                    throw new CodeGodException("signId 不存在", this.getClass());
                }else{
                    MemberSignContractEntity entity = byId.get();
                    //获取签约审核中生成的MemberId
                    MemberEntity memberEndId = entity.getMemberEndId();
                    //根据生成的 Member 然后查找私人信息表
                    entity.setMemberEndId(memberService.doStringConvarToList(memberEndId));
                    hashMap.put("signEnt",entity);
                    HashMap<String, Object> allDataFromDictionary = memberService.allDataFromDictionary();
                    hashMap.putAll(allDataFromDictionary);
                }
                return hashMap;
            }
        });
    }

    @ApiOperation(value = "签约设置保存接口" , notes = "id >>> memberSignId(必须) \n  memberMobile >>> 手机号  \n  memberPwd >>> 登录密码  \n  nickName >>> 昵称  \n" +
            "memberRealName >>> 姓名  \n  memberEmail >>> 邮箱  \n  memberQq >>> QQ  \n  memberWechat >>> 微信  \n  memebrCity >>> 所在城市  \n  memberContactAddress >>> 联系地址 " +
            "  \n  memberWord >>> 关键词  \n  memberLongRange >>> 远程开发报价  \n  memberOnSiteDevelopment >>> 驻场开发报价  \n memberCashWithdrawal >>> 提现账户  \n " +
            "memberIntroduce >>> 个人账户  \n  memberType >>> 用户类型  \n  memberCardno >>> 身份证号  \n  memberSigningPost >>> 签约岗位  \n  memberSigningMode >>> 签约方式  \n" +
            "memberMedal >>> 勋章  \n  memberStationing >>> 是否驻场  \n  memberStatus >>> 当前状态  \n  memberDisplay >>> 公开显示  \n  memberSigningStatus >>> 签约状态  \n  " +
            "memberPhotoFileMultipartFile(文件) >>> 形象照  \n  memberPhotoHeadPortraitMultipartFile(文件)  >>> 头像  \n  memberPersonalDataMultipartFile(文件 可多个) >>> 个人资料  \n" +
            "memberCardFrontMultipartFile >>> 身份证正面(文件)  \n  memberCardReverseSideMultipartFile >>> 身份证反面(文件)  \n  siginAgreementMultipartFile >>> 签约协议(文件 可多个)")
    @PostMapping(value = "/doSaveSign", headers = "content-type=multipart/form-data")
    @ResponseBody
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
    public AjaxResult<Object> doSaveSgin(MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity,MemberPrivacyEntity memberPrivacyEntity,
                                         @RequestParam("memberPhotoFileMultipartFile") MultipartFile memberPhotoFileMultipartFile, @RequestParam("memberPhotoHeadPortraitMultipartFile") MultipartFile memberPhotoHeadPortraitMultipartFile,
                                         @RequestParam("memberPersonalDataMultipartFile") MultipartFile[] memberPersonalDataMultipartFile, @RequestParam("memberCardFrontMultipartFile") MultipartFile memberCardFrontMultipartFile,
                                         @RequestParam("memberCardReverseSideMultipartFile") MultipartFile memberCardReverseSideMultipartFile, @RequestParam("siginAgreementMultipartFile") MultipartFile[] siginAgreementMultipartFile
            , HttpServletRequest request){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws CodeGodException {
                logger.info("URL:/SignContract/doSaveSgin 请求参数为: 用户签约 信息："+memberSignContractEntity.toString() +"  用户私密信息表数据:"+memberPrivacyEntity.toString() + " 用户基本信息："+memberEntity.toString());
                if(ObjectUtils.isEmpty(memberSignContractEntity.getId())) throw new CodeGodException("id 为空 ",this.getClass());
                memberSignContractService.signSetting(memberSignContractEntity, memberEntity, memberPrivacyEntity, memberPhotoFileMultipartFile, memberPhotoHeadPortraitMultipartFile, memberPersonalDataMultipartFile, memberCardFrontMultipartFile, memberCardReverseSideMultipartFile, siginAgreementMultipartFile, request);
                return "success";
            }
        });
    }

//    @ApiOperation("测试文件上传接口")
//    @PostMapping("/doForm")
//    @ResponseBody
//    public AjaxResult<Object> doForm(String competition,String year,String name,@RequestParam("memberPhotoFileMultipartFile") MultipartFile[] memberPhotoFileMultipartFile,HttpServletRequest request){
//        return AjaxUtils.process(new Func_T<Object>() {
//            @Override
//            public Object invoke() throws Exception {
//                System.out.println(competition);
//                System.out.println(name);
//                System.out.println(year);
//                if(memberPhotoFileMultipartFile!=null&&memberPhotoFileMultipartFile.length>0){
//                    //循环获取file数组中得文件
//                    for(int i = 0;i<memberPhotoFileMultipartFile.length;i++){
//                        MultipartFile file = memberPhotoFileMultipartFile[i];
//                        //保存文件
//                        if(!(ObjectUtils.isEmpty(file)) && file.getSize() != 0){
//                            UploadFile uploadFile = saveFileUtils.saveFile(file);
//                            UploadFile save = uploadFileRepository.save(uploadFile);
////                            System.out.println(save.toString());
//                        }
//                    }
//                }
//                return "success";
//            }
//        });
//    }


}
