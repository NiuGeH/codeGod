package com.springbootjpa.codeGod.repository.finance;

import com.springbootjpa.codeGod.entity.finance.FinanceCommissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FinanceCommissionRepository extends JpaSpecificationExecutor<FinanceCommissionEntity>, PagingAndSortingRepository<FinanceCommissionEntity, Long>, JpaRepository<FinanceCommissionEntity, Long> {

}
