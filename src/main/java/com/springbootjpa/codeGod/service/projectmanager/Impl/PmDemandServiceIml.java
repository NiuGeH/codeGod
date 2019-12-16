package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.projectmanager.PmDemandentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class PmDemandServiceIml implements PmDemandService {
    @Autowired
    private PmDemandentityRepository pmDemandentityRepository;
    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    @Override
    public Page<PmDemandEntity> findAll(Pageable pageable) {
        Page<PmDemandEntity> all = pmDemandentityRepository.findAll(pageable);
        List<PmDemandEntity> list = new ArrayList<PmDemandEntity>();
        for (PmDemandEntity pmDemandEntity : all) {
            BaseDataDictionaryEntity value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmDemandEntity.getDemandStatus().toString(), "pm_demand.demand_status");
            pmDemandEntity.setDemandStatus1(value.getDataValue());
            list.add(pmDemandEntity);
        }
        return new PageImpl<PmDemandEntity>(list, pageable,list.size());
    }
    @Override
    public int updateDemand(String demandRefusalCause, Long id) {
        return pmDemandentityRepository.updateDemand(demandRefusalCause, id);
    }
}
