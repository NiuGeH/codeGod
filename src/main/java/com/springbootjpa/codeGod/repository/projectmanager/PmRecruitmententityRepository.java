package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmRecruitmententityRepository extends JpaSpecificationExecutor<PmRecruitmentEntity>, PagingAndSortingRepository<PmRecruitmentEntity, Long>, JpaRepository<PmRecruitmentEntity, Long> {

}
