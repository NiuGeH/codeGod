package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationNewsRepository extends JpaSpecificationExecutor<OperationNewsEntity>, PagingAndSortingRepository<OperationNewsEntity, Long>, JpaRepository<OperationNewsEntity, Long> {

    OperationNewsEntity findByTitle(String title);

    OperationNewsEntity findByIdAndState(Long id, Integer state);
}
