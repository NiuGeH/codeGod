package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberentityRepository extends JpaSpecificationExecutor<MemberEntity>, PagingAndSortingRepository<MemberEntity, Long>, JpaRepository<MemberEntity,Long> {

}
