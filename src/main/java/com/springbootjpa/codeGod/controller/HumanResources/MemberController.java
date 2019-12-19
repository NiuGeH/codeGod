package com.springbootjpa.codeGod.controller.HumanResources;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
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
import java.util.HashMap;
import java.util.Optional;

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
                    value = "{'page':'1','rows':'5','memberDisplay':'0','memberStationing':'0','memberSigningPost':'0','memberType':'0' ,'keyWord':''}",required = true, paramType = "body")
    })
    public PageResult<MemberEntity> doPage(@RequestBody String json) {
        log.info("URL:/memberController/doPage 参数："+json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = super.gson.fromJson(json, PageRequestParam.class);
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEntity>>() {
            @Override
            public Page<MemberEntity> invoke(Pageable var1) throws Exception {
                HashMap<String , String > hashMap = gson.fromJson(json,HashMap.class);
                return memberService.findAll(var1,hashMap.get("memberDisplay"),hashMap.get("memberStationing"),hashMap.get("memberSigningPost"),hashMap.get("memberType"),hashMap.get("keyWord"));
            }
        });
    }

    @PostMapping( value = "/editMemberEcho", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "点击资料编辑回显", httpMethod = "POST" , notes =
            "回显中取sigEnt 中的memberEndId对象中的数据 具体对象与/doSaveSign接口对应  \n  >>>>>>>>>>>>>>>>>>>>>>>>>  \n" +
                    "  signEnt  签约实体类  \n  memberType  用户类型  \n  memberSigningPost  签约岗位  \n  memberSigningMode  签约方式" +
                    "  \n  memberMedal  勋章  \n  memberStationing  是否驻场  \n  memberStatus  当前状态  \n  memberSigningStatus  签约状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json" , value = "{'id':'2'}")
    })
    public AjaxResult<HashMap<String , Object >> editMemberEcho(@RequestBody String json){
        log.info("URL:/memberController/editMemberEcho 参数列表: "+json);
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                HashMap<String, Object> hashMap = memberService.allDataFromDictionary();
                HashMap<String , String > params = gson.fromJson(json, HashMap.class);
                Optional<MemberEntity> optional = memberentityRepository.findById(Long.valueOf(params.get("id")));
                if(ObjectUtils.isEmpty(optional)){
                    throw new CodeGodException("id 为空 /memberController/editMemberEcho",this.getClass());
                }else {
                    MemberEntity memberEntity = optional.get();
                    hashMap.put("MemberEnt",memberService.doStringConvarToList(memberEntity));
                    return hashMap;
                }
            }
        });
    }


    @PostMapping(value = "/basedSearch", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "分页搜索关键词数据", httpMethod = "POST",  notes = "memberDisplay==>是否公开  \n  memberStationing==>是否驻场  \n  所有城市待确定！！！  \n  " +
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

                //所有角色
                hashMap.put("memberSigningPost",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGPOST));
                //所有用户
                hashMap.put("memberType",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERTYPE));

                return hashMap;
            }
        });
    }

    @PostMapping(value = "/doEditMember" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "资料编辑(比/SignContract/doSaveSgin接口多两个参数 )" , httpMethod = "POST" , notes = "多的两个参数：memberCompany >>> 所属公司  \n  memberTeam >>> 所属团队  \n  >>>>>>>>>>>>>>  \n  id >>> memberId(必须) \n  memberMobile >>> 手机号  \n  memberPwd >>> 登录密码  \n  nickName >>> 昵称  \n" +
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
    public AjaxResult<Object> doEditMember (MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity,
                                            @RequestParam("memberPhotoFileMultipartFile") MultipartFile memberPhotoFileMultipartFile, @RequestParam("memberPhotoHeadPortraitMultipartFile") MultipartFile memberPhotoHeadPortraitMultipartFile,
                                            @RequestParam("memberPersonalDataMultipartFile") MultipartFile[] memberPersonalDataMultipartFile, @RequestParam("memberCardFrontMultipartFile") MultipartFile memberCardFrontMultipartFile,
                                            @RequestParam("memberCardReverseSideMultipartFile") MultipartFile memberCardReverseSideMultipartFile, @RequestParam("siginAgreementMultipartFile") MultipartFile[] siginAgreementMultipartFile
            , HttpServletRequest request){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                return null;
            }
        });
    }


}
