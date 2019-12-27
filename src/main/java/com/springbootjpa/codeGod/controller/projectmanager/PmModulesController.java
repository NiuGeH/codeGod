package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.PmIterationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
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

@Api(description = "模块信息Controller")
@RequestMapping("/PmModulesController")
@Controller
public class PmModulesController extends PmDemandBase{



    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);


    @PostMapping(value = "/findAllModules", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "模块信息数据", httpMethod = "POST", notes = "模块信息数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmModulesEntity> doPageModules(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmModulesController/findAllModules 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmModulesEntity>>() {
            @Override
            public Page<PmModulesEntity> invoke(Pageable var1) throws Exception {
                PmModulesEntity pmModulesEntity = new PmModulesEntity();
                pmModulesEntity.setProjectId(Long.valueOf(map.get("projectId")));
                Page<PmModulesEntity> pmModulesEntities = pmModulesService.doPage(var1, pmModulesEntity);
                return pmModulesEntities;
            }
        });
    }

    @PostMapping(value = "/saveModules", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "保存模块信息", httpMethod = "POST", notes = "保存模块信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> saveModule(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmModulesController/saveModules 请求参数" + json);
                PmModulesEntity pmModulesEntity = gson.fromJson(json, PmModulesEntity.class);
                boolean flag = pmModulesService.saveModelus(pmModulesEntity);
                if (flag) {
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });
    }

    @PostMapping(value = "/addModules", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加模块信息", httpMethod = "POST", notes = "添加模块信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<HashMap<String, Object>> addModules(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                logger.info("url:/PmModulesController/addModules 请求参数" + json);
                HashMap<String, Object> map = new HashMap<>();
                map.put("moduleType ", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_MODULESMODULE_TYPE));
                map.put("technologyStack", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_MODULESTECHNOLOGY_STACK));
                map.put("duty", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY));
                return map;
            }
        });
    }

    @PostMapping(value = "/updateModules", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "修改模块信息", httpMethod = "POST", notes = "修改模块信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateModules(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String, String> map = gson.fromJson(json, HashMap.class);
                return pmModulesService.findOne(Long.valueOf(map.get("id")));
            }
        });
    }

    @PostMapping(value = "/updateModulesSchedule", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "更新模块进度信息", httpMethod = "POST", notes = "更新模块进度接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'模块ID','是否完成','当前进度','开发描述','外显设置'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateSchedule(@RequestBody String json) {
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                boolean b = pmModulesService.updateModulesSchedule(map);
                if (b) {
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });
    }


    @PostMapping(value = "/findAllIteration", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "模块迭代信息", httpMethod = "POST", notes = "模块迭代信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'项目ID':'1','模块ID':}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmIterationEntity> doPageIteration(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmModulesController/findAllIteration 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmIterationEntity>>() {
            @Override
            public Page<PmIterationEntity> invoke(Pageable var1) throws Exception {
                return pmIterationService.doPage(var1, Long.valueOf(map.get("projectId")));
            }
        });
    }



    @PostMapping(value = "/addIteration", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加模块迭代信息", httpMethod = "POST", notes = "添加模块迭代信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'项目ID':'1','模块ID':}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> addIteration(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                try {
                    HashMap<String,String> map = gson.fromJson(json,HashMap.class);
                    boolean b = pmIterationService.saveIteration(map);
                    if(b){
                        return "SUCCESS";
                    }
                    return "ERROR";
                }catch (Exception e){
                    throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }
    @PostMapping(value = "/compileIteration", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "编辑模块迭代信息", httpMethod = "POST", notes = "编辑模块迭代信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'项目ID':'1','模块ID':}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> compileIteration(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String,String> map = gson.fromJson(json,HashMap.class);
                return pmIterationService.findOneById(Long.valueOf(map.get("id")));
            }
        });
    }

    @PostMapping(value = "/deleteIteration", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "删除模块迭代信息", httpMethod = "POST", notes = "删除模块迭代信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{''}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> deleteIteration(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String,String> map = gson.fromJson(json,HashMap.class);
                pmIterationService.delete(Long.valueOf(map.get("id")));
                return "SUCCESS";
            }
        });
    }


}
