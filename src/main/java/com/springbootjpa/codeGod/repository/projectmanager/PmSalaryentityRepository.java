package com.springbootjpa.codeGod.repository.projectmanager;


import com.springbootjpa.codeGod.entity.projectmanager.PmSalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PmSalaryentityRepository extends JpaSpecificationExecutor<PmSalaryEntity>, PagingAndSortingRepository<PmSalaryEntity, Long>, JpaRepository<PmSalaryEntity, Long> {


    @Query(value = "update pm_salary set salary_status = 1  ",nativeQuery = true)
    @Transactional
    int updateSalary(@Param("id") Long id);

}
