package com.springbootjpa.codeGod.service.projectmanager.Impl;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmTeamEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmModulesentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmTeamentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmModulesService;
import com.springbootjpa.codeGod.service.projectmanager.PmTeamService;
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
 * 团队
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmTeamServiceIml implements PmTeamService {


    @Autowired
    private PmTeamentityRepository pmTeamentityRepository;
    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;
    @Autowired
    private MemberentityRepository memberentityRepository;
    @Autowired
    private PmModulesentityRepository pmModulesentityRepository;
    @Autowired
    private PmModulesService modulesService;

    /**
     * 团队人员分页查询
     * @param pageable
     * @param projectId
     * @return
     */
    @Override
    public Page<PmTeamEntity> doPage(Pageable pageable, Long projectId) {
        Specification sp = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.equal(root.get("projectId"),projectId));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<PmTeamEntity> all = pmTeamentityRepository.findAll(sp, pageable);
        ArrayList<PmTeamEntity> list = new ArrayList<>();
        for (PmTeamEntity pmTeamEntity : all) {
            pmTeamEntity.getMemberEntity().setMemberSigningModeString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey((pmTeamEntity.getMemberEntity().getMemberSigningMode()).toString(), DataBaseFinal.MEMBER_MEMBERSIGNINGMODE).getDataValue());
            pmTeamEntity.setDutyString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getDuty().toString(),DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY).getDataValue());
            pmTeamEntity.setRoleString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getRole().toString(),DataBaseFinal.MEMBER_MEMBERSIGNINGPOST).getDataValue());
            pmTeamEntity.setStatusString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getStatus().toString(),DataBaseFinal.PM_TEAMSTATUS).getDataValue());
            list.add(pmTeamEntity);
        }
        return new PageImpl<PmTeamEntity>(list, pageable, list.size());
    }

    /**
     * 添加人员页面数据渲染
     * @param memberId
     * @param dutyName
     * @return
     */
    @Override
    public HashMap<String, Object> DataRendering(Long memberId, String dutyName,Long projectId) {
        HashMap<String,Object> map = new HashMap<>();
        //根据用户ID查询用户脚色
        Optional<MemberEntity> memberEntity = memberentityRepository.findById(memberId);
        if(ObjectUtils.isEmpty(memberEntity)){
            throw new CodeGodRunTimExcetion("用户不存在", this.getClass());
        }
        String[] as = memberEntity.get().getMemberSigningPost().split(",");
        String[] rulo = new String[]{};
        for (int i = 0; i < as.length; i++) {
            String value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(as[i], DataBaseFinal.MEMBERSIGNCONTRACT_SIGNVERIFICATIONCODE).getDataValue();
            rulo[i]+=value;
        }
        //驻场要求
        List<BaseDataDictionaryEntity> contractCourt = baseDataDictionaryentityRepository.findBaseDataDictionaryEntityByDataColumnName(DataBaseFinal.MEMBER_CONTRACTCONTRACT_COURT);
        //结算方式
        List<BaseDataDictionaryEntity> clearingForm = baseDataDictionaryentityRepository.findBaseDataDictionaryEntityByDataColumnName(DataBaseFinal.PM_TEAMCLEARING_FORM);
        //审核状态
        List<BaseDataDictionaryEntity> pmTeamStatus = baseDataDictionaryentityRepository.findBaseDataDictionaryEntityByDataColumnName(DataBaseFinal.PM_TEAMSTATUS);
        //未选择模块（根据模块分类和项目ID查询）
        List<PmModulesEntity> all = modulesService.findAllByTechnologyStack(dutyName, projectId);
        map.put("rulo",rulo);
        map.put("pmModulesEntity",all);
        map.put("contractCourt",contractCourt);
        map.put("clearingForm",clearingForm);
        map.put("pmTeamStatus",pmTeamStatus);
        return map;
    }

    /**
     * 保存团队信息
     * @param pmTeamEntity
     * @return
     */
    @Override
    public boolean saveTeam(PmTeamEntity pmTeamEntity) {
        //保存模块和人员
        int i = pmModulesentityRepository.updateModules(pmTeamEntity.getMemberEntity().getId(), pmTeamEntity.getId());
        PmTeamEntity save = pmTeamentityRepository.save(pmTeamEntity);
        if(save!=null){
            return true;
        }
        return false;
    }

    /**
     * 根据团队人员查询相关信息
     * @param teamId
     * @return
     */
    @Override
    public PmTeamEntity findOne(Long teamId) {
        PmTeamEntity pmTeamEntity = new PmTeamEntity();
        Optional<PmTeamEntity> byId = pmTeamentityRepository.findById(teamId);
        if(ObjectUtils.isEmpty(byId)){
            throw new CodeGodRunTimExcetion("该人员不存在",this.getClass());
        }
        List<PmModulesEntity> byTeamId = pmModulesentityRepository.findByTeamId(teamId);
        pmTeamEntity = byId.get();
        pmTeamEntity.setRoleString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getRole().toString(),DataBaseFinal.MEMBER_MEMBERSIGNINGPOST).getDataValue());
        pmTeamEntity.setDutyString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getDuty().toString(),DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY).getDataValue());
        pmTeamEntity.setClearingFormString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getClearingForm().toString(),DataBaseFinal.PM_TEAMCLEARING_FORM).getDataValue());
        pmTeamEntity.setSiteRequirementsString(baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmTeamEntity.getSiteRequirements().toString(),DataBaseFinal.MEMBER_CONTRACTCONTRACT_COURT).getDataValue());
//        pmTeamEntity.setPmModulesEntity(byTeamId);
        return pmTeamEntity;
    }


}
