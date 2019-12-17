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
    OperationCaseEntity addCase(String name, String order, Integer display);

    /**
     * 修改案例类型
     * @param oldName 案例原名称
     * @param newName 案例新名称
     * @param order 案例排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationCaseEntity updateCase(String oldName, String newName, String order, Integer display);

    /**
     * 案例类型分页
     * @param pageable 分页
     * @return
     */
    Page<OperationCaseEntity> findAll(Pageable pageable);
}
