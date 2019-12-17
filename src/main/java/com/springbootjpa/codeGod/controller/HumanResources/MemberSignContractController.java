package com.springbootjpa.codeGod.controller.HumanResources;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
                        findByColumNameRetrunDirctionaryAryList("member_sign_contract.sigin_results"));
                hashMap.put("siginVerificationCode",baseDataDirctionaryService.
                        findByColumNameRetrunDirctionaryAryList("member_sign_contract.sigin_verification_code"));
                return hashMap;
            }
        });
    }


    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "签约审核分页", httpMethod = "POST", notes = "签约审核分页 validationCode==验证码   siginEnd==签约结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','validationCode':'0','siginEnd':'0'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXifaadNdDJ/aErVrKppFraf5PzZw9o71f8w2zno1JYu8tG4f5V2Kyz069vMa9ESpww=="
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
                    BaseDataDictionaryEntity byDataKeyAndColumReturnDataValue = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(memberSignContractEntity.getSiginResults().toString(), "member_sign_contract.sigin_results");
                    memberSignContractEntity.setSiginResultsData(ObjectUtils.isEmpty(byDataKeyAndColumReturnDataValue) ? "未申请" : byDataKeyAndColumReturnDataValue.getDataValue());

                    BaseDataDictionaryEntity bd2 = baseDataDirctionaryService.findByDataKeyAndColumReturnDataValue(memberSignContractEntity.getSiginVerificationCode().toString(), "member_sign_contract.sigin_verification_code");
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

    @ApiOperation(value = "签约设置保存接口" , notes = "")
    @PostMapping("/doSaveSgin")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberSignContractEntity" , required = true , paramType = "prams")
    })
    public AjaxResult<Object> doSaveSgin(MemberSignContractEntity memberSignContractEntity){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {

                System.out.println(memberSignContractEntity);
                
                return memberSignContractEntity;
            }
        });
    }

    @ApiOperation("测试文件上传接口")
    @PostMapping("/doForm")
    @ResponseBody
    public AjaxResult<Object> doForm(String competition,String year,String name,@RequestParam("files") MultipartFile[] files,HttpServletRequest request){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                System.out.println(competition);
                System.out.println(name);
                System.out.println(year);
                if(files!=null&&files.length>0){
                    //循环获取file数组中得文件
                    for(int i = 0;i<files.length;i++){
                        MultipartFile file = files[i];
                        //保存文件
                        if(!(ObjectUtils.isEmpty(file)) && file.getSize() != 0){
                            saveFileUtils.saveFile(file, request);
//                            UploadFile save = uploadFileRepository.save(uploadFile);
//                            System.out.println(save.toString());
                        }
                    }
                }
                return "null";
            }
        });
    }


}
