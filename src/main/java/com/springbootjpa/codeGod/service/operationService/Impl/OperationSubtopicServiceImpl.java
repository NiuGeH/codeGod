package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.operation.OperationSubtopicEntity;
import com.springbootjpa.codeGod.entity.operation.OperationTopicEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.repository.Operation.OperationSubtopicRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationTopicRepository;
import com.springbootjpa.codeGod.service.operationService.OperationSubtopicService;
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
 * @date 2019/12/17 16:13
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationSubtopicServiceImpl implements OperationSubtopicService {

    @Autowired
    private OperationSubtopicRepository operationSubtopicRepository;

    @Autowired
    private OperationTopicRepository operationTopicRepository;

    /**
     * 添加子栏目
     * @param name 子栏目名称
     * @param order 子栏目排序编号
     * @param topicId 所属栏目id
     * @param content 内容
     * @param url 跳转路径
     * @param urlState 是否跳转url：0不可以，1可以
     * @return
     */
    @Override
    public OperationSubtopicEntity addSubtopic(String name, Long order, Long topicId, String content, String url, Integer urlState) {
        if(ObjectUtils.isEmpty(name)){
            throw new CodeGodRunTimExcetion("子栏目名称不能为空", this.getClass());
        }
        if(ObjectUtils.isEmpty(topicId)){
            throw new CodeGodRunTimExcetion("所属栏目不能为空", this.getClass());
        }
        //判断该子栏目在所属栏目下是否存在
        OperationSubtopicEntity subtopicEntity = operationSubtopicRepository.findBySubtopicNameAndTopicId(name,topicId);
        if(!ObjectUtils.isEmpty(subtopicEntity)){
            throw new CodeGodRunTimExcetion("所属栏目中该子栏目已存在", this.getClass());
        }

        //添加新子栏目
        OperationSubtopicEntity subtopic = new OperationSubtopicEntity();
        subtopic.setSubtopicName(name);
        if(ObjectUtils.isEmpty(order)){
            subtopic.setSubtopicOrder(operationSubtopicRepository.findMaxSubtopicOrder(topicId)+1);
        }else {
            subtopic.setSubtopicOrder(order);
        }
        OperationTopicEntity topic = operationTopicRepository.getOne(topicId);
        subtopic.setTopic(topic);
        subtopic.setContent(content);
        subtopic.setUrl(url);
        if(ObjectUtils.isEmpty(urlState)){
            subtopic.setUrlState(OperationEnum.OPERATION_URL_STATE_NO.getIndex());
        }else {
            subtopic.setUrlState(urlState);
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        subtopic.setCreateTime(now);
        log.info("添加的新子栏目：" + subtopic.toString());
        operationSubtopicRepository.save(subtopic);

        return subtopic;
    }

    /**
     * 修改子栏目
     * @param id 子栏目id
     * @param newName 子栏目新名称
     * @param order 子栏目排序编号
     * @param topicId 所属栏目id
     * @param content 内容
     * @param url 跳转路径
     * @param urlState 是否跳转url：0不可以，1可以
     * @return
     */
    @Override
    public OperationSubtopicEntity updateSubtopic(Long id, String newName, Long order, Long topicId, String content, String url, Integer urlState) {
        if(ObjectUtils.isEmpty(id)){
            throw new CodeGodRunTimExcetion("子栏目id不能为空", this.getClass());
        }
        if(ObjectUtils.isEmpty(topicId)){
            throw new CodeGodRunTimExcetion("所属栏目id不能为空", this.getClass());
        }
        //查询需要修改的子栏目
        OperationSubtopicEntity subtopic = operationSubtopicRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("子栏目id错误，未查到匹配子栏目",this.getClass()));
        log.info("子栏目修改前：" + subtopic.toString());

        //修改子栏目属性
        if (!ObjectUtils.isEmpty(newName)) {
            OperationSubtopicEntity se = operationSubtopicRepository.findBySubtopicNameAndTopicId(newName,topicId);
            if(id != se.getId()) throw new CodeGodRunTimExcetion("所属栏目中该子栏目已存在",this.getClass());
            subtopic.setSubtopicName(newName);
        }
        if (!ObjectUtils.isEmpty(order)) {
            subtopic.setSubtopicOrder(order);
        }
        if(ObjectUtils.isEmpty(urlState)){
            subtopic.setUrlState(OperationEnum.OPERATION_URL_STATE_NO.getIndex());
        }else {
            subtopic.setUrlState(urlState);
        }
        OperationTopicEntity topic = operationTopicRepository.getOne(topicId);
        subtopic.setTopic(topic);
        subtopic.setContent(content);
        subtopic.setUrl(url);
        subtopic.setModifyTime(Calendar.getInstance().getTime());
        log.info("子栏目修改后：" + subtopic.toString());
        operationSubtopicRepository.save(subtopic);

        return subtopic;
    }

    /**
     * 子栏目分页
     * @param pageable 分页
     * @param topicId 所属栏目id
     * @return
     */
    @Override
    public Page<OperationSubtopicEntity> findAll(Pageable pageable, Long topicId) {
        Specification<OperationSubtopicEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate pre = null;
                if(!ObjectUtils.isEmpty(topicId)){
                    pre = criteriaBuilder.equal(root.get("topic").get("id"),topicId.toString());
                }
                return pre;
            }
        };
        return operationSubtopicRepository.findAll(specification, pageable);
    }
}
