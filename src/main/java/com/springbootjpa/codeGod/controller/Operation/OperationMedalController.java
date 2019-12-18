package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api("后台勋章管理 接口")
@Controller
@RequestMapping("/operationController")
public class OperationMedalController extends OperationBase {

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "查询所有未删除的勋章数据" , httpMethod = "POST" , notes = "查询所有未删除的勋章数据")
    public AjaxResult<List<OperationMedalEntity>> findAllByState(){
        return AjaxUtils.process(new Func_T<List<OperationMedalEntity>>() {
            @Override
            public List<OperationMedalEntity> invoke() throws Exception {
                return operationMedalRepository.findAllByState(0);
            }
        });
    }

}
