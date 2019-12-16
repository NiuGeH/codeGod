package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface BaseDataDictionaryentityRepository extends JpaSpecificationExecutor<BaseDataDictionaryEntity>, PagingAndSortingRepository<BaseDataDictionaryEntity, Long>, JpaRepository<BaseDataDictionaryEntity, Long> {

    @Query("select b from BaseDataDictionaryEntity b where b.dataColumnName=:colum_name and b.dataKey=:key" )
    public BaseDataDictionaryEntity findDistinctByDataColumnNameAndAndDataKey(@Param("key") String key, @Param("colum_name") String colum_name);

    @Query("select b from BaseDataDictionaryEntity b where b.dataColumnName=:colum_name ")
    public List<BaseDataDictionaryEntity> findBaseDataDictionaryEntityByDataColumnName(@Param("colum_name") String dataColumnName);
}
