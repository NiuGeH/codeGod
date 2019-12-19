package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.entity.projectmanager.PmRecruitmentEntity;
import com.springbootjpa.codeGod.entity.projectmanager.PmTeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface PmTeamService {

    Page<PmTeamEntity> doPage(Pageable pageable,Long projectId);


    HashMap<String,Object> DataRendering(Long memberId,String dutyName);
}
