package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberEmployPersonnelentityRepository extends JpaSpecificationExecutor<MemberEmployPersonnelEntity>, PagingAndSortingRepository<MemberEmployPersonnelEntity, Long>, JpaRepository<MemberEmployPersonnelEntity, Long> {

}
