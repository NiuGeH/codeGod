package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/24 10:22
 */
@Api(value = "/resource", description = "资源类型管理Controller")
@RequestMapping("/resource")
@RestController
@Slf4j
public class OperationResourceController extends OperationBase {

    @PostMapping(value = "/addResource", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "添加资源类型", httpMethod = "POST", notes = "name/资源类型名称  \n  resourcePhotoFile/上传的图标文件  \n  " +
            "amount/人数  \n  order/资源排序  \n  skillMap/关联技术栈Map<Long,String>{id:name}  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "资源类型名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "resourcePhotoFile", value = "上传的图标文件", paramType = "formData"),
            @ApiImplicitParam(name = "amount", value = "人数", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "资源排序", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "skillMap", value = "关联技术栈Map<Long,String>{id:name}", dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "display", value = "是否显示，0是，1否", dataType = "int", paramType = "query")
    })
    public AjaxResult<Object> addResource(String name, Long amount, Long order, Map<Long,String> skillMap, Integer display,
                                          @RequestParam("resourcePhotoFile") MultipartFile resourcePhotoFile){
        log.info("URL:/resource/addResource 请求参数：资源类型名称:"+name+",人数:"+amount+",资源排序："+order+",关联技术栈map:"+skillMap+",是否显示:"+display);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationResourceEntity resourceEntity = operationResourceService.addResource(name, resourcePhotoFile, amount, order, skillMap, display);
                return resourceEntity;
            }
        });
    }


    @PostMapping(value = "/updateResource", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "修改资源类型", httpMethod = "POST", notes = "id/资源类型id  \n  newName/资源类型新名称  \n  resourcePhotoFile/上传的图标文件  \n  " +
            "amount/人数  \n  order/资源排序  \n  skillMap/关联技术栈Map<Long,String>{id:name}  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源类型id", dataType = "long", paramType = "query", required = true),
            @ApiImplicitParam(name = "newName", value = "资源类型新名称", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "resourcePhotoFile", value = "上传的图标文件", paramType = "formData"),
            @ApiImplicitParam(name = "amount", value = "人数", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "资源排序", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "skillMap", value = "关联技术栈Map<Long,String>{id:name}", dataType = "Map", paramType = "query"),
            @ApiImplicitParam(name = "display", value = "是否显示，0是，1否", dataType = "int", paramType = "query")
    })
    public AjaxResult<Object> updateResource(Long id, String newName, Long amount, Long order, Map<Long,String> skillMap, Integer display,
                                          @RequestParam("resourcePhotoFile") MultipartFile resourcePhotoFile){
        log.info("URL:/resource/updateResource 请求参数：资源类型id："+id+",资源类型名称:"+newName+",人数:"+amount+",资源排序："+order+",关联技术栈map:"+skillMap+",是否显示:"+display);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationResourceEntity resourceEntity = operationResourceService.updateResource(id, newName, resourcePhotoFile, amount, order, skillMap, display);
                return resourceEntity;
            }
        });
    }


    @PostMapping(value = "/findAllResource", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "资源类型分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationResourceEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/resource/findAllResource 请求参数：" + json);
        List<String> list = new ArrayList();
        list.add("resourceOrder");
        list.add("id");
        Sort sort = new Sort(Sort.Direction.ASC, list);
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationResourceEntity>>() {
            @Override
            public Page<OperationResourceEntity> invoke(Pageable page) throws Exception {
                Page<OperationResourceEntity> all = operationResourceService.findAll(page);
                return all;
            }
        });
    }
}
