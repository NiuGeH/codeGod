package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PmRatingentityRepository extends JpaSpecificationExecutor<PmRatingEntity>, PagingAndSortingRepository<PmRatingEntity, Long>, JpaRepository<PmRatingEntity, Long> {

}
