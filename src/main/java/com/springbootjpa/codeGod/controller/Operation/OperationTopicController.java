package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationTopicEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 14:09
 */
@Api(value = "/topic", description = "栏目管理Controller")
@RequestMapping("/topic")
@RestController
@Slf4j
public class OperationTopicController extends OperationBase {

    @PostMapping(value = "/addTopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加栏目", httpMethod = "POST", notes = "topicName/栏目名称  \n  topicOrder/栏目排序  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'topicName':'关于我们','topicOrder':'1','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addTopic(@RequestBody String json){
        log.info("URL:/topic/addTopic 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationTopicEntity topic = operationTopicService.addTopic(
                        String.valueOf(hashMap.get("topicName")),
                        ObjectUtils.isEmpty(hashMap.get("topicOrder")) ? null : Long.valueOf(hashMap.get("topicOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return topic;
            }
        });
    }


    @PostMapping(value = "/updateTopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改栏目", httpMethod = "POST", notes = "id/栏目id  \n  newTopicName/栏目新名称  \n  topicOrder/栏目排序  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'1','newTopicName':'关于我们','topicOrder':'1','display':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateTopic(@RequestBody String json){
        log.info("URL:/topic/updateTopic 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationTopicEntity topic = operationTopicService.updateTopic(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("newTopicName")),
                        ObjectUtils.isEmpty(hashMap.get("topicOrder")) ? null : Long.valueOf(hashMap.get("topicOrder")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return topic;
            }
        });
    }


    @PostMapping(value = "/findAllTopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "栏目分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationTopicEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/topic/findAllTopic 请求参数：" + json);
        List<String> list = new ArrayList();
        list.add("topicOrder");
        list.add("id");
        Sort sort = new Sort(Sort.Direction.ASC, list);
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationTopicEntity>>() {
            @Override
            public Page<OperationTopicEntity> invoke(Pageable page) throws Exception {
                Page<OperationTopicEntity> all = operationTopicService.findAll(page);
                return all;
            }
        });
    }


    @PostMapping(value = "/findTopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询全部栏目名称和id", httpMethod = "POST", notes = "供添加子栏目时调用")
    public AjaxResult<Object> findTopic() {
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                return operationTopicService.findAll();
            }
        });
    }
}
