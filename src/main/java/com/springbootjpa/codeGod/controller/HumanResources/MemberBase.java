package com.springbootjpa.codeGod.controller.HumanResources;

import com.google.gson.Gson;
import com.springbootjpa.codeGod.entity.humanResources.MemberWageEntity;
import com.springbootjpa.codeGod.repository.HumanResources.*;
import com.springbootjpa.codeGod.repository.Operation.*;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.service.baseService.Impl.BaseDataDirctionaryServiceImpl;
import com.springbootjpa.codeGod.service.humanResourcesService.*;
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

    @Autowired
    protected OperationRegionRepository operationRegionRepository;

    @Autowired
    protected OperationCompanyRepository operationCompanyRepository;

    @Autowired
    protected OperationResourceSkillRepository operationResourceSkillRepository;

    @Autowired
    protected MemberResourceSkillentityRepository memberResourceSkillentityRepository;

    @Autowired
    protected MemberResourceentityRepository memberResourceentityRepository;

    @Autowired
    protected MemberCaseService memberCaseService;

    @Autowired
    protected MemberCaseentityRepository memberCaseentityRepository;

    @Autowired
    protected MemberEmployService memberEmployService;

    @Autowired
    protected MemberEmployentityRepository memberEmployentityRepository;

    @Autowired
    protected MemberEmployPersonnelentityRepository memberEmployPersonnelentityRepository;

    @Autowired
    protected MemberEmployPersonnelService memberEmployPersonnelService;

    @Autowired
    protected MemberContractService memberContractService;

    @Autowired
    protected MemberContractEntityRepository memberContractEntityRepository;

    @Autowired
    protected MemberWageService memberWageService;

    @Autowired
    protected MemberWageentityRepository memberWageentityRepository;

}
