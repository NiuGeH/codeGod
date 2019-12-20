package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationTopicEntity;
import org.springframework.data.domain.Page;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 13:37
 */
public interface OperationTopicService {

    /**
     * 添加栏目
     * @param name 栏目名称
     * @param order 栏目排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationTopicEntity addTopic(String name, Long order, Integer display);

    /**
     * 修改栏目
     * @param id 栏目id
     * @param newName 栏目新名称
     * @param order 栏目排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationTopicEntity updateTopic(Long id, String newName, Long order, Integer display);

    /**
     * 查询全部栏目
     * @return
     */
    Page<OperationTopicEntity> findAll();
}
