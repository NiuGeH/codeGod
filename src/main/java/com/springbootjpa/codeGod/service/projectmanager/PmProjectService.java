package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmProjectEntity;
import org.springframework.data.domain.Page;

public interface PmProjectService {
    Page<PmProjectEntity> findAll();
}
