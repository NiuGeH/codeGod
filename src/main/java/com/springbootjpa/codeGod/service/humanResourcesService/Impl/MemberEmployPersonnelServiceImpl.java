package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberEmployPersonnelService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.beans.Transient;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class MemberEmployPersonnelServiceImpl extends MemberServiceBase implements MemberEmployPersonnelService {



    private Specification<MemberEmployPersonnelEntity> getSpecification(Long employId,Integer...employPersionnelType ) {
        return new Specification<MemberEmployPersonnelEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEmployPersonnelEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate preEmploy = !ObjectUtils.isEmpty(employId) ? criteriaBuilder.equal(root.get("employId").get("id"),employId) : criteriaBuilder.like(root.get("employPersionnelSkillId"), "%");
                Predicate prePersionnelType = !ObjectUtils.isEmpty(employPersionnelType) ? criteriaBuilder.equal(root.get("employPersionnelType"),employPersionnelType[0]) : criteriaBuilder.like(root.get("employPersionnelSkillId"), "%");
                return criteriaBuilder.and(preEmploy,prePersionnelType);
            }
        };
    }

    @Override
    public Page<MemberEmployPersonnelEntity> doPage(Pageable pageable,Long employId, Integer...employPersionnelType) {
        Page<MemberEmployPersonnelEntity> all = memberEmployPersonnelentityRepository.findAll(getSpecification(employId,employPersionnelType), pageable);
        for (MemberEmployPersonnelEntity memberEmployPersonnelEntity : all) {
            String employPersionnelSkillId = memberEmployPersonnelEntity.getEmployPersionnelSkillId();
            if (!StringUtils.isEmpty(employPersionnelSkillId)){
                String[] split = employPersionnelSkillId.split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : split) {
                    sb.append(operationSkillRepository.findById(Long.valueOf(s)).orElseThrow(() -> new CodeGodRunTimExcetion("SkillId不存在",this.getClass())).getSkillName()+",");
                }
                memberEmployPersonnelEntity.setEmployPersionnelSkillIdData(sb.toString());
            }
        }
        return all;
    }

    @Override
    public MemberEmployPersonnelEntity doAddReleaseRequire(MemberEmployPersonnelEntity memberEmployPersonnelEntity) {

        //Id 不为空为雇佣需求中点击 邀请的发布需求
        if (!(ObjectUtils.isEmpty(memberEmployPersonnelEntity.getId()))) {
            MemberEmployPersonnelEntity jdbc_memberPersonnel = memberEmployPersonnelentityRepository.findById(memberEmployPersonnelEntity.getId()).orElseThrow(() -> new CodeGodRunTimExcetion("请求人员Id不存在", this.getClass()));

            if(jdbc_memberPersonnel.getEmployInvitationStatus() != HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_WYQ.getIndex()){
                throw new CodeGodRunTimExcetion("该人员不为未邀请状态",this.getClass());
            }

            jdbc_memberPersonnel.setEmployPersionnelEntrustName(memberEmployPersonnelEntity.getEmployPersionnelEntrustName());
            jdbc_memberPersonnel.setEmployWorkContent(memberEmployPersonnelEntity.getEmployWorkContent());
            jdbc_memberPersonnel.setEmployPersionnelComeToStartTime(memberEmployPersonnelEntity.getEmployPersionnelComeToStartTime());
            jdbc_memberPersonnel.setEmployPersionnelAbortTime(memberEmployPersonnelEntity.getEmployPersionnelAbortTime());
            jdbc_memberPersonnel.setEmployPersionnelSkillId(memberEmployPersonnelEntity.getEmployPersionnelSkillId());
            jdbc_memberPersonnel.setEmployMonthMoney(memberEmployPersonnelEntity.getEmployMonthMoney());
            jdbc_memberPersonnel.setEmployProjectAddress(memberEmployPersonnelEntity.getEmployProjectAddress());
            jdbc_memberPersonnel.setEmployDevelopmentWay(memberEmployPersonnelEntity.getEmployDevelopmentWay());
            jdbc_memberPersonnel.setEmployPersionnelSiginType(memberEmployPersonnelEntity.getEmployPersionnelSiginType());
            jdbc_memberPersonnel.setEmployInvitationStatus(HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YYY.getIndex());

            return jdbc_memberPersonnel;
        }else {
            //id 为空 为后台邀请人员 (人力外包管理 人员管理 --> 邀请人员)
            if(!(ObjectUtils.isEmpty(memberEmployPersonnelEntity.getMemberId()))){
                memberEmployPersonnelEntity.setEmployInvitationStatus(HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_INVITATION_STATYS_YYY.getIndex());
                memberEmployPersonnelEntity.setEmployPersionnelType(HumanRecourcesStatus.MEMBER_MEMBER_EMPLOY_PERSONNEL_TYPE_HTYQ.getIndex());
                return memberEmployPersonnelentityRepository.save(memberEmployPersonnelEntity);
            }else {
                throw new CodeGodRunTimExcetion("后台邀请人员Id为空",this.getClass());
            }
        }
    }

    @Override
    public MemberEmployPersonnelEntity doSetting(MemberEmployPersonnelEntity memberEmployPersonnelEntity) {
        if(ObjectUtils.isEmpty(memberEmployPersonnelEntity.getId())){
            throw new CodeGodRunTimExcetion("id为空",this.getClass());
        }else {
            MemberEmployPersonnelEntity jdbc_memberPersonnel = memberEmployPersonnelentityRepository.findById(memberEmployPersonnelEntity.getId()).orElseThrow(() -> new CodeGodRunTimExcetion("id 不存在", this.getClass()));
            jdbc_memberPersonnel.setEmployPersionnelComeToStartTime(memberEmployPersonnelEntity.getEmployPersionnelComeToStartTime());
            jdbc_memberPersonnel.setEmployPersionnelAbortTime(memberEmployPersonnelEntity.getEmployPersionnelAbortTime());
            jdbc_memberPersonnel.setEmployPersionnelSkillId(memberEmployPersonnelEntity.getEmployPersionnelSkillId());
            jdbc_memberPersonnel.setEmployWorkStatus(memberEmployPersonnelEntity.getEmployWorkStatus());
            jdbc_memberPersonnel.setEmployUnitPrice(memberEmployPersonnelEntity.getEmployUnitPrice());
            jdbc_memberPersonnel.setEmployMonthMoney(memberEmployPersonnelEntity.getEmployMonthMoney());
            jdbc_memberPersonnel.setEmployProjectAddress(memberEmployPersonnelEntity.getEmployProjectAddress());
            jdbc_memberPersonnel.setEmployDevelopmentWay(memberEmployPersonnelEntity.getEmployDevelopmentWay());
            return jdbc_memberPersonnel;
        }
    }
}
