package com.springbootjpa.codeGod.controller.HumanResources;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Api(description = "人力外包合同管理")
@Controller
@RequestMapping("/memberContract")
@Slf4j
public class MemberContractController extends MemberBase {


    @PostMapping(value = "/doPage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "人力外包合同分页", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<MemberContractEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/memberContract/doPage 请求参数: " + json);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<MemberContractEntity>>() {
            @Override
            public Page<MemberContractEntity> invoke(Pageable pages) throws Exception {
                return memberContractService.doPage(pages);
            }
        });
    }

    @PostMapping(value = "/returnMapOrById", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "根据Id返回Map和对应的Key/Value(创建/编辑合同 传id为编辑  不传id为创建)", httpMethod = "POST", notes = "contractCloseWayList==>结算方式  \n  contractCourtList==>是否驻场  \n  contractAddress==>地址  \n  " +
            "contractStatusList==>签约状态  \n  contractEnt==>实体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}  \n  传id为编辑  不传id为创建", paramType = "body")
    })
    public AjaxResult<Object> returnMapOrById(@RequestBody String json) {
        log.info("URL:/memberContract/returnMapOrById 请求参数: " + json);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap hashMap = gson.fromJson(json, HashMap.class);
                Object id = hashMap.get("id");
                HashMap<String, Object> map = new HashMap<>();

                map.put("contractCloseWayList", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACT_CONTRACT_CLOSE_WAY));
                map.put("contractAddressList", operationRegionRepository.findAllByDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex()));//OperationRegionEntity
                map.put("contractCourtList", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACTCONTRACT_COURT));
                map.put("contractStatusList", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACT_CONTRACT_STATUS));
                if (!ObjectUtils.isEmpty(id)) {
                    MemberContractEntity memberContractEntity = memberContractEntityRepository.findById(Long.valueOf(String.valueOf(id))).orElseThrow(() -> new CodeGodException("会员不存在", this.getClass()));
                    map.put("contractEnt", memberContractEntity);
                    map.put("contractResource",memberService.findByMemberIdReturnResourceAndKill(memberContractEntity.getId()));
                }

                return map;
            }
        });

    }

    @PostMapping(value = "/doAddContract", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "创建合同", httpMethod = "POST", notes = "memberIdForm ==> 用户Id  \n  employIdForm ==> 需求Id  \n  " +
            "contractUnit ==> 结算单位  \n  contractCycleStart ==> 合同周期  \n  contractCycleEnd ==> 合同周期  \n  contractCloseWay ==> 结算方式" +
            "  \n  contractPactMultipartFile ==> 合同文件  \n  contractAddressId ==> 项目地址  \n  contractStatus==>签约状态  \n  contractCourt ==> 是否驻场  \n  " +
            "postman中粘贴  \n  " +
            "[{\"key\":\"memberIdForm\",\"value\":\"2\",\"description\":\"用户Id\",\"type\":\"text\",\"enabled\":true},{\"key\":\"employIdForm\",\"value\":\"1\",\"description\":\"需求Id\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractUnit\",\"value\":\"钟武彤\",\"description\":\"结算单位\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractCycleStart\",\"value\":\"2019/12/12 12:00:00\",\"description\":\"合同周期\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractCycleEnd\",\"value\":\"2019/12/13 13:00:00\",\"description\":\"合同周期\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractCloseWay\",\"value\":\"1\",\"description\":\"结算方式\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractPactMultipartFile\",\"description\":\"合同文件\",\"type\":\"file\",\"enabled\":true,\"value\":[\"/Users/ushi/Downloads/4ac90853-6734-4258-8e6e-fe90c18b029f.pdf\"],\"warning\":\"This file isn't in your working directory. Teammates you share this request with won't be able to use this file. To make collaboration easier you can setup your working directory in Settings.\"},{\"key\":\"contractAddressId\",\"value\":\"1\",\"description\":\"项目地址\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractStatus\",\"value\":\"0\",\"description\":\"签约状态\",\"type\":\"text\",\"enabled\":true},{\"key\":\"contractCourt\",\"value\":\"1\",\"description\":\"是否驻场\",\"type\":\"text\",\"enabled\":true}]")
    @ResponseBody
    public AjaxResult<Object> doAddContract(@ApiParam MemberContractEntity memberContractEntity, Long employIdForm, Long memberIdForm,Long contractAddressId
            , @RequestParam("contractPactMultipartFile") MultipartFile contractPactMultipartFile) {
        log.info("URL:/memberContract/doAddContract 请求参数: " + memberContractEntity.toString());
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                if(ObjectUtils.isEmpty(employIdForm)) throw new CodeGodException("创建合同 --> 需求Id为空",this.getClass());
                if(ObjectUtils.isEmpty(memberIdForm)) throw new CodeGodException("创建合同 --> 用户Id为空",this.getClass());
                memberContractEntity.setEmployId(memberEmployentityRepository.findById(employIdForm).orElseThrow(() -> new CodeGodException("需求Id不存在",this.getClass())));
                memberContractEntity.setMemberId(memberentityRepository.findById(memberIdForm).orElseThrow(() -> new CodeGodException("合同Id不存在",this.getClass())));
                if(!ObjectUtils.isEmpty(contractAddressId)){
                    memberContractEntity.setContractAddress(operationRegionRepository.findById(contractAddressId).orElseThrow(() -> new CodeGodException("城市Id不存在",this.getClass())));
                }
                return memberContractService.doAddContract(memberContractEntity, contractPactMultipartFile);
            }
        });
    }

    @PostMapping(value = "/doEditContract", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "编辑合同", httpMethod = "POST", notes = "postman 中粘贴  \n  ")
    @ResponseBody
    public AjaxResult<Object> doEditContract(@ApiParam MemberContractEntity memberContractEntity, Long employIdForm, Long memberIdForm,Long contractAddressId
            , @RequestParam("contractPactMultipartFile") MultipartFile contractPactMultipartFile) {
        log.info("URL:/memberContract/doEditContract 请求参数: " + memberContractEntity.toString() + " employIdForm: "+employIdForm + "  contractAddressId: "+contractAddressId);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                if(ObjectUtils.isEmpty(memberContractEntity.getId())){
                    throw new CodeGodException("创建合同 --> memberContract的Id为空 ",this.getClass());
                }
                if(ObjectUtils.isEmpty(employIdForm)) throw new CodeGodException("创建合同 --> 需求Id为空",this.getClass());
                if(ObjectUtils.isEmpty(memberIdForm)) throw new CodeGodException("创建合同 --> 用户Id为空",this.getClass());
                memberContractEntity.setEmployId(memberEmployentityRepository.findById(employIdForm).orElseThrow(() -> new CodeGodException("需求Id不存在",this.getClass())));
                memberContractEntity.setMemberId(memberentityRepository.findById(memberIdForm).orElseThrow(() -> new CodeGodException("合同Id不存在",this.getClass())));
                if(!ObjectUtils.isEmpty(contractAddressId)){
                    memberContractEntity.setContractAddress(operationRegionRepository.findById(contractAddressId).orElseThrow(() -> new CodeGodException("城市Id不存在",this.getClass())));
                }
                return memberContractService.doEditContract(memberContractEntity, contractPactMultipartFile);
            }
        });
    }




}
