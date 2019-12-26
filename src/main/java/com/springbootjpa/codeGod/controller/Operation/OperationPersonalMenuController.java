package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
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
 * @date 2019/12/25 14:11
 */
@Api(value = "/personalMenu", description = "菜单图标配置Controller")
@RequestMapping("/personalMenu")
@RestController
@Slf4j
public class OperationPersonalMenuController extends OperationBase {

    @PostMapping(value = "/setMenuIcon", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "修改个人中心菜单图标", httpMethod = "POST", notes = "myInvitation 我的邀请图标  \n  demandList 需求列表图标  \n  " +
            "recommendProject 推荐项目图标  \n  publishOrder 发起工单图标  \n  myAgent 我的经纪人图标  \n  publishDemand 发布需求图标  \n  operationDemand 运维需求图标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myInvitation",value = "我的邀请图标", paramType = "formData"),
            @ApiImplicitParam(name = "demandList",value = "需求列表图标", paramType = "formData"),
            @ApiImplicitParam(name = "recommendProject",value = "推荐项目图标", paramType = "formData"),
            @ApiImplicitParam(name = "publishOrder",value = "发起工单图标", paramType = "formData"),
            @ApiImplicitParam(name = "myAgent",value = "我的经纪人图标", paramType = "formData"),
            @ApiImplicitParam(name = "publishDemand",value = "发布需求图标", paramType = "formData"),
            @ApiImplicitParam(name = "operationDemand",value = "运维需求图标", paramType = "formData")

    })
    public AjaxResult<Object> setMenuIcon(
            @RequestParam("myInvitation") MultipartFile myInvitation,
            @RequestParam("demandList") MultipartFile demandList,
            @RequestParam("recommendProject") MultipartFile recommendProject,
            @RequestParam("publishOrder") MultipartFile publishOrder,
            @RequestParam("myAgent") MultipartFile myAgent,
            @RequestParam("publishDemand") MultipartFile publishDemand,
            @RequestParam("operationDemand") MultipartFile operationDemand
    ){
        log.info("URL:/personalMenu/setMenuIcon 请求参数：我的邀请图标:"+ myInvitation.getName() +",需求列表图标:"+ demandList.getName()
                +",推荐项目图标:"+ recommendProject.getName() +",发起工单图标:"+ publishOrder.getName() +",我的经纪人图标:"+ myAgent.getName()
                +",发布需求图标:"+ publishDemand.getName() +",运维需求图标:"+ operationDemand.getName());
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationPersonalMenuEntity menuEntity = operationPersonalMenuService.setMenuIcon(myInvitation, demandList, recommendProject,
                        publishOrder, myAgent, publishDemand, operationDemand);
                return menuEntity;
            }
        });
    }


    @PostMapping(value = "/findMenuIcon")
    @ApiOperation(value = "查询个人中心菜单图标", httpMethod = "POST", notes = "查询个人中心菜单图标")
    public AjaxResult<Object> findMenuIcon() {
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                return operationPersonalMenuService.findAll();
            }
        });
    }

}
