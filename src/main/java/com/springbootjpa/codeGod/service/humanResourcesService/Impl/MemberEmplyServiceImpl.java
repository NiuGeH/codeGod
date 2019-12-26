package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberCaseEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberEmployService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class MemberEmplyServiceImpl extends MemberServiceBase implements MemberEmployService  {
    @Override
    public Page<MemberEmployEntity> doPage(Pageable pageable) {
        return memberEmployentityRepository.findAll(getSpecification(),pageable);
    }

    @Override
    public MemberEmployEntity doRefused(Long id, String employReason) {
        MemberEmployEntity memberEmployEntity = memberEmployentityRepository.findById(id).orElseThrow(() -> new CodeGodRunTimExcetion("需求Id 不存在", this.getClass()));
        if(memberEmployEntity.getEmployStatus() != HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_WCL.getIndex()){
            throw new CodeGodRunTimExcetion("该需求不为未处理状态",this.getClass());
        }else {
            memberEmployEntity.setEmployStatus(HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_YJJ.getIndex());
            memberEmployEntity.setEmployReason(employReason);
        }
        return memberEmployentityRepository.save(memberEmployEntity);
    }

    @Override
    public MemberContractEntity doSignEmploy(MemberContractEntity memberContractEntity,String contractAddressId, MultipartFile contractPactMultipartFile) throws CodeGodException {

        MemberEmployEntity memberEmployEntity = memberEmployentityRepository.findById(memberContractEntity.getId()).orElseThrow(() -> new CodeGodRunTimExcetion("需求id 不存在", this.getClass()));
        OperationRegionEntity operationRegionEntity = null;
        if(!ObjectUtils.isEmpty(contractAddressId)){
            operationRegionEntity = operationRegionRepository.findById(Long.valueOf(contractAddressId)).get();
        }
        memberEmployEntity.setEmployItemAddress(operationRegionEntity);
        memberContractEntity.setMemberId(memberEmployEntity.getMemberEntrustId());
        memberContractEntity.setEmployId(memberEmployEntity);
        memberContractEntity.setContractAddress(operationRegionEntity);
        UploadFile uploadFile = saveFileUtils.saveFile(contractPactMultipartFile);
        if(!ObjectUtils.isEmpty(uploadFile)){
            memberContractEntity.setContractPact(uploadFileRepository.save(uploadFile));
        }else {
            throw new CodeGodRunTimExcetion("合同文件为空 ",this.getClass());
        }
        memberEmployEntity.setEmployStatus(HumanRecourcesStatus.MEMBER_EMPLOY_EMPLOYSTATUS_YQHT.getIndex());
        memberEmployentityRepository.save(memberEmployEntity);
        return memberContractEntityRepository.save(memberContractEntity);
    }

    private Specification<MemberEmployEntity> getSpecification() {
        return new Specification<MemberEmployEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEmployEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and();
            }
        };
    }
}
