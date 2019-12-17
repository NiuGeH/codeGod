package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationCaseRepository extends JpaSpecificationExecutor<OperationCaseEntity>, PagingAndSortingRepository<OperationCaseEntity, Long>, JpaRepository<OperationCaseEntity, Long> {

    @Query("select ce from OperationCaseEntity ce where ce.caseName= ?1")
    OperationCaseEntity findByCaseName(String name);

}
