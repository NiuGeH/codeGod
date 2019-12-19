package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmRecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PmRecruitmententityRepository extends JpaSpecificationExecutor<PmRecruitmentEntity>, PagingAndSortingRepository<PmRecruitmentEntity, Long>, JpaRepository<PmRecruitmentEntity, Long> {


    @Override
    Optional<PmRecruitmentEntity> findById(Long id);


}
