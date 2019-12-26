package com.springbootjpa.codeGod.repository.HumanResources;

import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceSkillEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MemberResourceSkillentityRepository extends JpaSpecificationExecutor<MemberResourceSkillEntity>, PagingAndSortingRepository<MemberResourceSkillEntity, Long>, JpaRepository<MemberResourceSkillEntity, Long> {

    MemberResourceSkillEntity findAllBySkillIdAndMemberResourceId(OperationSkillEntity skillId, Long memberResourceId);

    @Query(value = "select mrs from MemberResourceSkillEntity mrs where mrs.memberResourceId=?1")
    List<MemberResourceSkillEntity> findAllByMemberResourceId(Long memberResourceId);
}
