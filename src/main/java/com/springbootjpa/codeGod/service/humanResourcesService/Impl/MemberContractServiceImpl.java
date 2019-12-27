package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberContractService;
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

/**
 * 人力外包管理Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class MemberContractServiceImpl extends MemberServiceBase implements MemberContractService {



    private Specification<MemberContractEntity> getSpecification() {
        return new Specification<MemberContractEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberContractEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate preEmploy = !ObjectUtils.isEmpty(employId) ? criteriaBuilder.equal(root.get("employId").get("id"),employId) : criteriaBuilder.like(root.get("employPersionnelSkillId"), "%");
//                Predicate prePersionnelType = !ObjectUtils.isEmpty(employPersionnelType) ? criteriaBuilder.equal(root.get("employPersionnelType"),employPersionnelType) : criteriaBuilder.like(root.get("employPersionnelSkillId"), "%");
                return criteriaBuilder.and();

            }
        };
    }

    @Override
    public Page<MemberContractEntity> doPage(Pageable pageable) {
        return memberContractEntityRepository.findAll(getSpecification(),pageable);
    }

    @Override
    public MemberContractEntity doAddContract(MemberContractEntity memberContractEntity, MultipartFile contractPactMultipartFile) throws CodeGodException {
        UploadFile uploadFile = saveFileUtils.saveFile(contractPactMultipartFile);
        if(!ObjectUtils.isEmpty(uploadFile)){
            UploadFile save = uploadFileRepository.save(uploadFile);
            memberContractEntity.setContractPact(save);
        }
        return memberContractEntityRepository.save(memberContractEntity);
    }

    @Override
    public MemberContractEntity doEditContract(MemberContractEntity memberContractEntity, MultipartFile contractPactMultipartFile) throws CodeGodException {

        MemberContractEntity jdbc_contract = memberContractEntityRepository.findById(memberContractEntity.getId()).orElseThrow(() -> new CodeGodException("合同Id 不存在", this.getClass()));
        UploadFile uploadFile = saveFileUtils.saveFile(contractPactMultipartFile);
        if(!ObjectUtils.isEmpty(uploadFile)){
            memberContractEntity.setContractPact(uploadFileRepository.save(uploadFile));
        }
        jdbc_contract.setMemberId(memberContractEntity.getMemberId());
        jdbc_contract.setEmployId(memberContractEntity.getEmployId());
        jdbc_contract.setContractUnit(memberContractEntity.getContractUnit());
        jdbc_contract.setContractCycleStart(memberContractEntity.getContractCycleStart());
        jdbc_contract.setContractCycleEnd(memberContractEntity.getContractCycleEnd());
        jdbc_contract.setContractCloseWay(memberContractEntity.getContractCloseWay());
        jdbc_contract.setContractPact(memberContractEntity.getContractPact());
        jdbc_contract.setContractAddress(memberContractEntity.getContractAddress());
        jdbc_contract.setContractStatus(memberContractEntity.getContractStatus());
        jdbc_contract.setContractCourt(memberContractEntity.getContractCourt());
        return jdbc_contract;
    }
}
