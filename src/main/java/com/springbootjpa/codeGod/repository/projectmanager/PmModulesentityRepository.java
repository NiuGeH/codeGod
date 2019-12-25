package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface PmModulesentityRepository extends JpaSpecificationExecutor<PmModulesEntity>, PagingAndSortingRepository<PmModulesEntity, Long>, JpaRepository<PmModulesEntity, Long> {
    @Override
    Optional<PmModulesEntity> findById(Long id);

    @Query(value = "select * from pm_modules wehre technology_stack = :technologyStack and project_id = :projectId and  module_status = 0 and status = 0",nativeQuery = true)
    List<PmModulesEntity> findAllByTechnologyStack(@Param("technologyStack") Integer technologyStack,@Param("projectId") Long projectId);


    @Transactional
    @Query(value = "update pm_modules set team_id = :teamId,status = 1  where id = :id",nativeQuery = true)
    int updateModules(@Param("id") Long modulesId,@Param("teamId") Long teamId);

    @Query(value = "select b from pm_modules b where b.team_id = :teamId",nativeQuery = true)
    List<PmModulesEntity> findByTeamId(Long teamId);

    @Query(value = "select b from pm_modules b where project_id = :projectId",nativeQuery = true)
    List<PmModulesEntity> findByProjectId(@Param("projectId") Long project);

}
