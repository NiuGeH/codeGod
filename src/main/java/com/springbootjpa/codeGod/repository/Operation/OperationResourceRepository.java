package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperationResourceRepository extends JpaSpecificationExecutor<OperationResourceEntity>, PagingAndSortingRepository<OperationResourceEntity, Long>, JpaRepository<OperationResourceEntity, Long> {
}
