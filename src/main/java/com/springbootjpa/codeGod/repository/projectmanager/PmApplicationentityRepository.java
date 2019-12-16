package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.service.projectmanager.PmApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PmApplicationentityRepository extends JpaSpecificationExecutor<PmApplicationEntity>, PagingAndSortingRepository<PmApplicationEntity, Long>, JpaRepository<PmApplicationEntity, Long> {
    @Query(value = "",nativeQuery = true)
    Page<PmApplicationService> doPageByDemandId(Pageable pageable, Long demandId);

}
