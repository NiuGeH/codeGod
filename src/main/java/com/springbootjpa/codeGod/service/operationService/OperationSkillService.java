package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/23 16:19
 */
public interface OperationSkillService {

    /**
     * 添加技术栈
     * @param name 技术栈名称
     * @param order 技术栈排序
     * @param display 是否显示：0是，1否
     * @return
     */
    OperationSkillEntity addSkill(String name, Long order, Integer display);

    /**
     * 修改技术栈
     * @param id 被修改的技术栈id
     * @param newName 新的技术栈名称
     * @param order 技术栈排序
     * @param display 是否显示：0是，1否
     * @return
     */
    OperationSkillEntity updateSkill(Long id, String newName, Long order, Integer display);

    /**
     * 查询全部技术栈分页
     * @param pageable
     * @return
     */
    Page<OperationSkillEntity> findAll(Pageable pageable);
}
