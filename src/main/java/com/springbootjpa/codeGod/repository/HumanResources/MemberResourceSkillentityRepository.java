package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberResourceSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberResourceSkillentityRepository extends JpaSpecificationExecutor<MemberResourceSkillEntity>, PagingAndSortingRepository<MemberResourceSkillEntity, Long>, JpaRepository<MemberResourceSkillEntity, Long> {

}
