package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationResourceSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationResourceSkillRepository extends JpaSpecificationExecutor<OperationResourceSkillEntity>, PagingAndSortingRepository<OperationResourceSkillEntity, Long>, JpaRepository<OperationResourceSkillEntity, Long> {

}
