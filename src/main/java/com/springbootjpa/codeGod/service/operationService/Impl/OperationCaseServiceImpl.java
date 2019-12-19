package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationCaseEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationCaseRepository;
import com.springbootjpa.codeGod.service.operationService.OperationCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/16 12:26
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationCaseServiceImpl implements OperationCaseService {

    @Autowired
    private OperationCaseRepository operationCaseRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加案例类型
     * @param name 案例名称
     * @param order 案例排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationCaseEntity addCase(String name, Long order, Integer display) {

        //判断类型是否已存在
        OperationCaseEntity ce = operationCaseRepository.findByCaseName(name);
        if(!ObjectUtils.isEmpty(ce)){
            throw new CodeGodRunTimExcetion("该案例类型已存在", this.getClass());
        }

        //添加新案例类型
        OperationCaseEntity caseEntity = new OperationCaseEntity();
        caseEntity.setCaseName(name);
        caseEntity.setCaseOrder(order);
        caseEntity.setDisplay(display);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        caseEntity.setCreateTime(now);
        log.info("添加的新案例类型：" + caseEntity.toString());
        operationCaseRepository.save(caseEntity);

        return caseEntity;
    }

    /**
     * 修改案例类型
     * @param id 案例id
     * @param newName 案例新名称
     * @param order 案例排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationCaseEntity updateCase(Long id, String newName, Long order, Integer display) {
        //查询需要修改的案例类型
        OperationCaseEntity caseEntity = operationCaseRepository.getOne(id);
        log.info("案例类型修改前：" + caseEntity.toString());

        //修改该案例类型属性
        caseEntity.setCaseName(newName);
        caseEntity.setCaseOrder(order);
        caseEntity.setDisplay(display);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        caseEntity.setModifyTime(now);
        log.info("案例类型修改后：" + caseEntity.toString());

        //保存修改后的案例
        operationCaseRepository.save(caseEntity);

        return caseEntity;
    }

    /**
     * 案例类型分页
     * @param pageable 分页
     * @return
     */
    @Override
    public Page<OperationCaseEntity> findAll(Pageable pageable) {
        Page<OperationCaseEntity> all = operationCaseRepository.findAll(pageable);
        List<OperationCaseEntity> list = new ArrayList<>();
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationCaseEntity operationCaseEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationCaseEntity.getDisplay().toString(), DataBaseFinal.OPERATIONCASE_DISPLAY);
                operationCaseEntity.setDisplayStr(bdd.getDataValue());
                list.add(operationCaseEntity);
            }
        }
        return new PageImpl<OperationCaseEntity>(list);
    }


}
