package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.alibaba.druid.sql.PagerUtils;
import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.common.PageRequestParam;
import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.repository.projectmanager.PmApplicationentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Override
    public Page<PmApplicationService> doPageByDemandId(Pageable pageable, Long demandId) {
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

        return null;
    }
}
