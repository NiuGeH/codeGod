package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.common.AjaxResult;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T;
import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/20 14:33
 */
@Api(value = "/news", description = "新闻管理Controller")
@RequestMapping("/news")
@RestController
@Slf4j
public class OperationNewsController extends OperationBase {

    @PostMapping(value = "/addNews", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加新闻", httpMethod = "POST", notes = "title/新闻标题  \n  time/发布时间  \n  views/访问量  \n  content/内容  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'title':'航空母舰','time':'','views':'','content':'','display':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addNews(@RequestBody String json, HttpServletRequest request){
        log.info("URL:/news/addNews 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationNewsEntity newsEntity = operationNewsService.addNews(
                        String.valueOf(hashMap.get("title")),
                        String.valueOf(hashMap.get("time")),
                        ObjectUtils.isEmpty(hashMap.get("views")) ? null : Long.valueOf(hashMap.get("views")),
                        String.valueOf(hashMap.get("content")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")),
                        request);
                return newsEntity;
            }
        });
    }


    @PostMapping(value = "/deleteNews", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除新闻", httpMethod = "POST", notes = "软删除，只是改变了状态  \n  id/新闻id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'3'}", required = true, paramType = "body")
    })
    public AjaxResult<Object> deleteNews(@RequestBody String json){
        log.info("URL:/news/deleteNews 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationNewsEntity newsEntity = operationNewsService.deleteNews(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")));
                return newsEntity;
            }
        });
    }


    @PostMapping(value = "/updateNews", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改新闻", httpMethod = "POST", notes = "id/新闻id  \n  title/新闻新标题  \n  views/访问量  \n  content/内容  \n  display/是否显示，0是，1否")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'','title':'航空母舰','views':'','content':'','display':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateNews(@RequestBody String json){
        log.info("URL:/news/updateNews 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationNewsEntity newsEntity = operationNewsService.updateNews(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("title")),
                        ObjectUtils.isEmpty(hashMap.get("views")) ? null : Long.valueOf(hashMap.get("views")),
                        String.valueOf(hashMap.get("content")),
                        ObjectUtils.isEmpty(hashMap.get("display")) ? null : Integer.valueOf(hashMap.get("display")));
                return newsEntity;
            }
        });
    }
}
