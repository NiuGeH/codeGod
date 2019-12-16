package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmApplicationentityRepository extends JpaSpecificationExecutor<PmApplicationEntity>, PagingAndSortingRepository<PmApplicationEntity, Long>, JpaRepository<PmApplicationEntity, Long> {

}
