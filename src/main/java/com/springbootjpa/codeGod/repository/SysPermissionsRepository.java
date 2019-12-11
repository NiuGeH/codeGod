package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.sys.SysPermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SysPermissionsRepository extends JpaSpecificationExecutor<SysPermissionsEntity>, PagingAndSortingRepository<SysPermissionsEntity, Long>, JpaRepository<SysPermissionsEntity,Long> {
}
