package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PmModulesentityRepository extends JpaSpecificationExecutor<PmModulesEntity>, PagingAndSortingRepository<PmModulesEntity, Long>, JpaRepository<PmModulesEntity, Long> {
    @Override
    Optional<PmModulesEntity> findById(Long id);
    @Query(value = "select * from pm_modules wehre technology_stack = :technologyStack",nativeQuery = true)
    List<PmModulesEntity> findAllByTechnologyStack(@Param("technologyStack") Integer technologyStack);


}
