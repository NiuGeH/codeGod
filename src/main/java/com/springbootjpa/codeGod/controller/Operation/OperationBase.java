package com.springbootjpa.codeGod.controller.Operation;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.service.operationService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/16 15:41
 */
@Component
public class OperationBase {

    protected Gson gson = new Gson();

    @Autowired
    protected OperationCaseService operationCaseService;

    @Autowired
    protected OperationRegionService operationRegionService;

    @Autowired
    protected OperationTopicService operationTopicService;

    @Autowired
    protected OperationSubtopicService operationSubtopicService;

    @Autowired
    protected OperationMedalService operationMedalService;

    @Autowired
    protected OperationNewsService operationNewsService;

    @Autowired
    protected OperationSkillService operationSkillService;

    @Autowired
    protected OperationResourceService operationResourceService;

    @Autowired
    protected OperationCodeGodDataService operationCodeGodDataService;

    @Autowired
    protected OperationCompanyService operationCompanyService;

    @Autowired
    protected OperationPersonalMenuService operationPersonalMenuService;

}
