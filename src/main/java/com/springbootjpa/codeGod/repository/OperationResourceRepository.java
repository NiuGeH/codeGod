package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationResourceRepository extends JpaSpecificationExecutor<OperationResourceEntity>, PagingAndSortingRepository<OperationResourceEntity, Long>, JpaRepository<OperationResourceEntity, Long> {

}
