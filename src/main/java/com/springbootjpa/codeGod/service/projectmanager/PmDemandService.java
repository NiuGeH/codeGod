package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface PmDemandService {
    Page<PmDemandEntity> findAll(Pageable pageable);

    int updateDemand(String demandRefusalCause,Long id,Integer Status);

    boolean settingProjectManager(Long productManagerId,Long id,Integer Status);

    PmDemandEntity findOne(Long id);

}
