package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationPersonalMenuRepository extends JpaSpecificationExecutor<OperationPersonalMenuEntity>, PagingAndSortingRepository<OperationPersonalMenuEntity, Long>, JpaRepository<OperationPersonalMenuEntity, Long> {

}
