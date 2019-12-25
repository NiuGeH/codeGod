package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmTeamEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.projectmanager.PmSalaryentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmSalaryService;
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
import java.util.List;
import java.util.Optional;

/**
 * 工资
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmSalaryServiceIml implements PmSalaryService {

    @Autowired
    private PmSalaryentityRepository pmSalaryentityRepository;

    /**
     * 保存团队人员工资
     * @param pmSalaryEntity
     * @return
     */
    @Override
    public boolean saveSalary(PmSalaryEntity pmSalaryEntity) {
        PmSalaryEntity save = pmSalaryentityRepository.save(pmSalaryEntity);
        if(save!=null){
            return true;
        }
        return false;
    }



    /**
     * 根据ID删除人员工资
     * @param id
     * @return
     */
    @Override
    public boolean deleteSalary(Long id) {
        int i = pmSalaryentityRepository.updateSalary(id);
        if(i>0){
            return true;
        }
        return false;
    }

    /**
     * 查询工资信息
     * @param pageable
     * @param pmSalaryEntity
     * @return
     */
    @Override
    public Page<PmSalaryEntity> findOne(Pageable pageable,PmSalaryEntity pmSalaryEntity) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("pmTeamEntity").get("id"),pmSalaryEntity.getTeamId()));
                list.add(criteriaBuilder.equal(root.get("salaryStatus"),1));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return  pmSalaryentityRepository.findAll(sp,pageable);
    }

    /**
     * 查询单个
     * @param id
     * @return
     */
    @Override
    public PmSalaryEntity findOne(Long id) {
        Optional<PmSalaryEntity> byId = pmSalaryentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该工资单不存在！",this.getClass());
        }
        return byId.get();
    }


}
