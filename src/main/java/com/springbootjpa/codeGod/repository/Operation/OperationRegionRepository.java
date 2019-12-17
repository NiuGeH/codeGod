package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationRegionRepository extends JpaSpecificationExecutor<OperationRegionEntity>, PagingAndSortingRepository<OperationRegionEntity, Long>, JpaRepository<OperationRegionEntity, Long> {

    @Query("select re from OperationRegionEntity re where re.cityName= ?1")
    OperationRegionEntity findByCityName(String name);
}
