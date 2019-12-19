package com.springbootjpa.codeGod.service.projectmanager.Impl;
import com.alibaba.druid.util.StringUtils;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmTeamEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmModulesentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmTeamentityRepository;
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

    @Override
    public HashMap<String, Object> DataRendering(Long memberId, String dutyName) {
        HashMap<String,Object> map = new HashMap<>();
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

        map.put("rulo",rulo);
            return null;
    }





}
