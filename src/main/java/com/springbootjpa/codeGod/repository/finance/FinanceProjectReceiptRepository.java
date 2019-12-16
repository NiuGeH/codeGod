package com.springbootjpa.codeGod.repository.finance;

import com.springbootjpa.codeGod.entity.finance.FinanceProjectReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FinanceProjectReceiptRepository extends JpaSpecificationExecutor<FinanceProjectReceiptEntity>, PagingAndSortingRepository<FinanceProjectReceiptEntity, Long>, JpaRepository<FinanceProjectReceiptEntity, Long> {

}
