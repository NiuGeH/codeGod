package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmDemandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmDemandentityRepository extends JpaSpecificationExecutor<PmDemandEntity>, PagingAndSortingRepository<PmDemandEntity, Long>, JpaRepository<PmDemandEntity, Long> {

}
