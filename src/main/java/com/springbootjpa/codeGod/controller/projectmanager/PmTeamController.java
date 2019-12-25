package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.PmEvaluateEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmTeamEntity;
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

@Api(description="团队管理Controller")
@Controller
@RequestMapping("/PmTeamController")
public class PmTeamController extends PmDemandBase {
    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);


    @PostMapping(value = "/findAllTeam", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "团队数据信息", httpMethod = "POST", notes = "团队数据信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId':'1','page':1,'rows':5}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmTeamEntity> doPageTeam(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmTeamController/addModules 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmTeamEntity>>() {
            @Override
            public Page<PmTeamEntity> invoke(Pageable var1) throws Exception {
                Page<PmTeamEntity> PmTeamEntity = pmTeamService.doPage(var1, Long.valueOf(map.get("projectId")));
                return PmTeamEntity;
            }
        });
    }

    @PostMapping(value = "/addTeam", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加团队人员信息", httpMethod = "POST", notes = "添加团队人员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId(项目ID)':'1','memberId(用户ID)':1,'dutyName(模块类型)':5}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<HashMap<String,Object>> addTeam(@RequestBody String json){
        return AjaxUtils.process(new Func_T<HashMap<String,Object>>() {
            @Override
            public HashMap<String,Object> invoke() throws Exception {
                try {
                    logger.info("url:/PmTeamController/addTeam 请求参数" + json);
                    HashMap<String,Object> map = gson.fromJson(json,HashMap.class);
                    return pmTeamService.DataRendering(Long.valueOf(map.get("memberId").toString()),map.get("dutyName").toString(),Long.valueOf(map.get("projectId").toString()));
                } catch (Exception e) {
                    throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }

    @PostMapping(value = "/submitTeam", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "提交团队人员信息", httpMethod = "POST", notes = "提交团队人员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId(项目ID)':'1','memberId(用户ID)':1,'dutyName(模块类型)':5}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> submitTeam(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                try {
                    logger.info("url:/PmTeamController/submitTeam 请求参数" + json);
                    HashMap<String,Object> map = gson.fromJson(json,HashMap.class);
                    PmTeamEntity pmTeamEntity = gson.fromJson(json, PmTeamEntity.class);




                    return "SUCCESS";
                } catch (Exception e) {
                    throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }


    @PostMapping(value = "/updateTeam", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "修改团队人员信息回显", httpMethod = "POST", notes = "修改团队人员信息回显接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'teamId(团队人员ID)':1}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateTeam(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                try {
                    logger.info("url:/PmTeamController/updateTeam 请求参数" + json);
                    HashMap<String,Object> map = gson.fromJson(json,HashMap.class);
                    return pmTeamService.findOne(Long.valueOf(map.get("teamId").toString()));
                } catch (Exception e) {
                    throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }





    @PostMapping(value = "/findAllSalary", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "工资表数据信息", httpMethod = "POST", notes = "工资表数据信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':,'rows':,'团队人员ID'}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmSalaryEntity> doPage(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmTeamController/findAllSalary 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmSalaryEntity>>() {
            @Override
            public Page<PmSalaryEntity> invoke(Pageable var1) throws Exception {
                try {
                    PmSalaryEntity pmSalaryEntity = gson.fromJson(json, PmSalaryEntity.class);
                    return pmSalaryService.findOne(var1, pmSalaryEntity);
                } catch (Exception e) {
                    throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }

    @PostMapping(value = "/submitSalary", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "提交工资表信息", httpMethod = "POST", notes = "提交工资表信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'(团队人员ID)','款项名称','工资','社保缴纳','公积金缴纳','个税缴纳','奖金','实发工资','备注'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> addSalary(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                try{
                    PmSalaryEntity pmSalaryEntity = gson.fromJson(json, PmSalaryEntity.class);
                    boolean b = pmSalaryService.saveSalary(pmSalaryEntity);
                    if(b){
                        return "SUCCESS";
                    }
                    return "ERROR";
                }catch (Exception e){
                    throw  new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }

    @PostMapping(value = "/deleteSalary", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "删除工资表信息", httpMethod = "POST", notes = "删除工资表信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'工资表ID'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> deleteSalary(String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                try {
                    HashMap<String,Object> map = gson.fromJson(json,HashMap.class);
                    boolean b = pmSalaryService.deleteSalary(Long.valueOf(map.get("id").toString()));
                    if(b){
                        return "SUCCESS";
                    }
                    return "ERROR";
                } catch (Exception e) {
                    throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }

    @PostMapping(value = "/updateSalary", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "编辑工资表信息", httpMethod = "POST", notes = "编辑工资表信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'(团队人员ID)','款项名称','工资','社保缴纳','公积金缴纳','个税缴纳','奖金','实发工资','备注'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateSalary(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                try{
                    PmSalaryEntity pmSalaryEntity = gson.fromJson(json, PmSalaryEntity.class);
                    return pmSalaryService.findOne(pmSalaryEntity.getId());
                }catch (Exception e){
                    throw  new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
                }
            }
        });
    }



    @PostMapping(value = "/addEvaluate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加个人评价", httpMethod = "POST", notes = "添加个人评价接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'项目贡献度','编码能力/需求设计能力/设计能力/需求理解'," +
                    "'沟通能力/项目把控能力/支撑效率/测试能力'," +
                    "'进度表现/客户满意度/沟通能力'," +
                    "'绩效点评','团队人员ID'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> addEvaluate(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                PmEvaluateEntity pmEvaluateEntity = gson.fromJson(json, PmEvaluateEntity.class);
                boolean b = pmEvaluateService.saveEvalute(pmEvaluateEntity);
                if(b){
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });
    }



















}
