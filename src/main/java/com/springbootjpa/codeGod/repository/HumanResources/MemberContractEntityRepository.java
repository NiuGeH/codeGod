package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberContractEntityRepository extends JpaSpecificationExecutor<MemberContractEntity>, PagingAndSortingRepository<MemberContractEntity, Long>, JpaRepository<MemberContractEntity, Long> {

}
