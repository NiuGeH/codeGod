package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
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

@Api(value = "/medal", description = "勋章管理Controller")
@RequestMapping("/medal")
@RestController
@Slf4j
public class OperationMedalController extends OperationBase {

    @PostMapping(value = "/addMedal", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "添加勋章", httpMethod = "POST", notes = "medalName/勋章名称  \n  medalPhotoFile/上传的勋章图标文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "medalName", value = "勋章名称", required = true),
            @ApiImplicitParam(name = "medalPhotoFile", paramType = "formData",value = "上传的勋章图标文件")
    })
    public AjaxResult<Object> addMedal(String medalName, @RequestParam("medalPhotoFile") MultipartFile medalPhotoFile){
        log.info("URL:/medal/addMedal 请求参数：" + medalName);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationMedalEntity medalEntity = operationMedalService.addMedal(medalName, medalPhotoFile);
                return medalEntity;
            }
        });
    }

    @PostMapping(value = "/updateMedal", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "修改勋章", httpMethod = "POST", notes = "id/勋章id  \n  newName/勋章新名称  \n  medalPhotoFile/上传的勋章图标文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "勋章id", required = true),
            @ApiImplicitParam(name = "newName", value = "勋章新名称", required = true),
            @ApiImplicitParam(name = "medalPhotoFile", paramType = "formData",value = "上传的勋章图标文件")
    })
    public AjaxResult<Object> updateMedal(Long id, String newName, @RequestParam("medalPhotoFile") MultipartFile medalPhotoFile){
        log.info("URL:/medal/addMedal 请求参数：修改的勋章id>>>" + id.toString() + "，勋章新名称>>>" + newName);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationMedalEntity medalEntity = operationMedalService.updateMedal(id, newName, medalPhotoFile);
                return medalEntity;
            }
        });
    }

}
