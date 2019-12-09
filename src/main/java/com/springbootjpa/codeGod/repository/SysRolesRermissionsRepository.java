package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.SysRolesEntity;
import com.springbootjpa.codeGod.entity.SysRolesRermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysRolesRermissionsRepository extends JpaSpecificationExecutor<SysRolesRermissionsEntity>, PagingAndSortingRepository<SysRolesRermissionsEntity, Long>, JpaRepository<SysRolesRermissionsEntity,Long> {
    @Query(value = "select sr from SysRolesRermissionsEntity sr where sr.roleId=:roleId")
    List<SysRolesRermissionsEntity> findByRoleId(@Param(value = "roleId") SysRolesEntity role_id);

}
