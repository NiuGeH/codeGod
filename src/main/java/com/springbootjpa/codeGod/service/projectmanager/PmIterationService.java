package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmIterationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface PmIterationService {

    void delete(Long id);

    PmIterationEntity findOneById(Long id);

    Page<PmIterationEntity> doPage(Pageable pageable,Long porjectId);

    boolean saveIteration(HashMap<String,String> map);
}
