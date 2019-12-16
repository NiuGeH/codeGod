package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmProjectentityRepository extends JpaSpecificationExecutor<PmProjectEntity>, PagingAndSortingRepository<PmProjectEntity, Long>, JpaRepository<PmProjectEntity, Long> {

}
