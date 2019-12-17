package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 11:42
 */
public interface OperationRegionService {

    /**
     * 添加城市
     * @param name 城市名称
     * @param order 城市排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationRegionEntity addCity(String name, String order, Integer display);

    /**
     * 修改城市
     * @param oldName 城市原名称
     * @param newName 城市新名称
     * @param order 城市排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    OperationRegionEntity updateCity(String oldName, String newName, String order, Integer display);

    /**
     * 城市分页
     * @param pageable 分页
     * @return
     */
    Page<OperationRegionEntity> findAll(Pageable pageable);
}
