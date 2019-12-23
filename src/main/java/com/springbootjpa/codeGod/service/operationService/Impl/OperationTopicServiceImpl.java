package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationTopicEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationTopicRepository;
import com.springbootjpa.codeGod.service.operationService.OperationTopicService;
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
import java.util.Date;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 13:40
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationTopicServiceImpl implements OperationTopicService {

    @Autowired
    private OperationTopicRepository operationTopicRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加栏目
     * @param name 栏目名称
     * @param order 栏目排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationTopicEntity addTopic(String name, Long order, Integer display) {
        if(ObjectUtils.isEmpty(name)){
            throw new CodeGodRunTimExcetion("栏目名称不能为空", this.getClass());
        }
        //判断栏目是否已存在
        OperationTopicEntity te = operationTopicRepository.findByTopicName(name);
        if(!ObjectUtils.isEmpty(te)){
            throw new CodeGodRunTimExcetion("该栏目已存在", this.getClass());
        }

        //添加新栏目
        OperationTopicEntity topic = new OperationTopicEntity();
        topic.setTopicName(name);
        if(ObjectUtils.isEmpty(order)){
            topic.setTopicOrder(operationTopicRepository.findMaxTopicOrder()+1);
        }else {
            topic.setTopicOrder(order);
        }
        if(ObjectUtils.isEmpty(display)){
            topic.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            topic.setDisplay(display);
        }


        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        topic.setCreateTime(now);
        log.info("添加的新栏目：" + topic.toString());
        operationTopicRepository.save(topic);

        return topic;
    }

    /**
     * 修改栏目
     * @param id 栏目id
     * @param newName 栏目新名称
     * @param order 栏目排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationTopicEntity updateTopic(Long id, String newName, Long order, Integer display) {
        //查询需要修改的栏目
        if(ObjectUtils.isEmpty(id)){
            throw new CodeGodRunTimExcetion("参数id不能为空", this.getClass());
        }
        OperationTopicEntity topic = operationTopicRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("参数id错误，未查到匹配栏目",this.getClass()));
        log.info("栏目修改前：" + topic.toString());

        //修改该栏目属性
        if (!ObjectUtils.isEmpty(newName)) {
            topic.setTopicName(newName);
        }
        if (!ObjectUtils.isEmpty(order)) {
            topic.setTopicOrder(order);
        }
        if (!ObjectUtils.isEmpty(display)) {
            topic.setDisplay(display);
        }
        topic.setModifyTime(Calendar.getInstance().getTime());
        log.info("栏目修改后：" + topic.toString());

        //保存修改后的栏目
        operationTopicRepository.save(topic);

        return topic;
    }

    /**
     * 查询全部栏目
     * @return
     */
    @Override
    public Page<OperationTopicEntity> findAll(Pageable pageable) {
        Specification<OperationTopicEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<OperationTopicEntity> all = operationTopicRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationTopicEntity operationTopicEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationTopicEntity.getDisplay().toString(), DataBaseFinal.OPERATION_TOPIC_DISPLAY);
                operationTopicEntity.setDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }
}
