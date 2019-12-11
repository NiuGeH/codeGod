package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.humanResources.MemberCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberCaseentityRepository extends JpaSpecificationExecutor<MemberCaseEntity>, PagingAndSortingRepository<MemberCaseEntity, Long>, JpaRepository<MemberCaseEntity, Long> {

}
