package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmRecruitmentEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Api(description = "项目管理Controller")
@RequestMapping("/PmProjectController")
@Controller
public class PmProjectController extends PmDemandBase{

    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);



    @PostMapping(value = "/findAllPmPorject", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "项目管理分页", httpMethod = "POST", notes = "项目分页全查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public PageResult<PmProjectEntity> doPage(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmProjectController/findAllPmDemand 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null; try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmProjectEntity>>() {
            @Override
            public Page<PmProjectEntity> invoke(Pageable pages) throws Exception {
                Page<PmProjectEntity> all = pmProjectService.doPage(pages,Integer.valueOf(map.get("projectStatus")));
                System.out.println(all.getSize());
                System.out.println(all.getTotalElements() + "" + all.getTotalPages());
                for (PmProjectEntity pmProjectEntity : all) {

                }
                return all;
            }
        });
    }


    @PostMapping(value = "/findOneProjectById", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "项目数据回显", httpMethod = "POST", notes = "项目数据回显接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> findOneProjectById(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String,String> map = new HashMap<String,String>();
                return pmProjectService.findOne(Long.valueOf(map.get("id")));
            }
        });
    }




    @PostMapping(value = "/updateProjectById", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "编辑项目数据", httpMethod = "POST", notes = "项目数据编辑接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateProjectById(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                PmProjectEntity pmProjectEntity = gson.fromJson(json, PmProjectEntity.class);
                boolean flag = pmProjectService.saveProject(pmProjectEntity);
                if(flag){
                    return "success";
                }
                return "error";
            }
        });
    }



    @PostMapping(value = "/findAllRecruitment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "招聘信息数据", httpMethod = "POST", notes = "招聘信息数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public PageResult<PmRecruitmentEntity> doPageRecruitment(@RequestBody String json)throws CodeGodException{
        logger.info("url:/PmProjectController/findAllRecruitment 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null; try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmRecruitmentEntity>>() {
            @Override
            public Page<PmRecruitmentEntity> invoke(Pageable var1) throws Exception {
                return null;
            }
        });
    }


    @PostMapping(value = "/addRecruitment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加招聘信息", httpMethod = "POST", notes = "添加招聘信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public  AjaxResult<HashMap<String,String>> addRecruitment(@RequestBody String json){
        return AjaxUtils.process(new Func_T<HashMap<String, String>>() {
            @Override
            public HashMap<String, String> invoke() throws Exception {
                logger.info(""+json);
                return null;
            }
        });
    }

    @PostMapping(value = "/updateRecruitment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "修改招聘信息", httpMethod = "POST", notes = "修改招聘信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateRecruitment(@RequestBody String json) {
        return  AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                return null;
            }
        });
    }



























}
