package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.repository.projectmanager.PmApplicationentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class PmApplicationIml implements PmApplicationService {
    @Autowired
    private PmApplicationentityRepository pmApplicationentityRepository;
    /*
     * @Param    demandId 需求ID
     * @return   返回报名该需求产品经理
     */
    @Override
    public Page doPage(Pageable pageable, Long demandId) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtils.isEmpty(demandId.toString())){
                    list.add(criteriaBuilder.equal(root.get("demandId"),demandId.toString()));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return pmApplicationentityRepository.findAll(sp,pageable);
    }
}
