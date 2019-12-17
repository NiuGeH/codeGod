package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.repository.HumanResources.MemberSignContractentityRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberSignContractService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class MemberSignContractServiceImpl implements MemberSignContractService {

    @Autowired
    private MemberSignContractentityRepository memberSignContractentityRepository;

    @Override
    public Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable, Integer validationCode, Integer siginEnd) {

        return memberSignContractentityRepository.findAll(getSpecification(validationCode,siginEnd),pageable);
    }

    @Override
    public void byIdDelMemberSignContract(Long id) {
        Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("需要删除的Id不存在 byIdDelMemberSignContract",this.getClass());
        }
        memberSignContractentityRepository.delete(byId.get());
    }

    private Specification<MemberSignContractEntity> getSpecification(Integer validationCode,Integer siginEnd) {
        return new Specification<MemberSignContractEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberSignContractEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate p2 = criteriaBuilder.like(root.get("nickName"),"%zhang%");
                Predicate preValida = !ObjectUtils.isEmpty(validationCode) ?  criteriaBuilder.equal(root.get("siginVerificationCode"),validationCode) : criteriaBuilder.like(root.get("siginJobs"),"%");
                Predicate preSigin = !ObjectUtils.isEmpty(siginEnd) ? criteriaBuilder.equal(root.get("siginResults"),siginEnd) : criteriaBuilder.like(root.get("siginJobs"),"%");
                return criteriaBuilder.and(preValida,preSigin);
            }
        };
    }


}
