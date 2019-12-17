package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperationMedalRepository extends JpaSpecificationExecutor<OperationMedalEntity>, PagingAndSortingRepository<OperationMedalEntity, Long>, JpaRepository<OperationMedalEntity, Long> {
    /**
     * 查找未删除的勋章
     * @param state 0 未删除 1删除
     * @return List<OperationMedalEntity>
     */
    List<OperationMedalEntity> findAllByState(Integer state);
}
