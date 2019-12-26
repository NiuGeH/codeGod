package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MemberResourceentityRepository extends JpaSpecificationExecutor<MemberResourceEentity>, PagingAndSortingRepository<MemberResourceEentity, Long>, JpaRepository<MemberResourceEentity, Long> {

    MemberResourceEentity findAllByMemberIdAndMemberOperationResource(Long memberId, OperationResourceEntity memberOperationResource);

    List<MemberResourceEentity> findAllByMemberId(Long memberId);
}
