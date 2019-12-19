package com.springbootjpa.codeGod.controller.HumanResources;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.repository.HumanResources.MemberPrivacyentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberSignContractentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationMedalRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.service.baseService.Impl.BaseDataDirctionaryServiceImpl;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberPrivacyService;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberService;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberSignContractService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberBase {

    protected Gson gson = new Gson();

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected MemberSignContractService memberSignContractService;

    @Autowired
    protected MemberPrivacyService memberPrivacyService;

    @Autowired
    protected BaseDataDirctionaryService baseDataDirctionaryService;

    @Autowired
    protected SaveFileUtils saveFileUtils;

    @Autowired
    protected MemberSignContractentityRepository memberSignContractentityRepository;

    @Autowired
    protected OperationMedalRepository operationMedalRepository;

    @Autowired
    protected MemberPrivacyentityRepository memberPrivacyentityRepository;

    @Autowired
    protected UploadFileRepository uploadFileRepository;

    @Autowired
    protected MemberentityRepository memberentityRepository;
}
