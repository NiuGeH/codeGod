package com.springbootjpa.codeGod.repository.Operation;

import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OperationRegionRepository extends JpaSpecificationExecutor<OperationRegionEntity>, PagingAndSortingRepository<OperationRegionEntity, Long>, JpaRepository<OperationRegionEntity, Long> {

    OperationRegionEntity findByCityName(String cityName);

    @Query("select max(re.cityOrder) from OperationRegionEntity re")
    Long findMaxCityOrder();

    @Query("select re.cityName from OperationRegionEntity  re where re.cityName like ?1 and re.display= ?2")
    List<String > findAllByCityNameAndDisplay(String cityName,Integer display);

    /**
     * 排序字段中存在null 带上 - 号 ASC 改为 DESC
     * @param display 是否显示
     * @return List<OperationRegionEntity>
     */
    @Query("select re from OperationRegionEntity re where re.display= ?1 order by -re.cityOrder DESC ")
    List<OperationRegionEntity> findAllByDisplay(Integer display);

}
