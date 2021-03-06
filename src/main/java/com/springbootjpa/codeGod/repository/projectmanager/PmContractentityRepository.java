package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmContractentityRepository extends JpaSpecificationExecutor<PmContractEntity>, PagingAndSortingRepository<PmContractEntity, Long>, JpaRepository<PmContractEntity, Long> {

}
