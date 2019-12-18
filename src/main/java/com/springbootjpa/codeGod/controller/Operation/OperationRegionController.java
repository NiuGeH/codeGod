package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 12:12
 */
@Api(value = "/region", description = "地域管理Controller")
@RequestMapping("/region")
@RestController
@Slf4j
public class OperationRegionController extends OperationBase{

    @PostMapping(value = "/addCity", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加城市", httpMethod = "POST", notes = "cityName/城市名称 cityOrder/城市排序 display/是否显示，0显示，1不显示")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'cityName':'重庆','cityOrder':'1','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addCity(@RequestBody String json){
        log.info("URL:/region/addCity 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationRegionEntity city = operationRegionService.addCity(String.valueOf(hashMap.get("cityName")), String.valueOf(hashMap.get("cityOrder")), Integer.valueOf(hashMap.get("display")));
                return city;
            }
        });
    }


    @PostMapping(value = "/updateCity", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改城市", httpMethod = "POST", notes = "oldCityName/城市原名称 newCityName/城市新名称 cityOrder/城市排序 display/是否显示，0显示，1不显示")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'oldCityName':'重庆','newCityName':'重庆','cityOrder':'1','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateCase(@RequestBody String json){
        log.info("URL:/region/updateCity 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationRegionEntity city = operationRegionService.updateCity(String.valueOf(hashMap.get("oldCityName")), String.valueOf(hashMap.get("newCityName")), String.valueOf(hashMap.get("cityOrder")), Integer.valueOf(hashMap.get("display")));
                return city;
            }
        });
    }


    @PostMapping(value = "/findAllCity", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "城市分页", httpMethod = "POST", notes = "page/当前页 rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationRegionEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/region/findAllCity 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "cityOrder");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationRegionEntity>>() {
            @Override
            public Page<OperationRegionEntity> invoke(Pageable page) throws Exception {
                Page<OperationRegionEntity> all = operationRegionService.findAll(page);
                for (OperationRegionEntity operationRegionEntity:all) {
                    log.info("城市分页：" + operationRegionEntity.toString());
                }
                return all;
            }
        });
    }
}