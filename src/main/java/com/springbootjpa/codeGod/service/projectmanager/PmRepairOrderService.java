package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmRepairOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PmRepairOrderService {


    Page<PmRepairOrderEntity> doPage(Pageable pageable,Long projectId);


    PmRepairOrderEntity findRepairOrder(Long id);

}
