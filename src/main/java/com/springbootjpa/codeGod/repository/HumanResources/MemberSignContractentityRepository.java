package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberSignContractentityRepository extends JpaSpecificationExecutor<MemberSignContractEntity>, PagingAndSortingRepository<MemberSignContractEntity, Long>, JpaRepository<MemberSignContractEntity, Long> {

}
