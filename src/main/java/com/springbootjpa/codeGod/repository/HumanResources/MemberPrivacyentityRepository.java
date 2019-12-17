package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberPrivacyentityRepository extends JpaSpecificationExecutor<MemberPrivacyEntity>, PagingAndSortingRepository<MemberPrivacyEntity, Long>, JpaRepository<MemberPrivacyEntity,Long> {

    MemberPrivacyEntity findAllByMemberId(Long memberId);
}
