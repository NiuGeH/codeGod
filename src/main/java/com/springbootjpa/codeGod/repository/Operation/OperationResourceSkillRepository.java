package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationResourceSkillEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperationResourceSkillRepository extends JpaSpecificationExecutor<OperationResourceSkillEntity>, PagingAndSortingRepository<OperationResourceSkillEntity, Long>, JpaRepository<OperationResourceSkillEntity, Long> {

    @Query("select ors from OperationResourceSkillEntity ors where ors.resource.display= ?1 and ors.skill.display= ?2 order by -ors.skill.skillOrder DESC")
    List<OperationResourceSkillEntity> findAllByResourceAnAndOrderBySkill(Integer resourceDisplay,Integer skillDisplay);
}
