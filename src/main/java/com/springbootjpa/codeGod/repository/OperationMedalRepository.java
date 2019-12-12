package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationMedalRepository extends JpaSpecificationExecutor<OperationMedalEntity>, PagingAndSortingRepository<OperationMedalEntity, Long>, JpaRepository<OperationMedalEntity, Long> {

}
