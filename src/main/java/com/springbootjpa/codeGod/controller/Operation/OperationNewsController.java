package com.springbootjpa.codeGod.controller.Operation;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.operation.OperationCommentEntity;
import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    @PostMapping(value = "/findNews", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新闻分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}", required = true, paramType = "body")
    })
    public PageResult<OperationNewsEntity> doPageNews(@RequestBody String json) throws CodeGodException {
        log.info("URL:/news/findAllNews 请求参数：" + json);
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationNewsEntity>>() {
            @Override
            public Page<OperationNewsEntity> invoke(Pageable page) throws Exception {
                Page<OperationNewsEntity> all = operationNewsService.findAllNews(page);
                return all;
            }
        });
    }


    @PostMapping(value = "/addComment", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "添加评论", httpMethod = "POST", notes = "newsId/关联的新闻id  \n  content/评论内容")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'newsId':'','content':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> addComment(@RequestBody String json, HttpServletRequest request){
        log.info("URL:/news/addComment 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCommentEntity commentEntity = operationNewsService.addComment(
                        ObjectUtils.isEmpty(hashMap.get("newsId")) ? null : Long.valueOf(hashMap.get("newsId")),
                        String.valueOf(hashMap.get("content")),
                        request);
                return commentEntity;
            }
        });
    }


    @PostMapping(value = "/updateComment", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改评论", httpMethod = "POST", notes = "id/评论id  \n  content/内容")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "json",
                    value = "{'id':'','content':''}",
                    required = true,
                    paramType = "body")
    })
    public AjaxResult<Object> updateComment(@RequestBody String json){
        log.info("URL:/news/updateComment 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCommentEntity commentEntity = operationNewsService.updateComment(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")),
                        String.valueOf(hashMap.get("content")));
                return commentEntity;
            }
        });
    }


    @PostMapping(value = "/deleteComment", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除评论", httpMethod = "POST", notes = "软删除，只是改变了状态  \n  id/评论id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'3'}", required = true, paramType = "body")
    })
    public AjaxResult<Object> deleteComment(@RequestBody String json){
        log.info("URL:/news/deleteComment 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {

            @Override
            public Object invoke() throws Exception {
                OperationCommentEntity commentEntity = operationNewsService.deleteComment(
                        ObjectUtils.isEmpty(hashMap.get("id")) ? null : Long.valueOf(hashMap.get("id")));
                return commentEntity;
            }
        });
    }


    @PostMapping(value = "/findComment", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "评论分页", httpMethod = "POST", notes = "page/当前页  \n  rows/每页记录数  \n  newsId/新闻id  \n  若新闻id查询全部评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','newsId':'1'}", required = true, paramType = "body")
    })
    public PageResult<OperationCommentEntity> doPageComment(@RequestBody String json) throws CodeGodException {
        log.info("URL:/news/findComment 请求参数：" + json);
        HashMap<String,String> hashMap = gson.fromJson(json, HashMap.class);
        Long newsId = ObjectUtils.isEmpty(hashMap.get("newsId")) ? null : Long.valueOf(hashMap.get("newsId"));
        Sort sort = null;
        if(newsId == null){
            List<String> list = new ArrayList();
            list.add("newsId");
            list.add("id");
            sort = new Sort(Sort.Direction.ASC, list);
        }else {
            sort = new Sort(Sort.Direction.ASC, "id");
        }
        PageRequestParam pages = null;
        try{
            pages = gson.fromJson(json, PageRequestParam.class);
        }catch (Exception e){
            throw new CodeGodException("Gson格式转换错误，请求参数为："+json,this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<OperationCommentEntity>>() {
            @Override
            public Page<OperationCommentEntity> invoke(Pageable page) throws Exception {
                Page<OperationCommentEntity> all = operationNewsService.findAllComment(page, newsId);
                return all;
            }
        });
    }
}
