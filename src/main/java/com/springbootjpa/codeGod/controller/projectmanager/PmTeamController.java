package com.springbootjpa.codeGod.controller.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.common.AjaxUtils;
import com.springbootjpa.codeGod.common.Func_T1;
import com.springbootjpa.codeGod.common.PageRequestParam;
import com.springbootjpa.codeGod.common.PageResult;
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

@Api("团队管理Controller")
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












}
