package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmRepairOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmRepairOrderentityRepository extends JpaSpecificationExecutor<PmRepairOrderEntity>, PagingAndSortingRepository<PmRepairOrderEntity, Long>, JpaRepository<PmRepairOrderEntity, Long> {

}
