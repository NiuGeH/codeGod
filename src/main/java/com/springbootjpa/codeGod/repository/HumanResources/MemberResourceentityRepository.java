package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberResourceentityRepository extends JpaSpecificationExecutor<MemberResourceEentity>, PagingAndSortingRepository<MemberResourceEentity, Long>, JpaRepository<MemberResourceEentity, Long> {

}
