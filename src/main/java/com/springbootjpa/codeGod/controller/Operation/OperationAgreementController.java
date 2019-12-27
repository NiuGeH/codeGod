package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationAgreementEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/26 18:47
 */
@Api(value = "/agreement", description = "文案协议设置Controller")
@RequestMapping("/agreement")
@RestController
@Slf4j
public class OperationAgreementController extends OperationBase {

    @PostMapping(value = "/setAgreement", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "设置协议", httpMethod = "POST", notes = "userServiceAgreement 用户服务协议  \n  residentNotice 驻场须知  \n  " +
            "codeGodSignAgreement 码神签约协议  \n  workerOutsourcingAgreement 人力外包协议  \n  projectSignAgreement 项目签约协议  \n  " +
            "parttimeAgreement 兼职协议  \n  fulltimeAgreement 全职协议  \n  recommendReward 推荐奖励")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userServiceAgreement",value = "用户服务协议", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "residentNotice",value = "驻场须知", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "codeGodSignAgreement",value = "码神签约协议", paramType = "formData"),
            @ApiImplicitParam(name = "workerOutsourcingAgreement",value = "人力外包协议", paramType = "formData"),
            @ApiImplicitParam(name = "projectSignAgreement",value = "项目签约协议", paramType = "formData"),
            @ApiImplicitParam(name = "parttimeAgreement",value = "兼职协议", paramType = "formData"),
            @ApiImplicitParam(name = "fulltimeAgreement",value = "全职协议", paramType = "formData"),
            @ApiImplicitParam(name = "recommendReward", value = "推荐奖励", dataType = "int", paramType = "query")
    })
    public AjaxResult<Object> setAgreement(String userServiceAgreement, String residentNotice,
            @RequestParam("codeGodSignAgreement") MultipartFile codeGodSignAgreement,
            @RequestParam("workerOutsourcingAgreement") MultipartFile workerOutsourcingAgreement,
            @RequestParam("projectSignAgreement") MultipartFile projectSignAgreement,
            @RequestParam("parttimeAgreement") MultipartFile parttimeAgreement,
            @RequestParam("fulltimeAgreement") MultipartFile fulltimeAgreement, Integer recommendReward
    ){
        log.info("URL:/agreement/setAgreement 请求参数：用户服务协议:"+ userServiceAgreement +",驻场须知:"+ residentNotice
                +",码神签约协议:"+ codeGodSignAgreement.getName() +",人力外包协议:"+ workerOutsourcingAgreement.getName() +",项目签约协议:"+ projectSignAgreement.getName()
                +",兼职协议:"+ parttimeAgreement.getName() +",全职协议:"+ fulltimeAgreement.getName() +",推荐奖励:"+ recommendReward);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationAgreementEntity agreementEntity = operationAgreementService.setAgreement(userServiceAgreement, residentNotice, codeGodSignAgreement,
                        workerOutsourcingAgreement, projectSignAgreement, parttimeAgreement, fulltimeAgreement, recommendReward);
                return agreementEntity;
            }
        });
    }


    @PostMapping(value = "/findAgreement")
    @ApiOperation(value = "查询全部协议", httpMethod = "POST", notes = "查询全部协议")
    public AjaxResult<Object> findMenuIcon() {
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                return operationAgreementService.findAll();
            }
        });
    }
}
