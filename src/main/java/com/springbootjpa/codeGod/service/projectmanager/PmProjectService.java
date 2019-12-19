package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PmProjectService {
    Page<PmProjectEntity> findAll();

    int getCount(Long projectManagerId);

    boolean saveProject(PmProjectEntity pmProjectEntity,MultipartFile[] requirementDocument) throws CodeGodException;

    Page<PmProjectEntity> doPage(Pageable pageable, Integer projectStuats);


    PmProjectEntity findOne(Long id);


    boolean updateStatus(Long id,Integer status);

}
