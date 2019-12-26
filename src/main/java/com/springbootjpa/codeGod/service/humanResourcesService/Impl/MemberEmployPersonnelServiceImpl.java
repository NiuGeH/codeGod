package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.entity.humanResources.MemberEmployEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberEmployPersonnelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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



    private Specification<MemberEmployPersonnelEntity> getSpecification(Integer employPersionnelType ,Long employId) {
        return new Specification<MemberEmployPersonnelEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEmployPersonnelEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate preEmploy = !ObjectUtils.isEmpty(employId) ? criteriaBuilder.equal(root.get("employId").get("id"),employId) : criteriaBuilder.like(root.get("employPersionnelSkillId"), "%");
                Predicate prePersionnelType = !ObjectUtils.isEmpty(employPersionnelType) ? criteriaBuilder.equal(root.get("employPersionnelType"),employPersionnelType) : criteriaBuilder.like(root.get("employPersionnelSkillId"), "%");
                return criteriaBuilder.and(preEmploy,prePersionnelType);
            }
        };
    }

    @Override
    public Page<MemberEmployPersonnelEntity> doPage(Pageable pageable, Integer employPersionnelType,Long employId) {
        return memberEmployPersonnelentityRepository.findAll(getSpecification(employPersionnelType,employId),pageable);
    }
}
