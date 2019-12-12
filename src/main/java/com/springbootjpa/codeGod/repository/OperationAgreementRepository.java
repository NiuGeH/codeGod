package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.operation.OperationAgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationAgreementRepository extends JpaSpecificationExecutor<OperationAgreementEntity>, PagingAndSortingRepository<OperationAgreementEntity, Long>, JpaRepository<OperationAgreementEntity, Long> {

}
