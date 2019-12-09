package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.SysUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SysUsersRepository extends JpaSpecificationExecutor<SysUsersEntity>, PagingAndSortingRepository<SysUsersEntity, Long>, JpaRepository<SysUsersEntity,Long> {

    SysUsersEntity findByUsername(String username);


}
