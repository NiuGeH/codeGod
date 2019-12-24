package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
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
}
