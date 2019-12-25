package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
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
import java.util.Optional;

/**
 * 需求
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmDemandServiceIml implements PmDemandService {
    @Autowired
    private PmDemandentityRepository pmDemandentityRepository;
    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 查询需求列表
     * @param pageable 分页参数
     * @return 返回需求
     */
    @Override
    public Page<PmDemandEntity> findAll(Pageable pageable) {
        Page<PmDemandEntity> all = pmDemandentityRepository.findAll(pageable);
        List<PmDemandEntity> list = new ArrayList<PmDemandEntity>();
        for (PmDemandEntity pmDemandEntity : all) {
            BaseDataDictionaryEntity value = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(pmDemandEntity.getDemandStatus().toString(), DataBaseFinal.PM_DEMANDDEMAND_STATUS);
            pmDemandEntity.setDemandStatus1(value.getDataValue());
            list.add(pmDemandEntity);
        }
        return new PageImpl<PmDemandEntity>(list, pageable,list.size());
    }

    /**
     * 拒单
     * @param demandRefusalCause 拒单原因
     * @param id    需求编号
     * @return   影响行数
     */
    @Override
    public int updateDemand(String demandRefusalCause, Long id,Integer status) {
        return pmDemandentityRepository.updateDemand(demandRefusalCause, id,status);
    }
    /**
     * 设置产品经理
     * @param productManagerId 产品经理编号
     * @param id    需求编号
     * @return  是否成功
     */
    @Override
    public boolean settingProjectManager(Long productManagerId, Long id,Integer status) {
        boolean falg = false;
        int i = pmDemandentityRepository.settingProjectManager(productManagerId, id,status);
        if(i>0){
            falg = true;
        }
        return falg;
    }
    /**
     * 根据ID查询单个需求
     * @param id
     * @return 单个对象
     */
    @Override
    public PmDemandEntity findOne(Long id) {
        Optional<PmDemandEntity> byId = pmDemandentityRepository.findById(id);
        return byId.get();
    }
}
