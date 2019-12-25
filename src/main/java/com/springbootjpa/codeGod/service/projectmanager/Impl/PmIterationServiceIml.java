package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.projectmanager.PmIterationEntity;
import com.springbootjpa.codeGod.repository.projectmanager.PmIterationentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmIterationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 模块迭代
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmIterationServiceIml implements PmIterationService {


    @Autowired
    private PmIterationentityRepository pmIterationentityRepository;

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        pmIterationentityRepository.deleteById(id);
    }

    /**
     * 根据ID查询单个
     * @param id
     * @return
     */
    @Override
    public PmIterationEntity findOneById(Long id) {
        Optional<PmIterationEntity> byId = pmIterationentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该信息不存在！",this.getClass());
        }
        return byId.get();
    }

    /**
     * 根据项目ID查询分页
     * @param pageable
     * @param porjectId
     * @return
     */
    @Override
    public Page<PmIterationEntity> doPage(Pageable pageable, Long porjectId) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("pmProjectEntity").get("id"),porjectId));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return pmIterationentityRepository.findAll(sp, pageable);
    }

    @Override
    public boolean saveIteration(HashMap<String, String> map) {




        return false;
    }


}
