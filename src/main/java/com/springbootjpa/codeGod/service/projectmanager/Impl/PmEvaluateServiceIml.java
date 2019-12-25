package com.springbootjpa.codeGod.service.projectmanager.Impl;

import com.springbootjpa.codeGod.entity.projectmanager.PmEvaluateEntity;
import com.springbootjpa.codeGod.repository.projectmanager.PmEvaluateentityRepository;
import com.springbootjpa.codeGod.service.projectmanager.PmEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 个人评价
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class PmEvaluateServiceIml implements PmEvaluateService {


    @Autowired
    private PmEvaluateentityRepository pmEvaluateentityRepository;
    /**
     * 保存个人评价
     * @param pmEvaluateEntity
     * @return
     */
    @Override
    public boolean saveEvalute(PmEvaluateEntity pmEvaluateEntity) {
        PmEvaluateEntity save = pmEvaluateentityRepository.save(pmEvaluateEntity);
        if(save!=null){
            return  true;
        }
        return false;
    }
}
