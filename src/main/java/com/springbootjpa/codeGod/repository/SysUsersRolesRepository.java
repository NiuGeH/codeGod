package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.SysUsersEntity;
import com.springbootjpa.codeGod.entity.SysUsersRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SysUsersRolesRepository extends JpaSpecificationExecutor<SysUsersRolesEntity>, PagingAndSortingRepository<SysUsersRolesEntity, Long>, JpaRepository<SysUsersRolesEntity,Long> {

    List<SysUsersRolesEntity> findByUserId(SysUsersEntity user_id);
}
