package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationSkillRepository;
import com.springbootjpa.codeGod.service.operationService.OperationSkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/23 16:22
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationSkillServiceImpl implements OperationSkillService {

    @Autowired
    private OperationSkillRepository operationSkillRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加技术栈
     * @param name 技术栈名称
     * @param order 技术栈排序
     * @param display 是否显示：0是，1否
     * @return
     */
    @Override
    public OperationSkillEntity addSkill(String name, Long order, Integer display) {
        if(ObjectUtils.isEmpty(name)){
            throw new CodeGodRunTimExcetion("技术栈名称不能为空", this.getClass());
        }

        //判断是否已存在
        OperationSkillEntity se = operationSkillRepository.findBySkillName(name);
        if(!ObjectUtils.isEmpty(se)){
            throw new CodeGodRunTimExcetion("该技术栈已存在", this.getClass());
        }

        //添加
        OperationSkillEntity skillEntity = new OperationSkillEntity();
        skillEntity.setSkillName(name);
        if(ObjectUtils.isEmpty(order)){
            skillEntity.setSkillOrder(operationSkillRepository.findMaxSkillOrder()+1);
        }else {
            skillEntity.setSkillOrder(order);
        }
        if(ObjectUtils.isEmpty(display)){
            skillEntity.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            skillEntity.setDisplay(display);
        }
        skillEntity.setCreateTime(Calendar.getInstance().getTime());
        log.info("添加的技术栈：" + skillEntity.toString());

        //保存
        operationSkillRepository.save(skillEntity);

        return skillEntity;
    }

    /**
     * 修改技术栈
     * @param id 被修改的技术栈id
     * @param newName 新的技术栈名称
     * @param order 技术栈排序
     * @param display 是否显示：0是，1否
     * @return
     */
    @Override
    public OperationSkillEntity updateSkill(Long id, String newName, Long order, Integer display) {
        //参数验证
        if(ObjectUtils.isEmpty(id)){
            throw new CodeGodRunTimExcetion("技术栈id不能为空", this.getClass());
        }
        //查询需要修改的技术栈
        OperationSkillEntity skillEntity = operationSkillRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("技术栈id错误，未查到技术栈",this.getClass()));
        log.info("技术栈修改前：" + skillEntity.toString());

        //修改
        if (!ObjectUtils.isEmpty(newName)) {
            OperationSkillEntity se = operationSkillRepository.findBySkillName(newName);
            if(id != se.getId()) throw new CodeGodRunTimExcetion("该技术栈名称已存在",this.getClass());
            skillEntity.setSkillName(newName);
        }
        if (!ObjectUtils.isEmpty(order)) {
            skillEntity.setSkillOrder(order);
        }
        if (!ObjectUtils.isEmpty(display)) {
            skillEntity.setDisplay(display);
        }
        skillEntity.setModifyTime(Calendar.getInstance().getTime());
        log.info("技术栈修改后：" + skillEntity.toString());

        //保存
        operationSkillRepository.save(skillEntity);

        return skillEntity;
    }

    /**
     * 查询全部技术栈分页
     * @param pageable
     * @return
     */
    @Override
    public Page<OperationSkillEntity> findAll(Pageable pageable) {
        Specification<OperationSkillEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<OperationSkillEntity> all = operationSkillRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationSkillEntity operationSkillEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationSkillEntity.getDisplay().toString(), DataBaseFinal.OPERATION_SKILL_DISPLAY);
                operationSkillEntity.setDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }

    /**
     * 查询全部技术栈id-name，供添加资源类型时调用
     * @return
     */
    @Override
    public Map<Long, String> findAll() {
        List<OperationSkillEntity> list = operationSkillRepository.findAll();
        Map<Long, String> map = new HashMap<>();
        if(!ObjectUtils.isEmpty(list) && list.size()>0){
            for(OperationSkillEntity skillEntity : list){
                map.put(skillEntity.getId(),skillEntity.getSkillName());
            }
        }
        return map;
    }
}
