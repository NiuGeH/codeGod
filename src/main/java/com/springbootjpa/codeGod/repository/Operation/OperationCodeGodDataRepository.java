package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationCodeGodDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationCodeGodDataRepository extends JpaSpecificationExecutor<OperationCodeGodDataEntity>, PagingAndSortingRepository<OperationCodeGodDataEntity, Long>, JpaRepository<OperationCodeGodDataEntity, Long> {

}
