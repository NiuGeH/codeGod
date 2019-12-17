package com.springbootjpa.codeGod.repository;

import com.springbootjpa.codeGod.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UploadFileRepository extends JpaSpecificationExecutor<UploadFile>, PagingAndSortingRepository<UploadFile, Long>, JpaRepository<UploadFile,Long> {
}
