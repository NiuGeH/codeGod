package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationTopicEntity;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationTopicRepository;
import com.springbootjpa.codeGod.service.operationService.OperationTopicService;
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
    public OperationTopicEntity addTopic(String name, String order, Integer display) {
        //判断栏目是否已存在
        OperationTopicEntity te = operationTopicRepository.findByTopicName(name);
        if(!ObjectUtils.isEmpty(te)){
            throw new CodeGodRunTimExcetion("该栏目已存在", this.getClass());
        }

        //添加新栏目
        OperationTopicEntity topic = new OperationTopicEntity();
        topic.setTopicName(name);
        topic.setTopicOrder(order);
        topic.setDisplay(display);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        topic.setCreateTime(now);
        log.info("添加的新栏目：" + topic.toString());
        operationTopicRepository.save(topic);

        return topic;
    }

    /**
     * 修改栏目
     * @param oldName 栏目原名称
     * @param newName 栏目新名称
     * @param order 栏目排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationTopicEntity updateTopic(String oldName, String newName, String order, Integer display) {
        //查询需要修改的栏目
        OperationTopicEntity topic = operationTopicRepository.findByTopicName(oldName);
        log.info("栏目修改前：" + topic.toString());

        //修改该栏目属性
        topic.setTopicName(newName);
        topic.setTopicOrder(order);
        topic.setDisplay(display);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        topic.setModifyTime(now);
        log.info("栏目修改后：" + topic.toString());

        //保存修改后的栏目
        operationTopicRepository.save(topic);

        return topic;
    }

    /**
     * 栏目分页
     * @param pageable 分页
     * @return
     */
    @Override
    public Page<OperationTopicEntity> findAll(Pageable pageable) {
        Page<OperationTopicEntity> all = operationTopicRepository.findAll(pageable);
        List<OperationTopicEntity> list = new ArrayList<>();
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationTopicEntity operationTopicEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationTopicEntity.getDisplay().toString(), "operation_topic.display");
                operationTopicEntity.setDisplayStr(bdd.getDataValue());
                list.add(operationTopicEntity);
            }
        }
        return new PageImpl<OperationTopicEntity>(list);
    }
}
