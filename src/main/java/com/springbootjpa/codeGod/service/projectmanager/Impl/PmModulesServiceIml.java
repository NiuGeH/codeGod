package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmModulesentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmModulesService;
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
 * 模块
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmModulesServiceIml implements PmModulesService {
    @Autowired
    private PmModulesentityRepository pmModulesentityRepository;
    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 模块分页查询
     * @param pageable
     * @param modulesEntity
     * @return
     */
    @Override
    public Page<PmModulesEntity> doPage(Pageable pageable,PmModulesEntity modulesEntity) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("moduleStatus"),"0"));
                list.add(criteriaBuilder.equal(root.get("projectId"),modulesEntity.getPmProjectEntity().getId()));
                if(!StringUtils.isEmpty(modulesEntity.getRecruitmenString())){
                    modulesEntity.setRecruitmenId(Integer.valueOf(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataValue(modulesEntity.getRecruitmenString(), DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY).getDataKey()));
                    list.add(criteriaBuilder.equal(root.get("recruitmenId"),modulesEntity.getRecruitmenId()));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<PmModulesEntity> all = pmModulesentityRepository.findAll(sp, pageable);
        ArrayList<PmModulesEntity> list = new ArrayList<PmModulesEntity>();
        for (PmModulesEntity pmModulesEntity:all){
            String value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmModulesEntity.getModuleType().toString(), DataBaseFinal.PM_MODULESMODULE_TYPE).getDataValue();
            String value1 = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmModulesEntity.getTechnologyStack().toString(), DataBaseFinal.PM_MODULESTECHNOLOGY_STACK).getDataValue();
            pmModulesEntity.setModuleTypeString(value);
            pmModulesEntity.setTechnologyStackString(value1);
            list.add(pmModulesEntity);
        }
        return  new PageImpl<PmModulesEntity>(list, pageable,list.size());
    }

    /**
     * 保存模块信息
     * @param modulesEntity
     * @return
     */
    @Override
    public boolean saveModelus(PmModulesEntity modulesEntity) {
        PmModulesEntity save = pmModulesentityRepository.save(modulesEntity);
        if(save!=null) {
            return true;
        }
        return false;
    }

    /**
     * 根据ID查询单个信息
     * @param id
     * @return
     */
    @Override
    public PmModulesEntity findOne(Long id) {
        Optional<PmModulesEntity> byId = pmModulesentityRepository.findById(id);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该模块不存在",this.getClass());
        }
        return byId.get();
    }

    /**
     * 根据技术栈查询模块
     * @param technologyStackName 技术栈名称
     * @return
     */
    @Override
    public List<PmModulesEntity> findAllByTechnologyStack(String technologyStackName,Long projectId) {
        String dataKey = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(technologyStackName, DataBaseFinal.PM_MODULESTECHNOLOGY_STACK).getDataKey();
        return pmModulesentityRepository.findAllByTechnologyStack(Integer.valueOf(dataKey),projectId);
    }

    /**
     * 根据项目ID查询模块
     * @param projectId
     * @return
     */
    @Override
    public List<PmModulesEntity> findAllByProjectId(Long projectId) {
        return  pmModulesentityRepository.findByTeamId(projectId);
    }

    /**
     * 更新模块进度
     * @param map
     * @return
     */
    @Override
    public boolean updateModulesSchedule(HashMap<String, String> map) {
        for (HashMap.Entry<String,String> entry : map.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            System.out.println("key= " + entry.getKey() + " and value= "
                    + entry.getValue());
        }
        return false;
    }


}
