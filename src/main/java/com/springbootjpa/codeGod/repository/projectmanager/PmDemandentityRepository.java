package com.springbootjpa.codeGod.repository.projectmanager;

import com.springbootjpa.codeGod.entity.eunm.DemandStatus;
import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PmDemandentityRepository extends JpaSpecificationExecutor<PmDemandEntity>, PagingAndSortingRepository<PmDemandEntity, Long>, JpaRepository<PmDemandEntity, Long> {



    @Transactional
    @Modifying
    @Query(value = "update PmDemandEntity set demandRefusalCause = :demandRefusalCause,demandStatus = :status  where id=:id")
    int updateDemand(@Param("demandRefusalCause") String demandRefusalCause, @Param("id") Long id,@Param("status") Integer status);

    @Transactional
    @Modifying
    @Query(value = "update PmDemandEntity set memberEntity.id = :productManagerId,demandStatus = :status  where id=:id")
    int settingProjectManager(@Param("productManagerId") Long productManagerId, @Param("id") Long id,@Param("status") Integer status);

    @Override
    Optional<PmDemandEntity> findById(Long id);


}
