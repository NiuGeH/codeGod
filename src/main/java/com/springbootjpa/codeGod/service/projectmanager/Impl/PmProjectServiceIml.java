package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmProjectentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.Optional;

@Service
@Transactional(rollbackOn = Exception.class)
public class PmProjectServiceIml implements PmProjectService {
    @Autowired
    private PmProjectentityRepository pmProjectentityRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;
    @Override
    public Page<PmProjectEntity> findAll() {
        return null;
    }

    /**
     * 获取产品经理下的项目
     * @param projectManagerId 产品经理ID
     * @return 项目数量
     */
    @Override
    public int getCount(Long projectManagerId) {
        return pmProjectentityRepository.findAllByProjectManagerId(projectManagerId);
    }

    /**
     *
     * @param pmProjectEntity
     * @return
     */
    @Override
    public boolean saveProject(PmProjectEntity pmProjectEntity){
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectName())){
//
//        }
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectAdderss())){
//
//        }
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectIntroduce())){
//
//        }
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectKeyword())){
//
//        }
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectPeriod())){
//
//        }
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectRemark())){
//
//        }
//        if(StringUtils.isEmpty(pmProjectEntity.getProjectBudget().toString())){
//
//        }
        PmProjectEntity save = pmProjectentityRepository.save(pmProjectEntity);
        if(save!=null){
            return true;
        }
        return false;
    }
    /**
     * 项目查询
     * @param pageable 分页
     * @param projectStuats 项目状态
     * @return  返回项目列表
     */
    @Override
    public Page<PmProjectEntity> doPage(Pageable pageable,Integer projectStuats) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(!StringUtils.isEmpty(projectStuats.toString())){
                    list.add(criteriaBuilder.equal(root.get("projectStatus"),projectStuats.toString()));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<PmProjectEntity> all = pmProjectentityRepository.findAll(sp, pageable);
        ArrayList<PmProjectEntity> list = new ArrayList<>();
        for (PmProjectEntity pmProjectEntity:all){
            BaseDataDictionaryEntity value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmProjectEntity.getProjectStatus().toString(), "pm_project.project_status");
            pmProjectEntity.setProjectStatus1(value.getDataValue());
            list.add(pmProjectEntity);
        }
        return  new PageImpl<PmProjectEntity>(list, pageable,list.size());
    }

    /**
     * 单个项目数据
     * @param id 项目iD
     * @return 项目信息
     */
    @Override
    public PmProjectEntity findOne(Long id) {
        Optional<PmProjectEntity> byId = pmProjectentityRepository.findById(id);
        return byId.get();
    }


}
