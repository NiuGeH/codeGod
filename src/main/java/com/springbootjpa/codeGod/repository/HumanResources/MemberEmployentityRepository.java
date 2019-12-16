package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberEmployEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberEmployentityRepository extends JpaSpecificationExecutor<MemberEmployEntity>, PagingAndSortingRepository<MemberEmployEntity, Long>, JpaRepository<MemberEmployEntity, Long> {

}
