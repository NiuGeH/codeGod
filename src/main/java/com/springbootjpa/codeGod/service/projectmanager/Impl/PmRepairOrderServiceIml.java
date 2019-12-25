package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.projectmanager.PmRepairOrderEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import com.springbootjpa.codeGod.repository.projectmanager.PmRepairOrderentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmRepairOrderService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 工单
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmRepairOrderServiceIml implements PmRepairOrderService {

    @Autowired
    private PmRepairOrderentityRepository pmRepairOrderentityRepository;

    /**
     * 工单通知查询
     * @param pageable
     * @return
     */
    @Override
    public Page<PmRepairOrderEntity> doPage(Pageable pageable,Long projectId) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("pmProjectEntity").get("id"),projectId));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        pmRepairOrderentityRepository.findAll();
        return pmRepairOrderentityRepository.findAll(pageable);
    }

    /**
     * 查询单个工单信息
     * @param id
     * @return
     */
    @Override
    public PmRepairOrderEntity findRepairOrder(Long id) {
        Optional<PmRepairOrderEntity> byId = pmRepairOrderentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该工单为空",this.getClass());
        }
        return byId.get();
    }
}
