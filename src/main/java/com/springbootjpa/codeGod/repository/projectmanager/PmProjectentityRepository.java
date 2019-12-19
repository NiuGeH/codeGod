package com.springbootjpa.codeGod.repository.projectmanager;



import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.Optional;

@Repository
public interface PmProjectentityRepository extends JpaSpecificationExecutor<PmProjectEntity>, PagingAndSortingRepository<PmProjectEntity, Long>, JpaRepository<PmProjectEntity, Long> {

    @Query(value = "select count(1) from framework.pm_project where product_manager_id = :projectManagerId",nativeQuery = true)
    int findAllByProjectManagerId(@Param("projectManagerId") Long projectManagerId);


    @Override
    Optional<PmProjectEntity> findById(Long id);

    @Modifying
    @Transient
    @Query(value = "update pm_project set project_status = :projectStatus where id = :id ",nativeQuery = true)
    int updateProjectStatus(@Param("id") Long id,@Param("projectStatus") Integer projectStatus);

}
