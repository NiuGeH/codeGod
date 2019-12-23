package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationCommentRepository extends JpaSpecificationExecutor<OperationCommentEntity>, PagingAndSortingRepository<OperationCommentEntity, Long>, JpaRepository<OperationCommentEntity, Long> {

    OperationCommentEntity findByIdAndState(Long id, Integer state);
}
