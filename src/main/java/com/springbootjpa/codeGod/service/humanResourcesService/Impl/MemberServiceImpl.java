package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberentityRepository memberentityRepository;

    @Override
    public Page<MemberEntity> findAll(Pageable pageable) {
        return memberentityRepository.findAll(getSpecification(),pageable);
    }


    private Specification<MemberEntity> getSpecification() {
        return new Specification<MemberEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p2 = criteriaBuilder.like(root.get("nickName"),"%zhang%");
                return criteriaBuilder.and(p2);
            }
        };
    }
}
