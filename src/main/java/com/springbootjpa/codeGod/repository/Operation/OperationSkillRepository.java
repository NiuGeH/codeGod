package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationSkillRepository extends JpaSpecificationExecutor<OperationSkillEntity>, PagingAndSortingRepository<OperationSkillEntity, Long>, JpaRepository<OperationSkillEntity, Long> {

}
