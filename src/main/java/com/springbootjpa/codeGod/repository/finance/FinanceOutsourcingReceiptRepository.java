package com.springbootjpa.codeGod.repository.finance;

import com.springbootjpa.codeGod.entity.finance.FinanceOutsourcingReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FinanceOutsourcingReceiptRepository extends JpaSpecificationExecutor<FinanceOutsourcingReceiptEntity>, PagingAndSortingRepository<FinanceOutsourcingReceiptEntity, Long>, JpaRepository<FinanceOutsourcingReceiptEntity, Long> {

}
