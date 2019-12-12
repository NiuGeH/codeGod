package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationTeamRepository extends JpaSpecificationExecutor<OperationTeamEntity>, PagingAndSortingRepository<OperationTeamEntity, Long>, JpaRepository<OperationTeamEntity, Long> {

}
