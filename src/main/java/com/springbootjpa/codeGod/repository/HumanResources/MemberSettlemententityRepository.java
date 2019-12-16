package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberSettlementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface MemberSettlemententityRepository extends JpaSpecificationExecutor<MemberSettlementEntity>, PagingAndSortingRepository<MemberSettlementEntity, Long>, JpaRepository<MemberSettlementEntity, Long> {

}
