package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationSubtopicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 15:50
 */
public interface OperationSubtopicService  {

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
    OperationSubtopicEntity addSubtopic(String name, Long order, Long topicId, String content, String url, Integer urlState);

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
    OperationSubtopicEntity updateSubtopic(Long id, String newName, Long order, Long topicId, String content, String url, Integer urlState);

    /**
     * 子栏目分页
     * @param pageable 分页
     * @param topicId 所属栏目id
     * @return
     */
    Page<OperationSubtopicEntity> findAll(Pageable pageable, Long topicId);
}
