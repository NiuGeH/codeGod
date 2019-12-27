package com.springbootjpa.codeGod.controller.projectmanager;

import com.google.gson.Gson;

import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.service.projectmanager.*;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PmDemandBase {
    protected Gson gson = new Gson();
    //需求
    @Autowired
    protected PmDemandService pmDemandService;
    //产品经理报名
    @Autowired
    protected PmApplicationService pmApplicationService;
    //项目
    @Autowired
    protected PmProjectService pmProjectService;
    //合同
    @Autowired
    protected PmContractService pmContractService;
    //数据字典
    @Autowired
    protected BaseDataDirctionaryService baseDataDirctionaryService;
    //招聘
    @Autowired
    protected PmRecruitmentService pmRecruitmentService;
    //模块
    @Autowired
    protected PmModulesService pmModulesService;
    //团队
    @Autowired
    protected PmTeamService pmTeamService;
    //工资表
    @Autowired
    protected PmSalaryService pmSalaryService;
    //个人评价
    @Autowired
    protected PmEvaluateService pmEvaluateService;
    //工单
    @Autowired
    protected PmRepairOrderService pmRepairOrderService;
    //迭代
    @Autowired
    protected PmIterationService pmIterationService;
    //项目评价
    @Autowired
    protected PmRatingService pmRatingService;
    //结算
    @Autowired
    protected PmSettleAccountsService pmSettleAccountsService;
}
