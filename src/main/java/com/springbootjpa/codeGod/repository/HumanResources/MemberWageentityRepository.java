package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberWageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberWageentityRepository extends JpaSpecificationExecutor<MemberWageEntity>, PagingAndSortingRepository<MemberWageEntity, Long>, JpaRepository<MemberWageEntity, Long> {

}
