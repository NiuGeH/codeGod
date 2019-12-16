package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmTeamentityRepository extends JpaSpecificationExecutor<PmTeamEntity>, PagingAndSortingRepository<PmTeamEntity, Long>, JpaRepository<PmTeamEntity, Long> {

}
