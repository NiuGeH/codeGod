package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationSubtopicEntity;
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
 * @date 2019/12/17 16:56
 */
@Api(value = "/subtopic", description = "子栏目管理Controller")
@RequestMapping("/subtopic")
@RestController
@Slf4j
public class OperationSubtopicController extends OperationBase {

    @PostMapping(value = "/addSubtopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加子栏目", httpMethod = "POST", notes = "subtopicName/子栏目名称  \n  subtopicOrder/子栏目排序  \n  topicId/所属栏目id  \n  content/内容  \n  url/跳转路径  \n  urlState/是否跳转，0不可以，1可以")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'subtopicName':'码神简介','subtopicOrder':'1','topicId':'1','content':'神一样的存在','url':'','urlState':'0'}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addTopic(@RequestBody String json){
        log.info("URL:/subtopic/addSubtopic 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationSubtopicEntity subtopic = operationSubtopicService.addSubtopic(
                        String.valueOf(hashMap.get("subtopicName")),
                        ObjectUtils.isEmpty(hashMap.get("subtopicOrder")) ? null : Long.valueOf(hashMap.get("subtopicOrder")),
                        ObjectUtils.isEmpty(hashMap.get("topicId")) ? null : Long.valueOf(hashMap.get("topicId")),
                        String.valueOf(hashMap.get("content")),
                        String.valueOf(hashMap.get("url")),
                        ObjectUtils.isEmpty(hashMap.get("urlState")) ? null : Integer.valueOf(hashMap.get("urlState")));
                return subtopic;
            }
        });
    }

    @PostMapping(value = "/updateSubtopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改子栏目", httpMethod = "POST", notes = "id/子栏目id  \n  newName/子栏目新名称  \n  subtopicOrder/子栏目排序  \n  topicId/所属栏目id  \n  content/内容  \n  url/跳转路径  \n  urlState/是否跳转，0不可以，1可以")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'1','newName':'码神简介','subtopicOrder':'1','topicId':'1','content':'神一样的存在','url':'','urlState':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateSubtopic(@RequestBody String json){
        log.info("URL:/subtopic/updateSubtopic 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationSubtopicEntity subtopic = operationSubtopicService.updateSubtopic(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("newName")),
                        ObjectUtils.isEmpty(hashMap.get("subtopicOrder")) ? null : Long.valueOf(hashMap.get("subtopicOrder")),
                        ObjectUtils.isEmpty(hashMap.get("topicId")) ? null : Long.valueOf(hashMap.get("topicId")),
                        String.valueOf(hashMap.get("content")),
                        String.valueOf(hashMap.get("url")),
                        ObjectUtils.isEmpty(hashMap.get("urlState")) ? null : Integer.valueOf(hashMap.get("urlState")));
                return subtopic;
            }
        });
    }

    @PostMapping(value = "/findSubtopic", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "子栏目分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数  \n  topicId/所属栏目id  \n  若所属栏目id为空查询全部子栏目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','topicId':'1'}", required = true, paramType = "body")
    })
    public PageResult<OperationSubtopicEntity> doPage(@RequestBody String json) throws CodeGodException {
        log.info("URL:/subtopic/findSubtopic 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        Long topicId = ObjectUtils.isEmpty(hashMap.get("topicId")) ? null : Long.valueOf(hashMap.get("topicId"));
        Sort sort = null;
        if(topicId == null){
            List<String> list = new ArrayList();
            list.add("topicId");
            list.add("subtopicOrder");
            sort = new Sort(Sort.Direction.ASC, list);
        }else {
            sort = new Sort(Sort.Direction.ASC, "subtopicOrder");
        }
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationSubtopicEntity>>() {
            @Override
            public Page<OperationSubtopicEntity> invoke(Pageable page) throws Exception {
                Page<OperationSubtopicEntity> all = operationSubtopicService.findAll(page, topicId);
                return all;
            }
        });
    }
}
