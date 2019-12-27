package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.entity.humanResources.MemberWageEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberWageService;
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

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class MemberWageServiceImpl extends MemberServiceBase implements MemberWageService {

    @Override
    public MemberWageEntity doMakeSalary(MemberWageEntity memberWageEntity) {
        log.info("造工资 保存数据："+memberWageEntity.toString());
        memberWageEntity.setWagePaymentStatus(HumanRecourcesStatus.MEMBER_WAGE_PAYMENT_STATUS_WZF.getIndex());
        return memberWageentityRepository.save(memberWageEntity);
    }

    @Override
    public Page<MemberWageEntity> doPageByContractId(Pageable pageable, Long contractId) {
        return memberWageentityRepository.findAll(getSpecification(contractId),pageable);
    }

    @Override
    public MemberWageEntity doEditWage(MemberWageEntity wageEntity) {

        return null;
    }

    /**
     * 根据合同进行分页
     * @param contractId 合同id
     * @return Specification<MemberWageEntity>
     */
    private Specification<MemberWageEntity> getSpecification(Long contractId) {
        return new Specification<MemberWageEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberWageEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate preSigin = !ObjectUtils.isEmpty(contractId) ? criteriaBuilder.equal(root.get("contractId").get("id"), contractId) : criteriaBuilder.like(root.get("wageBonus"), "%");
                return criteriaBuilder.and(preSigin);
            }
        };
    }
}
