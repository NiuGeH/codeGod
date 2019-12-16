package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmSalaryentityRepository extends JpaSpecificationExecutor<PmSalaryEntity>, PagingAndSortingRepository<PmSalaryEntity, Long>, JpaRepository<PmSalaryEntity, Long> {

}
