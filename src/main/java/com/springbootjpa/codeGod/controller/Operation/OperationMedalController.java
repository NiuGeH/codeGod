package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

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
        log.info("URL:/medal/updateMedal 请求参数：修改的勋章id>>>" + id.toString() + "，勋章新名称>>>" + newName);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationMedalEntity medalEntity = operationMedalService.updateMedal(id, newName, medalPhotoFile);
                return medalEntity;
            }
        });
    }


    @PostMapping(value = "/deleteMedal", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除勋章", httpMethod = "POST", notes = "软删除，只是改变了状态  \n  id/勋章id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'3'}", required = true, paramType = "body")
    })
    public AjaxResult<Object> deleteMedal(@RequestBody String json){
        log.info("URL:/medal/deleteMedal 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationMedalEntity medalEntity = operationMedalService.deleteMedal(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")));
                return medalEntity;
            }
        });
    }


    @PostMapping(value = "/findAllMedal", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "勋章分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationMedalEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/medal/findAllMedal 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationMedalEntity>>() {
            @Override
            public Page<OperationMedalEntity> invoke(Pageable page) throws Exception {
                Page<OperationMedalEntity> all = operationMedalService.findAll(page);
                for (OperationMedalEntity operationMedalEntity:all) {
                    log.info("勋章分页：" + operationMedalEntity.toString());
                }
                return all;
            }
        });
    }
}
