package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @ApiOperation(value = "添加城市", httpMethod = "POST", notes = "cityName/城市名称  \n  cityOrder/城市排序  \n  display/是否显示，0是，1否")
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
                OperationRegionEntity city = operationRegionService.addCity(
                        String.valueOf(hashMap.get("cityName")),
                        ObjectUtils.isEmpty(hashMap.get("cityOrder")) ? null : Long.valueOf(hashMap.get("cityOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return city;
            }
        });
    }


    @PostMapping(value = "/updateCity", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改城市", httpMethod = "POST", notes = "id/城市id  \n  newCityName/城市新名称  \n  cityOrder/城市排序  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'1','newCityName':'重庆','cityOrder':'1','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateCase(@RequestBody String json){
        log.info("URL:/region/updateCity 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationRegionEntity city = operationRegionService.updateCity(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("newCityName")),
                        ObjectUtils.isEmpty(hashMap.get("cityOrder")) ? null : Long.valueOf(hashMap.get("cityOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return city;
            }
        });
    }


    @PostMapping(value = "/findAllCity", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "城市分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationRegionEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/region/findAllCity 请求参数：" + json);
        List<String> list = new ArrayList();
        list.add("cityOrder");
        list.add("id");
        Sort sort = new Sort(Sort.Direction.ASC, list);
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
                return all;
            }
        });
    }

    @PostMapping(value = "/findAllCityForm")
    @ApiOperation(value = "表单中用到的城市" , httpMethod = "POST")
    @ResponseBody
    public AjaxResult<Object> findAllCityForm(){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                return operationRegionRepository.findAllByDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_XS.getIndex());
            }
        });
    }



    @PostMapping(value = "/findCity", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询全部城市名称和id", httpMethod = "POST", notes = "")
    public AjaxResult<Object> findCity() {
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                return operationRegionService.findAll();
            }
        });
    }
}
