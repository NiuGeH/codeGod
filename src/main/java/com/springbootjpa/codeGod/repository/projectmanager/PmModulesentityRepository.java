package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmModulesentityRepository extends JpaSpecificationExecutor<PmModulesEntity>, PagingAndSortingRepository<PmModulesEntity, Long>, JpaRepository<PmModulesEntity, Long> {

}
