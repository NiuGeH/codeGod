package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.eunm.DemandStatus;
import com.springbootjpa.codeGod.entity.eunm.ReturnStatus;
import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.eunm.ProjectStatus;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import io.swagger.annotations.*;
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

@Api(description = "需求管理Controller")
@RequestMapping("/PmDemandController")
@Controller
public class PmDemandController extends PmDemandBase {
    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);

    @PostMapping(value = "/findAllPmDemand", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "需求管理分页", httpMethod = "POST", notes = "需求分页全查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXjnLLz29mLKqw++5mAdbk8k="
                    , required = true, paramType = "body")
    })
    public PageResult<PmDemandEntity> doPage(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmDemandController/findAllPmDemand 请求参数" + json);
        //HashMap<String,String> map= gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmDemandEntity>>() {
            @Override
            public Page<PmDemandEntity> invoke(Pageable pages) throws Exception {
                Page<PmDemandEntity> all = pmDemandService.findAll(pages);
                System.out.println(all.getSize());
                System.out.println(all.getTotalElements() + "" + all.getTotalPages());
                for (PmDemandEntity pmDemandEntity : all) {
                    System.out.println(pmDemandEntity.getDemandStatus1());
                    System.out.println(pmDemandEntity.getDemandDeliverTime());
                }
                return all;
            }
        });
    }

    @PostMapping(value = "/refuseByIdPmDemand", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "拒绝需求", httpMethod = "POST", notes = "拒绝需求接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id':'1','demandRefusalCause':'做不了太难了'}" +
                    "\nfNE4sE7Clc/CagXQkLu4BpfZMZfbM1KhaVdYzgLGIdTIMCcJIzzU9PC/VPfZSVmmU9tYEMvhpg2yehNpM/cYBg=="
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> refuseByIdPmDemand(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmDemandController/refuseByIdPmDemand 请求参数" + json);
                PmDemandEntity pmDemandEntity = gson.fromJson(json, PmDemandEntity.class);
                System.out.println(pmDemandEntity.getDemandRefusalCause() + "原因  id:" + pmDemandEntity.getId());
                int i = pmDemandService.updateDemand(pmDemandEntity.getDemandRefusalCause(), pmDemandEntity.getId(), DemandStatus.已拒单.getIndex());
                if (i > 0) {
                    return "success";
                }
                return "error";
            }
        });
    }

    @PostMapping(value = "/findAllProjectManager", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "报名产品经理分页", httpMethod = "POST", notes = "报名产品经理分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':'5','demandId':'1'}" +
                    "\n0Gj755mNLsrxeUhCgPQWXmxctlIuTqSNF5t51HmgRcLBtwIWH3+hb8XC6dctRuKA"
                    , required = true, paramType = "body")
    })
    public PageResult<PmApplicationEntity> findAllProjectManager(@RequestBody String json) throws CodeGodException {
        logger.info("url:/findAllProjectManager/findAllProjectManager 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.ASC, "number");
        PageRequestParam pageRequest = null;
        try {
            pageRequest = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pageRequest, sort, new Func_T1<Pageable, Page<PmApplicationEntity>>() {
            @Override
            public Page<PmApplicationEntity> invoke(Pageable var1) throws Exception {
                Page<PmApplicationEntity> all = pmApplicationService.doPage(var1, Long.valueOf(map.get("demandId")));
                for (PmApplicationEntity pmApplicationEntity : all) {
                    System.out.println(pmApplicationEntity.getMemberEntity().getNickName() + " " + pmApplicationEntity.getRemark() + " " + pmApplicationEntity.getNumber());
                }
                return all;
            }
        });
    }


    @PostMapping(value = "/settingProjectManager", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "设置产品经理", httpMethod = "POST", notes = "设置产品经理接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "\nnQyECdObl+7oYn7CXkboxAerHNh99ElaeyEh7SIjdgh+IWZa/06ikj0rg9DL+UoF" +
                    "\n{'productManagerId':'41','id':'1'}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> settingProjectManager(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmDemandController/settingProjectManager 请求参数" + json);
                PmDemandEntity pmDemandEntity = gson.fromJson(json, PmDemandEntity.class);
                boolean falg = pmDemandService.settingProjectManager(pmDemandEntity.getProductManagerId(), pmDemandEntity.getId(), DemandStatus.已安排产品.getIndex());
                if (falg) {
                    return "success";
                }
                return "error";
            }
        });
    }


    @PostMapping(value = "/sendProject", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "发布项目", httpMethod = "POST", notes = "发布项目接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'id（需求编号）':'1'}" +
                    "\ngrIVrCdxtEbLol2tUxfI2g=="
                    , required = true, paramType = "body")
    })
    public AjaxResult<HashMap<String, Object>> sendProject(@RequestBody String json) {
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                HashMap<String, String> hashMap = gson.fromJson(json, HashMap.class);
                HashMap<String, Object> map = new HashMap<String, Object>();
                PmDemandEntity one = pmDemandService.findOne(Long.valueOf(hashMap.get("id")));
                map.put("pmDemandEntity", one);
                map.put("privateProject", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PM_PROJECTPRIVATE_PROJECT));
                return map;
            }
        });
    }


    @PostMapping(value = "/affirmSendProject", headers = "content-type=multipart/form-data")
    @ResponseBody
    @ApiOperation(value = "确认发布项目", httpMethod = "POST", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productManagerId", value = "产品经理ID", dataType = "long"),
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
    public AjaxResult<Object> affirmSendProject(Long productManagerId
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
                logger.info("url:/PmDemandController/affirmSendProject 请求参数"+pmProjectEntity.toString());
                System.out.println(pmProjectEntity);
                //🚗
                pmProjectEntity.setProjectStatus(ProjectStatus.洽谈中.getIndex());
                boolean flag = pmProjectService.saveProject(pmProjectEntity,requirementDocument);
                if(flag){
                    return ReturnStatus.SUCCESS.getName();
                }
                return ReturnStatus.ERROR.getName();
            }
        });
    }
}