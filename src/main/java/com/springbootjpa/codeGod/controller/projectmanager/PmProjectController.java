package com.springbootjpa.codeGod.controller.projectmanager;
import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.*;
import com.springbootjpa.codeGod.eunm.ProjectStatus;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;

@Api(description = "项目管理Controller")
@RequestMapping("/PmProjectController")
@Controller
public class PmProjectController extends PmDemandBase {

    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);


    @PostMapping(value = "/findAllPmProject", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "项目管理分页", httpMethod = "POST", notes = "项目分页全查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','projectStatus':'0'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public PageResult<PmProjectEntity> doPage(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmProjectController/findAllPmProject 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmProjectEntity>>() {
            @Override
            public Page<PmProjectEntity> invoke(Pageable pages) throws Exception {
                Page<PmProjectEntity> all = pmProjectService.doPage(pages, Integer.valueOf(map.get("projectStatus")));
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
    public AjaxResult<Object> findOneProjectById(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmProjectController/findOneProjectById 请求参数" + json);
                HashMap<String, String> map = gson.fromJson(json, HashMap.class);
                return pmProjectService.findOne(Long.valueOf(map.get("id")));
            }
        });
    }


    @PostMapping(value = "/updateProjectById", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "编辑项目数据", httpMethod = "POST", notes = "项目数据编辑接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':1,'productManagerId':41,'demandId':1,'projectName':'会员系统','projectBudget':1000.0,'projectType':1,'projectPeriod':'30天'," +
                    "'projectDelivery_time':'2019-12-01','projectAdderss':'重庆','projectDevelopmentModel':1,'projectRemark':'很简单的','projectKeyword':'牛逼，贼牛逼，老牛逼了'," +
                    "'projectIntroduce':'这是个贼牛逼的项目','projectDesignDocumentId':'','privateProject':0,'projectPassword':''}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateProjectById(@RequestBody String json, @RequestParam("requirementDocument") MultipartFile[] requirementDocument) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmProjectController/updateProjectById 请求参数" + json);
                PmProjectEntity pmProjectEntity = gson.fromJson(json, PmProjectEntity.class);
                boolean flag = pmProjectService.saveProject(pmProjectEntity,requirementDocument);
                if (flag) {
                    return "success";
                }
                return "error";
            }
        });
    }


    @PostMapping(value = "/addContract", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加合同", httpMethod = "POST", notes = "添加合同数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId':'1','demandId':'1','contractName':'会员系统','contractMoney':1000.0,'actualAmount':1000.0," +
                    "'contractDate':'2019-12-06'," +
                    "'paymentMethod':'10%20%20%10%10%','explains':'重庆','contractDocument_id':1}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> addContract(@RequestBody String json,@RequestParam("contractDocument") MultipartFile contractDocument) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmProjectController/addContract 请求参数" + json);
                PmContractEntity pmContractEntity = gson.fromJson(json, PmContractEntity.class);
                HashMap<String,String> map = gson.fromJson(json, HashMap.class);
                boolean save = pmContractService.save(pmContractEntity,Long.valueOf(map.get("projectId")), ProjectStatus.筹备中.getIndex(),contractDocument);
                if (save) {
                    return "SUCCESS";
                }
                return "ERROR";
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
    public PageResult<PmRecruitmentEntity> doPageRecruitment(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmProjectController/findAllRecruitment 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmRecruitmentEntity>>() {
            @Override
            public Page<PmRecruitmentEntity> invoke(Pageable var1) throws Exception {
                Page<PmRecruitmentEntity> all = pmRecruitmentService.dopage(var1);
                return all;
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
    public AjaxResult<HashMap<String, Object>> addRecruitment(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<HashMap<String,  Object>>() {
            @Override
            public HashMap<String,  Object> invoke() throws Exception {
                logger.info("url:/PmProjectController/addRecruitment 请求参数" + json);
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("duty",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY));
                map.put("rule",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGPOST));
                map.put("stationing",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACTCONTRACT_COURT));
                map.put("display",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.OPERATIONTOPIC_DISPLAY));
                return map;
            }
        });
    }

    @PostMapping(value = "/submitRecruitment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "提交招聘信息", httpMethod = "POST", notes = "提交招聘信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> submitRecruitment(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("URL:/PmProjectController/submitRecruitment 请求参数"+json);
                PmRecruitmentEntity pmRecruitmentEntity = gson.fromJson(json,PmRecruitmentEntity.class);
                boolean b = pmRecruitmentService.saveRecruitment(pmRecruitmentEntity);
                if(b){
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });
    }

    @PostMapping(value = "/updateRecruitment", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "编辑招聘信息", httpMethod = "POST", notes = "编辑招聘信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> updateRecruitment(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("URL:/PmProjectController/updateRecruitment 请求参数"+json);
                HashMap<String,String> param = gson.fromJson(json,HashMap.class);
                return pmRecruitmentService.findOne(Long.valueOf(param.get("id")));
            }
        });
    }






    @PostMapping(value = "/findAllModules", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "模块信息数据", httpMethod = "POST", notes = "模块信息数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmModulesEntity> doPageModules(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmProjectController/findAllModules 请求参数" + json);
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
                Page<PmModulesEntity> pmModulesEntities = pmModulesService.doPage(var1, Long.valueOf(map.get("projectId")));
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
    public AjaxResult<Object> saveModule(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmProjectController/saveModules 请求参数" + json);
                PmModulesEntity pmModulesEntity = gson.fromJson(json,PmModulesEntity.class);
                boolean flag = pmModulesService.saveModelus(pmModulesEntity);
                if(flag){
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
    public AjaxResult<HashMap<String,Object>> addModules(@RequestBody String json){
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                logger.info("url:/PmProjectController/addModules 请求参数" + json);
                HashMap<String,Object> map = new HashMap<>();
                map.put("moduleType ",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_MODULESMODULE_TYPE));
                map.put("technologyStack",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_MODULESTECHNOLOGY_STACK));
                map.put("duty",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY));
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
    public AjaxResult<Object> updateModules(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String,String> map = gson.fromJson(json,HashMap.class);
                return pmModulesService.findOne(Long.valueOf(map.get("id")));
            }
        });
    }
























}
