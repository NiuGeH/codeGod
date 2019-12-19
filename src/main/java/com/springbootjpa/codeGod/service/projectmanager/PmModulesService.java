package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmModulesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PmModulesService {

    Page<PmModulesEntity> doPage(Pageable pageable,Long projectId);

    boolean saveModelus(PmModulesEntity modulesEntity);

    PmModulesEntity findOne(Long id);

    List<PmModulesEntity> findAllByTechnologyStack(String technologyStackName);


}
