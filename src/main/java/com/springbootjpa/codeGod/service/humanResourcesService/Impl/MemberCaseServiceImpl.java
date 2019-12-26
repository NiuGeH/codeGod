package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberCaseEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberCaseService;
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
public class MemberCaseServiceImpl extends MemberServiceBase implements MemberCaseService {
    @Override
    public Page<MemberCaseEntity> findAll(Pageable pageable,String memberId) {
        return memberCaseentityRepository.findAll(getSpecification(memberId),pageable);
    }

    /**
     * 添加用户案例
     * @param memberCaseEntity 案例实体
     * @param casePhotoMultipartFile 图片
     * @return 保存后的案例实体
     * @throws CodeGodException 异常
     */
    @Override
    public MemberCaseEntity doAdd(MemberCaseEntity memberCaseEntity, MultipartFile casePhotoMultipartFile) throws CodeGodRunTimExcetion, CodeGodException{

        UploadFile uploadFile = saveFileUtils.saveFile(casePhotoMultipartFile);
        if(!ObjectUtils.isEmpty(uploadFile)){
            memberCaseEntity.setCasePhoto(uploadFileRepository.save(uploadFile));
        }
        memberCaseEntity.setCasePlatformProject(HumanRecourcesStatus.MEMBERCASE_CASEPLATGORMPROJECT_F.getIndex());
        return memberCaseentityRepository.save(memberCaseEntity);

    }


    /**
     * 修改用户案例
     * @param memberCaseEntity 案例实体
     * @param casePhotoMultipartFile 图片
     * @return 修改后的案例实体
     * @throws CodeGodRunTimExcetion 自定义异常
     * @throws CodeGodException 自定义
     */
    @Override
    public MemberCaseEntity doUpdate(MemberCaseEntity memberCaseEntity, MultipartFile casePhotoMultipartFile) throws CodeGodRunTimExcetion, CodeGodException{
        MemberCaseEntity jdbc_case = memberCaseentityRepository.findById(memberCaseEntity.getId()).orElseThrow(() -> new CodeGodRunTimExcetion("案例Id不存在", this.getClass()));
        if(jdbc_case.getCasePlatformProject() == HumanRecourcesStatus.MEMBERCASE_CASEPLATGORMPROJECT_F.getIndex()){

            jdbc_case.setCasePlatformProject(memberCaseEntity.getCasePlatformProject());
            jdbc_case.setCaseProjectName(memberCaseEntity.getCaseProjectName());
            jdbc_case.setCaseCodingAbility(memberCaseEntity.getCaseCodingAbility());
            jdbc_case.setCaseAbilityToCommunicate(memberCaseEntity.getCaseAbilityToCommunicate());
            jdbc_case.setCaseSchedulePerformance(memberCaseEntity.getCaseSchedulePerformance());
            jdbc_case.setCaseTheDevelopmentCycle(memberCaseEntity.getCaseTheDevelopmentCycle());
            jdbc_case.setCaseProjectContribution(memberCaseEntity.getCaseProjectContribution());
            jdbc_case.setCaseTheSkillsUsed(memberCaseEntity.getCaseTheSkillsUsed());
            jdbc_case.setCaseParticipateInTheRole(memberCaseEntity.getCaseParticipateInTheRole());
            jdbc_case.setCaseThePerformanceReview(memberCaseEntity.getCaseThePerformanceReview());

            UploadFile uploadFile = saveFileUtils.saveFile(casePhotoMultipartFile);
            if(!ObjectUtils.isEmpty(uploadFile)){
                jdbc_case.setCasePhoto(uploadFileRepository.save(uploadFile));
            }

        }else {
            throw new CodeGodRunTimExcetion("该项目是平台项目",this.getClass());
        }
        return jdbc_case;
    }

    private Specification<MemberCaseEntity> getSpecification(String memberId) {
        return new Specification<MemberCaseEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberCaseEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate preValida = !ObjectUtils.isEmpty(memberId) ? criteriaBuilder.equal(root.get("memberId").get("id"), Long.valueOf(memberId)) : criteriaBuilder.like(root.get("caseProjectName"), "%");
                return criteriaBuilder.and(preValida);
            }
        };
    }
}
