package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmIterationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmIterationentityRepository extends JpaSpecificationExecutor<PmIterationEntity>, PagingAndSortingRepository<PmIterationEntity, Long>, JpaRepository<PmIterationEntity, Long> {

}
