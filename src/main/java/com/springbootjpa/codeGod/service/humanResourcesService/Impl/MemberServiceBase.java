package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.repository.HumanResources.*;
import com.springbootjpa.codeGod.repository.Operation.*;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceBase {
    @Autowired
    protected MemberSignContractentityRepository memberSignContractentityRepository;

    @Autowired
    protected MemberPrivacyentityRepository memberPrivacyentityRepository;

    @Autowired
    protected UploadFileRepository uploadFileRepository;

    @Autowired
    protected MemberentityRepository memberentityRepository;

    @Autowired
    protected SaveFileUtils saveFileUtils = new SaveFileUtils();

    @Autowired
    protected OperationRegionRepository operationRegionRepository;

    @Autowired
    protected OperationCompanyRepository operationCompanyRepository;

    @Autowired
    protected BaseDataDirctionaryService baseDataDirctionaryService;

    @Autowired
    protected OperationMedalRepository operationMedalRepository;

    @Autowired
    protected OperationTeamRepository operationTeamRepository;

    @Autowired
    protected MemberResourceentityRepository memberResourceentityRepository;

    @Autowired
    protected MemberResourceSkillentityRepository memberResourceSkillentityRepository;

    @Autowired
    protected OperationResourceRepository operationResourceRepository;

    @Autowired
    protected OperationSkillRepository operationSkillRepository;

    @Autowired
    protected MemberCaseentityRepository memberCaseentityRepository;

    @Autowired
    protected MemberEmployentityRepository memberEmployentityRepository;

    @Autowired
    protected MemberEmployPersonnelentityRepository memberEmployPersonnelentityRepository;

    @Autowired
    protected MemberContractEntityRepository memberContractEntityRepository;



}
