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
    @Autowired
    protected PmDemandService pmDemandService;
    @Autowired
    protected PmApplicationService pmApplicationService;
    @Autowired
    protected PmProjectService pmProjectService;
    @Autowired
    protected PmContractService pmContractService;
    @Autowired
    protected BaseDataDirctionaryService baseDataDirctionaryService;
    @Autowired
    protected PmRecruitmentService pmRecruitmentService;
    @Autowired
    protected PmModulesService pmModulesService;
    @Autowired
    protected PmTeamService pmTeamService;
    @Autowired
    protected PmSalaryService pmSalaryService;
    @Autowired
    protected PmEvaluateService pmEvaluateService;
    @Autowired
    protected PmRepairOrderService pmRepairOrderService;
    @Autowired
    protected PmIterationService pmIterationService;
    @Autowired
    protected PmRatingService pmRatingService;
}
