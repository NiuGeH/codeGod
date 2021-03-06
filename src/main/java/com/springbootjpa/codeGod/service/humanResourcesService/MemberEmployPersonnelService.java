package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.entity.humanResources.MemberEmployPersonnelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberEmployPersonnelService {

    /**
     * 指定人员 分页
     * @param pageable 页容量和页数
     * @param employPersionnelType 指定人员类型
     * @return Page<MemberEmployPersonnelEntity>
     */
    Page<MemberEmployPersonnelEntity> doPage(Pageable pageable, Long employId, Integer... employPersionnelType);

    /**
     * 添加 修改
     * @param memberEmployPersonnelEntity 实体
     * @return MemberEmployPersonnelEntity
     */
    MemberEmployPersonnelEntity doAddReleaseRequire(MemberEmployPersonnelEntity memberEmployPersonnelEntity);

    /**
     * 设置
     * @param memberEmployPersonnelEntity 实体
     * @return MemberEmployPersonnelEntity
     */
    MemberEmployPersonnelEntity doSetting(MemberEmployPersonnelEntity memberEmployPersonnelEntity);
}
