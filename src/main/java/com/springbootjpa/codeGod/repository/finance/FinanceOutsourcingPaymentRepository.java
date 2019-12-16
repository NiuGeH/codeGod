package com.springbootjpa.codeGod.repository.finance;

import com.springbootjpa.codeGod.entity.finance.FinanceOutsourcingPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FinanceOutsourcingPaymentRepository extends JpaSpecificationExecutor<FinanceOutsourcingPaymentEntity>, PagingAndSortingRepository<FinanceOutsourcingPaymentEntity, Long>, JpaRepository<FinanceOutsourcingPaymentEntity, Long> {

}
