package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationCaseRepository extends JpaSpecificationExecutor<OperationCaseEntity>, PagingAndSortingRepository<OperationCaseEntity, Long>, JpaRepository<OperationCaseEntity, Long> {

    OperationCaseEntity findByCaseName(String caseName);

    @Query("select max(ce.caseOrder) from OperationCaseEntity ce")
    Long findMaxCaseOrder();

}
