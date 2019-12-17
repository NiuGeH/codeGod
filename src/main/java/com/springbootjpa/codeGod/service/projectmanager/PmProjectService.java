package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmApplicationEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PmProjectService {
    Page<PmProjectEntity> findAll();

    int getCount(Long projectManagerId);

    boolean saveProject(PmProjectEntity pmProjectEntity);

    Page<PmProjectEntity> doPage(Pageable pageable, Integer projectStuats);


    PmProjectEntity findOne(Long id);

}
