package com.springbootjpa.codeGod.controller.Operation;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.service.operationService.OperationCaseService;
import com.springbootjpa.codeGod.service.operationService.OperationRegionService;
import com.springbootjpa.codeGod.service.operationService.OperationTopicService;
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

}
