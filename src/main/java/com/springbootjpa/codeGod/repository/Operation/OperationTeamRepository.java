package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface OperationTeamRepository extends JpaSpecificationExecutor<OperationTeamEntity>, PagingAndSortingRepository<OperationTeamEntity, Long>, JpaRepository<OperationTeamEntity, Long> {

    List<OperationTeamEntity> findAllByState(Integer state);
}
