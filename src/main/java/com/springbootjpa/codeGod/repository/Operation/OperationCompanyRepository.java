package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationCompanyRepository extends JpaSpecificationExecutor<OperationCompanyEntity>, PagingAndSortingRepository<OperationCompanyEntity, Long>, JpaRepository<OperationCompanyEntity, Long> {

}
