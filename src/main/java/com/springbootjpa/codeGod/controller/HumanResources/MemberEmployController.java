package com.springbootjpa.codeGod.controller.HumanResources;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberCaseEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
@Api(description = "雇佣需求")
@RequestMapping("/memberEmploy")
@Slf4j
public class MemberEmployController extends MemberBase {

    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "雇佣需求分页", httpMethod = "POST", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}",required = true, paramType = "body")
    })
    public PageResult<MemberEmployEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/memberEmploy/doPage 请求参数: "+json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberEmployEntity>>() {
            @Override
            public Page<MemberEmployEntity> invoke(Pageable pages) throws Exception {

                return memberEmployService.doPage(pages);
            }
        });
    }

    @PostMapping(value = "/doRefused", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "拒绝", httpMethod = "POST", notes = "id,remark 必填")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json",value = "{'id':'1','employReason':'拒绝原因'}")
    })
    public AjaxResult<Object> doRefused(@RequestBody String json){
        log.info("URL:/memberEmploy/doRefused 请求参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                String employReason = (String)hashMap.get("employReason");
                String id = (String)hashMap.get("id");
                if(ObjectUtils.isEmpty(id)){
                    throw new CodeGodException("需求Id为空",this.getClass());
                } else if(ObjectUtils.isEmpty(employReason)){
                    throw new CodeGodException("拒绝原因为空",this.getClass());
                }else{
                    return memberEmployService.doRefused(Long.valueOf(id),employReason);
                }
            }
        });
    }

    @PostMapping(value = "/doSignEmploy" , headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "雇佣需求签约",httpMethod = "POST", notes = "id 为需求Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "需求Id",paramType = "form"),
            @ApiImplicitParam(name = "contractUnit" ,value = "结算单位" , paramType = "form"),
            @ApiImplicitParam(name = "contractCycleStart",value = "合同周期 start (yyyy-MM-dd)",paramType = "form"),
            @ApiImplicitParam(name = "contractCycleEnd",value = "合同周期 End(yyyy-MM-dd)",paramType = "form"),
            @ApiImplicitParam(name = "contractCloseWay",value = "结算方式 (HashMap中的对应的 contractCloseWayList)",paramType = "form"),
            @ApiImplicitParam(name = "contractAddressId",value = "项目地址 (HashMap中的对应的contractAddressList )",paramType = "form"),
            @ApiImplicitParam(name = "contractCourt",value = "是否驻场 (HashMap中的对应的 contractCourtList)",paramType = "form"),
            @ApiImplicitParam(name = "contractNumber",value = "结算人数",paramType = "form")

    })
    public AjaxResult<Object> doSignEmploy(@ApiParam MemberContractEntity memberContractEntity,String contractAddressId , @RequestParam("contractPactMultipartFile") MultipartFile contractPactMultipartFile){
        log.info("URL:/memberEmploy/doSignEmploy 请求参数: "+memberContractEntity.toString() +" contractAddressId :"+ contractAddressId);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                return memberEmployService.doSignEmploy(memberContractEntity,contractAddressId, contractPactMultipartFile);
            }
        });
    }

    @PostMapping(value = "/findByEmployIdReturnMap" , produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "根据Id返回Map 签约实体 和 对应的Key/Value",httpMethod = "POST",notes = "contractCloseWayList==>结算方式  \n  contractCourtList==>是否驻场  \n  contractAddress==>地址  \n 需求实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}",required = true, paramType = "body")
    })
    public AjaxResult<Object> findByEmployIdReturnMap(@RequestBody String json){
        log.info("URL:/memberEmploy/findByEmployIdReturnMap 请求参数: "+json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                String id = (String)hashMap.get("id");
                if(ObjectUtils.isEmpty(id)){
                    throw new CodeGodException("对应的签约Id为空",this.getClass());
                }else{
                    MemberEmployEntity memberEmployEntity = memberEmployentityRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CodeGodException("签约表中id不存在", this.getClass()));
                    HashMap<String , Object> map = new HashMap<>();
                    map.put("employEnt",memberEmployEntity);
                    map.put("contractCloseWayList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACT_CONTRACT_CLOSE_WAY));
                    map.put("contractAddressList",operationRegionRepository.findAllByDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex()));//OperationRegionEntity
                    map.put("contractCourtList",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACTCONTRACT_COURT));
                    return map;
                }
            }
        });

    }

}
