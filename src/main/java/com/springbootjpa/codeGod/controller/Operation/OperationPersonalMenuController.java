package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 14:11
 */
@Api(value = "/personalMenu", description = "菜单图标配置Controller")
@RequestMapping("/personalMenu")
@RestController
@Slf4j
public class OperationPersonalMenuController extends OperationBase {

    @PostMapping(value = "/addPersonalMenu", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "添加个人中心菜单", httpMethod = "POST", notes = "menuName 菜单名称  \n  menuPhotoFile 上传的菜单图标文件  \n  display 是否显示：0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuName", value = "菜单名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "menuPhotoFile",value = "上传的菜单图标文件", paramType = "formData"),
            @ApiImplicitParam(name = "display", value = "是否显示", dataType = "int", paramType = "query")
    })
    public AjaxResult<Object> addPersonalMenu(String menuName, Integer display, @RequestParam("menuPhotoFile") MultipartFile menuPhotoFile){
        log.info("URL:/personalMenu/addPersonalMenu 请求参数：菜单名称:"+ menuName +",是否显示:"+ display +",上传的菜单图标文件:"+ menuPhotoFile);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationPersonalMenuEntity menuEntity = operationPersonalMenuService.addPersonalMenu(menuName, menuPhotoFile, display);
                return menuEntity;
            }
        });
    }


    @PostMapping(value = "/updateMenu", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "修改个人中心菜单", httpMethod = "POST", notes = "id 被修改的菜单id  \n  menuName 菜单名称  \n  menuPhotoFile 上传的菜单图标文件  \n  display 是否显示：0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "被修改的菜单id", dataType = "long", paramType = "query", required = true),
            @ApiImplicitParam(name = "menuName", value = "菜单名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "menuPhotoFile",value = "上传的菜单图标文件", paramType = "formData"),
            @ApiImplicitParam(name = "display", value = "是否显示", dataType = "int", paramType = "query")
    })
    public AjaxResult<Object> updateMenu(Long id, String menuName, Integer display, @RequestParam("menuPhotoFile") MultipartFile menuPhotoFile){
        log.info("URL:/personalMenu/updateMenu 请求参数：被修改的菜单id:"+ id +",菜单名称:"+ menuName +",是否显示:"+ display +",上传的菜单图标文件:"+ menuPhotoFile);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationPersonalMenuEntity menuEntity = operationPersonalMenuService.updateMenu(id, menuName, menuPhotoFile, display);
                return menuEntity;
            }
        });
    }


    @PostMapping(value = "/findAllMenu", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "个人中心菜单分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationPersonalMenuEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/personalMenu/findAllMenu 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationPersonalMenuEntity>>() {
            @Override
            public Page<OperationPersonalMenuEntity> invoke(Pageable page) throws Exception {
                Page<OperationPersonalMenuEntity> all = operationPersonalMenuService.findAll(page);
                return all;
            }
        });
    }

}
