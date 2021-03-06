package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationCaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/16 12:13
 */
public interface OperationCaseService {

    /**
     * 添加案例类型
     * @param name 案例名称
     * @param order 案例排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationCaseEntity addCase(String name, Long order, Integer display);

    /**
     * 修改案例类型
     * @param id 案例id
     * @param newName 案例新名称
     * @param order 案例排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationCaseEntity updateCase(Long id, String newName, Long order, Integer display);

    /**
     * 查询全部案例类型
     * @return
     */
    Page<OperationCaseEntity> findAll(Pageable pageable);
}
