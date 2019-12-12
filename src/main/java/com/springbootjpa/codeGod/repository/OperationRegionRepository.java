package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationRegionRepository extends JpaSpecificationExecutor<OperationRegionEntity>, PagingAndSortingRepository<OperationRegionEntity, Long>, JpaRepository<OperationRegionEntity, Long> {

}
