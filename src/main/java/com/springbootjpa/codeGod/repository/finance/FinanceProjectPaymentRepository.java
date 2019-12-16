package com.springbootjpa.codeGod.repository.finance;

import com.springbootjpa.codeGod.entity.finance.FinanceProjectPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FinanceProjectPaymentRepository extends JpaSpecificationExecutor<FinanceProjectPaymentEntity>, PagingAndSortingRepository<FinanceProjectPaymentEntity, Long>, JpaRepository<FinanceProjectPaymentEntity, Long> {

}
