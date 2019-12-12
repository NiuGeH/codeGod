package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationSubtopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationSubtopicRepository extends JpaSpecificationExecutor<OperationSubtopicEntity>, PagingAndSortingRepository<OperationSubtopicEntity, Long>, JpaRepository<OperationSubtopicEntity, Long> {

}
