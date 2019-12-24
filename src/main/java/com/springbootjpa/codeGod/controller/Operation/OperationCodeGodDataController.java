package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationCodeGodDataEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/24 17:24
 */
@Api(value = "/codeGodData", description = "码神资料Controller")
@RequestMapping("/codeGodData")
@RestController
@Slf4j
public class OperationCodeGodDataController extends OperationBase {

    @PostMapping(value = "/setCodeGodData", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "设置码神资料", httpMethod = "POST", notes = "amountProject/已完成项目数量  \n  grossAccount/累计合同金额  \n  amountSignContract/签约码神数量  \n  amountTeam/全国服务团队数量")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'amountProject':'1','grossAccount':'1','amountSignContract':'1','amountTeam':'1'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> setCodeGodData(@RequestBody String json){
        log.info("URL:/codeGodData/setCodeGodData 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCodeGodDataEntity codeGodDataEntity = operationCodeGodDataService.setCodeGodData(
                        ObjectUtils.isEmpty(hashMap.get("amountProject")) ? null : Long.valueOf(hashMap.get("amountProject")),
                        ObjectUtils.isEmpty(hashMap.get("grossAccount")) ? null : Long.valueOf(hashMap.get("grossAccount")),
                        ObjectUtils.isEmpty(hashMap.get("amountSignContract")) ? null : Long.valueOf(hashMap.get("amountSignContract")),
                        ObjectUtils.isEmpty(hashMap.get("amountTeam")) ? null : Long.valueOf(hashMap.get("amountTeam")));
                return codeGodDataEntity;
            }
        });
    }


    @PostMapping(value = "/findCodeGodData")
    @ApiOperation(value = "查询码神资料", httpMethod = "POST", notes = "查询码神资料")
    public AjaxResult<Object> findCodeGodData() {
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                return operationCodeGodDataService.findAll();
            }
        });
    }
}
