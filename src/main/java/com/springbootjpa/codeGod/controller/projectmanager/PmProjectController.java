package com.springbootjpa.codeGod.controller.projectmanager;


import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.*;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','projectStatus':'0'}"
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
            @ApiImplicitParam(name = "json", value = "{'id':'1'}"
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
            @ApiImplicitParam(name = "productManagerId", value = "产品经理ID", dataType = "long"),
            @ApiImplicitParam(name = "id", value = "项目ID", dataType = "long"),
            @ApiImplicitParam(name = "demandId", value = "关联需求编号", dataType = "long"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", dataType = "String"),
            @ApiImplicitParam(name = "projectType", value = "项目类型", dataType = "int"),
            @ApiImplicitParam(name = "projectPeriod", value = "项目周期", dataType = "int"),
            @ApiImplicitParam(name = "projectDeliveryTime", value = "交付时限", dataType = "time"),
            @ApiImplicitParam(name = "projectDevelopmentModel", value = "开发方式", dataType = "int"),
            @ApiImplicitParam(name = "projectRemark", value = "备注", dataType = "String"),
            @ApiImplicitParam(name = "projectKeyword", value = "关键字", dataType = "String"),
            @ApiImplicitParam(name = "projectIntroduce", value = "项目介绍", dataType = "String"),
            @ApiImplicitParam(name = "privateProject", value = "私有项目", dataType = "int"),
            @ApiImplicitParam(name = "projectPassword", value = "密码", dataType = "int"),
            @ApiImplicitParam(name = "requirementDocument", paramType = "formData", value = "需求文档"),
    })
    public AjaxResult<Object> updateProjectById(Long productManagerId
            , Long id
            , Long demandId
            , String projectName
            , int projectType
            , String projectPeriod
            , String projectDeliveryTime
            , int projectDevelopmentModel
            , String projectRemark
            , String projectKeyword
            , String projectIntroduce
            , int privateProject
            , int projectPassword
            , @RequestParam("requirementDocument") MultipartFile[] requirementDocument) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parse = simpleDateFormat.parse(projectDeliveryTime);
                PmProjectEntity pmProjectEntity = new PmProjectEntity();
                pmProjectEntity.setId(id);
                pmProjectEntity.setDemandId(demandId);
                pmProjectEntity.setProductManagerId(productManagerId);
                pmProjectEntity.setProjectType(projectType);
                pmProjectEntity.setProjectName(projectName);
                pmProjectEntity.setProjectPeriod(projectPeriod);
                pmProjectEntity.setProjectDeliveryTime(parse);
                pmProjectEntity.setProjectDevelopmentModel(projectDevelopmentModel);
                pmProjectEntity.setProjectRemark(projectRemark);
                pmProjectEntity.setProjectKeyword(projectKeyword);
                pmProjectEntity.setProjectIntroduce(projectIntroduce);
                pmProjectEntity.setPrivateProject(privateProject);
                pmProjectEntity.setProjectPassword(projectPassword);
                logger.info("url:/PmProjectController/updateProjectById 请求参数" + pmProjectEntity);
                boolean flag = pmProjectService.saveProject(pmProjectEntity, requirementDocument);
                if (flag) {
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
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                logger.info("url:/PmProjectController/addRecruitment 请求参数" + json);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("duty", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY));
                map.put("rule", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGPOST));
                map.put("stationing", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_CONTRACTCONTRACT_COURT));
                map.put("display", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.OPERATION_CASE_DISPLAY));
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
    public AjaxResult<Object> submitRecruitment(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("URL:/PmProjectController/submitRecruitment 请求参数" + json);
                PmRecruitmentEntity pmRecruitmentEntity = gson.fromJson(json, PmRecruitmentEntity.class);
                boolean b = pmRecruitmentService.saveRecruitment(pmRecruitmentEntity);
                if (b) {
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
                logger.info("URL:/PmProjectController/updateRecruitment 请求参数" + json);
                HashMap<String, String> param = gson.fromJson(json, HashMap.class);
                return pmRecruitmentService.findOne(Long.valueOf(param.get("id")));
            }
        });
    }



    @PostMapping(value = "/doPageRepairOrder", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "工单管理", httpMethod = "POST", notes = "工单管理接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmRepairOrderEntity> doPageRepairOrder(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmProjectController/doPageRepairOrder 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmRepairOrderEntity>>() {
            @Override
            public Page<PmRepairOrderEntity> invoke(Pageable var1) throws Exception {
                return pmRepairOrderService.doPage(var1, Long.valueOf(map.get("projectId")));
            }
        });
    }
    @PostMapping(value = "/examineRepairOrder", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "查看工单详细信息", httpMethod = "POST", notes = "查看工单详细信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'工单ID':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> examineRepairOrder(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                HashMap<String, Object> map = gson.fromJson(json, HashMap.class);
                return pmRepairOrderService.findRepairOrder(Long.valueOf(map.get("id").toString()));
            }
        });
    }


    @PostMapping(value = "/addProjectEvaluation", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加项目评价", httpMethod = "POST", notes = "添加项目评价接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'工单ID':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object>  addProjectEvaluation(@RequestBody String json,@RequestParam("performance") MultipartFile[] performance,@RequestParam("photos") MultipartFile photos){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmProjectController/addProjectEvaluation 请求参数" + json);
                HashMap<String,String> map = gson.fromJson(json,HashMap.class);
                boolean b = pmRatingService.saveRating(performance, photos, map);
                if(b){
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });

    }























}
