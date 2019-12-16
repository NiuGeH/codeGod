package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberSignContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberSignContractServiceImpl implements MemberSignContractService {

    @Override
    public Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable, Integer validationCode, Integer siginEnd) {

        return null;
    }

    private Specification<MemberEntity> getSpecification(Integer validationCode,Integer siginEnd) {
        return new Specification<MemberEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate p2 = criteriaBuilder.like(root.get("nickName"),"%zhang%");
                return criteriaBuilder.and(p2);
            }
        };
    }


}
