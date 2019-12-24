package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.entity.operation.OperationCodeGodDataEntity;
import com.springbootjpa.codeGod.repository.Operation.OperationCodeGodDataRepository;
import com.springbootjpa.codeGod.service.operationService.OperationCodeGodDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/24 17:10
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationCodeGodDataServiceImpl implements OperationCodeGodDataService {

    @Autowired
    private OperationCodeGodDataRepository operationCodeGodDataRepository;

    /**
     * 设置马神资料
     * @param amountProject 已完成项目数量
     * @param grossAccount 累计合同金额
     * @param amountSignContract 签约码神数量
     * @param amountTeam 全国服务团队数量
     * @return
     */
    @Override
    public OperationCodeGodDataEntity setCodeGodData(Long amountProject, Long grossAccount, Long amountSignContract, Long amountTeam) {
        OperationCodeGodDataEntity codeGodDataEntity = new OperationCodeGodDataEntity();
        List<OperationCodeGodDataEntity> list = operationCodeGodDataRepository.findAll();
        if(!ObjectUtils.isEmpty(list) && list.size()>0){
            codeGodDataEntity = operationCodeGodDataRepository.findById(1L).get();
        }
        codeGodDataEntity.setAmountProject(amountProject);
        codeGodDataEntity.setGrossAccount(grossAccount);
        codeGodDataEntity.setAmountSignContract(amountSignContract);
        codeGodDataEntity.setAmountTeam(amountTeam);
        codeGodDataEntity.setModifyTime(Calendar.getInstance().getTime());

        operationCodeGodDataRepository.save(codeGodDataEntity);

        return codeGodDataEntity;
    }

    /**
     * 查询码神资料
     * @return
     */
    @Override
    public OperationCodeGodDataEntity findAll() {
        return operationCodeGodDataRepository.findAll().get(0);
    }
}
