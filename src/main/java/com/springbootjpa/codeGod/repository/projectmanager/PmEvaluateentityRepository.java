package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmEvaluateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmEvaluateentityRepository extends JpaSpecificationExecutor<PmEvaluateEntity>, PagingAndSortingRepository<PmEvaluateEntity, Long>, JpaRepository<PmEvaluateEntity, Long> {

}
