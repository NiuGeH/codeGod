package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.entity.projectmanager.PmRecruitmentEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmRecruitmententityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmRecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 招聘
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmRecruitmentServiceIml implements PmRecruitmentService {

    @Autowired
    private PmRecruitmententityRepository pmRecruitmententityRepository;


    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 招聘职务信息
     * @param pageable
     * @return
     */
    @Override
    public Page<PmRecruitmentEntity> dopage(Pageable pageable) {
        List<PmRecruitmentEntity> all = pmRecruitmententityRepository.findAll();
        ArrayList<PmRecruitmentEntity> list = new ArrayList<>();
        for(PmRecruitmentEntity pmRecruitmentEntity:all){
            String value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmRecruitmentEntity.getRecruitmentDuty().toString(), DataBaseFinal.PM_RECRUITMENTRECRUITMENT_DUTY).getDataValue();
            String value1 = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmRecruitmentEntity.getRecruitmentRole().toString(), DataBaseFinal.MEMBER_MEMBERSIGNINGPOST).getDataValue();
            pmRecruitmentEntity.setDuty(value);
            pmRecruitmentEntity.setRole(value1);
            list.add(pmRecruitmentEntity);
        }
        return new PageImpl<PmRecruitmentEntity>(list,pageable,list.size());
    }

    /**
     * 保存招聘信息
     * @param pmRecruitmentEntity
     * @return
     */
    @Override
    public boolean saveRecruitment(PmRecruitmentEntity pmRecruitmentEntity) {
        PmRecruitmentEntity save = pmRecruitmententityRepository.save(pmRecruitmentEntity);
        if(save!=null){
            return true;
        }
        return false;
    }
    /**
     * 根据ID查询单个对象
     * @param id
     * @return
     */
    @Override
    public PmRecruitmentEntity findOne(Long id) {
        Optional<PmRecruitmentEntity> pmRecruitmentEntity = pmRecruitmententityRepository.findById(id);
        return pmRecruitmentEntity.get();
    }


}
