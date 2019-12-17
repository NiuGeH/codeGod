package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.common.PageRequestParam;
import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import com.springbootjpa.codeGod.repository.projectmanager.PmApplicationentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmProjectentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmApplicationService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private PmProjectentityRepository pmProjectentityRepository;

    /*
     * @Param    demandId 需求ID
     * @return   返回报名该需求产品经理
     */
    @Override
    public Page<PmApplicationEntity> doPage(Pageable pageable, Long demandId) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtils.isEmpty(demandId.toString())){
                    list.add(criteriaBuilder.equal(root.get("pmDemandEntity").get("id"),demandId.toString()));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<PmApplicationEntity> all = pmApplicationentityRepository.findAll(sp, pageable);
        ArrayList<PmApplicationEntity> list = new ArrayList<>();
        for (PmApplicationEntity pmApplicationEntity:all){
            System.out.println(pmApplicationEntity.getMemberEntity().getId());
            int count = pmProjectentityRepository.findAllByProjectManagerId(pmApplicationEntity.getMemberEntity().getId());
            pmApplicationEntity.setNumber(count);
            list.add(pmApplicationEntity);
        }
        return  new PageImpl<PmApplicationEntity>(list, pageable,list.size());
    }
}
