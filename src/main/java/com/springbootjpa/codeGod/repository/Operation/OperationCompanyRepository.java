package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperationCompanyRepository extends JpaSpecificationExecutor<OperationCompanyEntity>, PagingAndSortingRepository<OperationCompanyEntity, Long>, JpaRepository<OperationCompanyEntity, Long> {

    @Query("select oc from OperationCompanyEntity oc where oc.companyDisplay= ?1 ")
    List<OperationCompanyEntity> findAllByCompanyDispay(Integer companyDisplay);

    @Query("select oc.companyName from OperationCompanyEntity oc where oc.companyName like ?1 and oc.companyDisplay = ?2")
    List<String> findAllByCompanyNameAndCompanyDispay(String companyName,Integer companyDisplay);

    OperationCompanyEntity findAllByCompanyName(String companyName);
}
