package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationNewsRepository extends JpaSpecificationExecutor<OperationNewsEntity>, PagingAndSortingRepository<OperationNewsEntity, Long>, JpaRepository<OperationNewsEntity, Long> {

}
