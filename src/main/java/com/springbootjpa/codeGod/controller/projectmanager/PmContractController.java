package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.*;
import com.springbootjpa.codeGod.entity.projectmanager.PmContractEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmRepairOrderEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmSettleAccountsEntity;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Api(description = "合同管理Controller")
@RequestMapping("/PmContractController")
@Controller
public class PmContractController extends PmDemandBase {


    private static Logger logger = LoggerFactory.getLogger(PmDemandController.class);


    @PostMapping(value = "/doPageContract", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "合同管理", httpMethod = "POST", notes = "合同管理接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'page':'1','rows':}"
                    , required = true, paramType = "body")
    })
    public PageResult<PmContractEntity> doPageContract(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmContractController/doPageContract 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmContractEntity>>() {
            @Override
            public Page<PmContractEntity> invoke(Pageable var1) throws Exception {
                return pmContractService.findAll(var1);
            }
        });
    }


    @PostMapping(value = "/addContract", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加合同", httpMethod = "POST", notes = "添加合同数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'contractName'(合同名称):'1','contractMoney'（合同金额）:'1','actualAmount（实际金额）':'会员系统'" +
                    ",'contractDate'(签订日期):1000.0,'paymentMethod'（付款方式）:," +
                    "'explains（付款方式说明）':'2019-12-06','project_id'(项目ID):,'demandId'（需求Id）:}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> addContract(HttpServletRequest servletRequest) {
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                MultipartFile file = null;
                if (servletRequest instanceof StandardMultipartHttpServletRequest) {
                    MultiValueMap<String, MultipartFile> fileMap = ((StandardMultipartHttpServletRequest) servletRequest).getMultiFileMap();
                    file = fileMap.get("file").get(0);
                }
                System.out.println(file);
                String type = servletRequest.getParameter("json");
                System.out.println(type);
//                logger.info("url:/PmContractController/addContract 请求参数" + json);
//                PmContractEntity pmContractEntity = gson.fromJson(json, PmContractEntity.class);
//                HashMap<String, String> map = gson.fromJson(json, HashMap.class);
//                boolean save = pmContractService.save(pmContractEntity, Long.valueOf(map.get("projectId")), ProjectStatus.筹备中.getIndex(), contractDocument);
//                if (save) {
//                    return "SUCCESS";
//                }
                return "ERROR";
            }
        });
    }

    @PostMapping(value = "/echoContract", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "合同数据回显", httpMethod = "POST", notes = "合同数据回显接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId':'1','demandId':'1','contractName':'会员系统','contractMoney':1000.0,'actualAmount':1000.0," +
                    "'contractDate':'2019-12-06'," +
                    "'paymentMethod':'10%20%20%10%10%','explains':'重庆','contractDocument_id':1}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> Contract(@RequestBody String json){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                logger.info("url:/PmContractController/echoContract 请求参数" + json);
                HashMap<String,String> map = gson.fromJson(json,HashMap.class);
                return pmContractService.findOneById(Long.valueOf(map.get("id")));
            }
        });
    }


    @PostMapping(value = "/renderData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "结算单页面数据渲染", httpMethod = "POST", notes = "结算单页面数据渲染接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId':'1','demandId':'1','contractName':'会员系统','contractMoney':1000.0,'actualAmount':1000.0," +
                    "'contractDate':'2019-12-06'," +
                    "'paymentMethod':'10%20%20%10%10%','explains':'重庆','contractDocument_id':1}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<HashMap<String,Object>>  renderData(){
        return AjaxUtils.process(new Func_T<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> invoke() throws Exception {
                logger.info("url:/PmContractController/renderData");
                HashMap<String,Object> map = new HashMap<>();
                map.put("funds_type",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.PMSETTLEACCOUNTS_FUNDSTYPE));
                return map;
            }
        });
    }





    @PostMapping(value = "/doPageSettleAccounts", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "结算单分页", httpMethod = "POST", notes = "结算单分页接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = ""
                    , required = true, paramType = "body")
    })
    public PageResult<PmSettleAccountsEntity> doPageSettleAccounts(@RequestBody String json) throws CodeGodException {
        logger.info("url:/PmContractController/doPageSettleAccounts 请求参数" + json);
        HashMap<String, String> map = gson.fromJson(json, HashMap.class);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        PageRequestParam pages = null;
        try {
            pages = gson.fromJson(json, PageRequestParam.class);
        } catch (Exception e) {
            throw new CodeGodException("Gson格式转换错误，请求参数为：" + json, this.getClass());
        }
        return AjaxUtils.process(pages, sort, new Func_T1<Pageable, Page<PmSettleAccountsEntity>>() {
            @Override
            public Page<PmSettleAccountsEntity> invoke(Pageable var1) throws Exception {
                return pmSettleAccountsService.findAll(var1);
            }
        });
    }





    @PostMapping(value = "/addSettleAccounts", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "添加结算单", httpMethod = "POST", notes = "添加结算单接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId':'1','demandId':'1','contractName':'会员系统','contractMoney':1000.0,'actualAmount':1000.0," +
                    "'contractDate':'2019-12-06'," +
                    "'paymentMethod':'10%20%20%10%10%','explains':'重庆','contractDocument_id':1}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> addSettleAccounts(@RequestBody String json,@RequestParam("certificate") MultipartFile certificate){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                PmSettleAccountsEntity pmSettleAccountsEntity = gson.fromJson(json, PmSettleAccountsEntity.class);
                boolean b = pmSettleAccountsService.saveSettleAccount(pmSettleAccountsEntity, certificate);
                if(b){
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });
    }

    @PostMapping(value = "/collectionConfirmation", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "确认收款", httpMethod = "POST", notes = "确认收款接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "{'projectId':'1','demandId':'1','contractName':'会员系统','contractMoney':1000.0,'actualAmount':1000.0," +
                    "'contractDate':'2019-12-06'," +
                    "'paymentMethod':'10%20%20%10%10%','explains':'重庆','contractDocument_id':1}"
                    , required = true, paramType = "body")
    })
    public AjaxResult<Object> collectionConfirmation(@RequestBody String json,@RequestParam("payEvidence") MultipartFile payEvidence){
        return AjaxUtils.process(new Func_T<Object>() {
            @Override
            public Object invoke() throws Exception {
                PmSettleAccountsEntity pmSettleAccountsEntity = gson.fromJson(json, PmSettleAccountsEntity.class);
                boolean b = pmSettleAccountsService.collectionConfirmation(pmSettleAccountsEntity, payEvidence);
                if(b){
                    return "SUCCESS";
                }
                return "ERROR";
            }
        });
    }


}
