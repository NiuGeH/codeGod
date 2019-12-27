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
import io.swagger.annotations.*;
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
//                for (MemberSignContractEntity memberSignContractEntity : memberSignContractEntities) {
//                    memberSignContractEntity.setMemberEndId(memberService.doStringConvarToList(memberSignContractEntity.getMemberEndId()));
//                    BaseDataDictionaryEntity byDataKeyAndColumReturnDataValue = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(memberSignContractEntity.getSiginResults().toString(), DataBaseFinal.MEMBERSIGNCONTRACT_SIGIN_RESULTS);
//                    memberSignContractEntity.setSiginResultsData(ObjectUtils.isEmpty(byDataKeyAndColumReturnDataValue) ? "未申请" : byDataKeyAndColumReturnDataValue.getDataValue());
//                    BaseDataDictionaryEntity bd2 = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(memberSignContractEntity.getSiginVerificationCode().toString(), DataBaseFinal.MEMBERSIGNCONTRACT_SIGNVERIFICATIONCODE);
//                    memberSignContractEntity.setSiginVerificationCodeData(ObjectUtils.isEmpty(bd2) ? "" : bd2.getDataValue());
//
//                }
                for (MemberSignContractEntity memberSignContractEntity : memberSignContractEntities) {
                    memberSignContractEntity.setMemberEndId(memberService.doStringConvarToList(memberSignContractEntity.getMemberEndId()));
                }
                return memberSignContractEntities;
            }
        });
    }

    @ApiOperation(value = "推荐签约审核删除接口" , notes = "签约结果未申请且验证码已过期 / 签约结果拒绝 删除  \n  ")
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
                HashMap<String,Object> hashMap1 = gson.fromJson(signId, HashMap.class);
                Object signId1 = hashMap1.get("signId");
                HashMap<String , Object> hashMap = new HashMap<>();
                Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(Long.valueOf(String.valueOf(signId1)));
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
            "memberCardFrontMultipartFile >>> 身份证正面(文件)  \n  memberCardReverseSideMultipartFile >>> 身份证反面(文件)  \n  siginAgreementMultipartFile >>> 签约协议(文件 可多个)  \n  " +
            "postman 中复制粘贴:  \n  [{\"key\":\"id\",\"value\":\"2\",\"description\":\"memberSignId(必须)\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberMobile\",\"value\":\"1399999917326\",\"description\":\"手机号\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberPwd\",\"value\":\"9999999\",\"description\":\"登录密码\",\"type\":\"text\",\"enabled\":true},{\"key\":\"nickName\",\"value\":\"昵称2019-12-26\",\"description\":\"昵称\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberRealName\",\"value\":\"张三手下\",\"description\":\"姓名\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberEmail\",\"value\":\"123222222\",\"description\":\"邮箱\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberQq\",\"value\":\"99998888\",\"description\":\"QQ\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberWechat\",\"value\":\"微信号\",\"description\":\"微信\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memebrCity\",\"value\":\"重庆\",\"description\":\"所在城市\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberContactAddress\",\"value\":\"联系地址\",\"description\":\"联系地址\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberIntroduce\",\"value\":\"123222\",\"description\":\"个人账户\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberType\",\"value\":\"1\",\"description\":\"用户类型\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberCardno\",\"value\":\"991826215162742\",\"description\":\"身份证号\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberSigningPost\",\"value\":\"1\",\"description\":\"签约岗位\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberSigningMode\",\"value\":\"1\",\"description\":\"签约方式\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberPhotoFileMultipartFile\",\"description\":\"形象照（文件）\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/123 (2).xlsx\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberPhotoHeadPortraitMultipartFile\",\"description\":\"头像（文件）\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/83385489-b8b4-41cc-aa8a-c762f698ab89.jpg\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberPersonalDataMultipartFile\",\"description\":\"个人资料（文件）\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/timg.jpeg\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberPersonalDataMultipartFile\",\"description\":\"个人资料（文件）\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/timg.jpeg\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberPersonalDataMultipartFile\",\"description\":\"个人资料（文件）\",\"type\":\"file\",\"enabled\":false,\"value\":[\"/Users/ushi/Downloads/timg.jpeg\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberPersonalDataMultipartFile\",\"description\":\"个人资料（文件）\",\"type\":\"file\",\"enabled\":false,\"value\":[\"/Users/ushi/Downloads/4ac90853-6734-4258-8e6e-fe90c18b029f.pdf\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberCardFrontMultipartFile\",\"description\":\"身份证正面（文件）\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/天府支付互联网支付开发指南2.0.1(2).docx\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"memberCardReverseSideMultipartFile\",\"description\":\"身份证反面(文件)\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/83385489-b8b4-41cc-aa8a-c762f698ab89.jpg\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"siginAgreementMultipartFile\",\"description\":\"签约协议(文件 可多个)\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/4ac90853-6734-4258-8e6e-fe90c18b029f.pdf\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"delKey\",\"value\":\"363,364,367,360,353,346,339,332,325,318,311,283\",\"description\":\"\",\"type\":\"text\",\"enabled\":true}]" +
            "" +
            "\n  ===============  继续粘贴============  \n  [{\"key\":\"memberIntroduce\",\"value\":\"个人介绍\",\"description\":\"个人介绍\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberMedal\",\"value\":\"1,2\",\"description\":\"勋章\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberStationing\",\"value\":\"1\",\"description\":\"是否驻场\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberStatus\",\"value\":\"1\",\"description\":\"当前状态\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberDisplay\",\"value\":\"1\",\"description\":\"公开显示\",\"type\":\"text\",\"enabled\":true},{\"key\":\"memberSigningStatus\",\"value\":\"0\",\"description\":\"签约状态\",\"type\":\"text\",\"enabled\":true}]" +
            "\n  =================无情===============")
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
    public AjaxResult<Object> doSaveSgin(@ApiParam MemberSignContractEntity memberSignContractEntity,@ApiParam MemberEntity memberEntity,@ApiParam MemberPrivacyEntity memberPrivacyEntity, String delKey,
                                         @RequestParam("memberPhotoFileMultipartFile") MultipartFile memberPhotoFileMultipartFile, @RequestParam("memberPhotoHeadPortraitMultipartFile") MultipartFile memberPhotoHeadPortraitMultipartFile,
                                         @RequestParam("memberPersonalDataMultipartFile") MultipartFile[] memberPersonalDataMultipartFile, @RequestParam("memberCardFrontMultipartFile") MultipartFile memberCardFrontMultipartFile,
                                         @RequestParam("memberCardReverseSideMultipartFile") MultipartFile memberCardReverseSideMultipartFile, @RequestParam("siginAgreementMultipartFile") MultipartFile[] siginAgreementMultipartFile
            , HttpServletRequest request){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws CodeGodException {
                logger.info("URL:/SignContract/doSaveSign 请求参数为: 用户签约 信息："+memberSignContractEntity.toString() +"  用户私密信息表数据:"+memberPrivacyEntity.toString() + " 用户基本信息："+memberEntity.toString());
                if(ObjectUtils.isEmpty(memberSignContractEntity.getId())) throw new CodeGodException("id 为空 ",this.getClass());
                memberSignContractService.signSetting(memberSignContractEntity, memberEntity, memberPrivacyEntity, memberPhotoFileMultipartFile, memberPhotoHeadPortraitMultipartFile, memberPersonalDataMultipartFile, memberCardFrontMultipartFile, memberCardReverseSideMultipartFile, siginAgreementMultipartFile, delKey, request);
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
