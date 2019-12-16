package com.springbootjpa.codeGod.repository.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmSettleAccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmSettleAccountsentityRepository extends JpaSpecificationExecutor<PmSettleAccountsEntity>, PagingAndSortingRepository<PmSettleAccountsEntity, Long>, JpaRepository<PmSettleAccountsEntity, Long> {

}
