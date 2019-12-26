package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.humanResources.MemberCaseEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MemberCaseService {

    /**
     *  用户案例分页
     * @param pageable pageable
     * @param memberId 用户Id
     * @return 集合实体
     */
    Page<MemberCaseEntity> findAll(Pageable pageable,String memberId);

    /**
     * 添加用户案例
     * @param memberCaseEntity 案例实体
     * @param casePhotoMultipartFile 图片
     * @return 保存后的案例实体
     * @throws CodeGodException 异常
     */
    MemberCaseEntity doAdd(MemberCaseEntity memberCaseEntity, MultipartFile casePhotoMultipartFile) throws CodeGodRunTimExcetion, CodeGodException;

    /**
     * 修改用户案例
     * @param memberCaseEntity 案例实体
     * @param casePhotoMultipartFile 图片
     * @return 修改后的案例实体
     * @throws CodeGodRunTimExcetion 自定义异常
     * @throws CodeGodException 自定义
     */
    MemberCaseEntity doUpdate(MemberCaseEntity memberCaseEntity,MultipartFile casePhotoMultipartFile) throws CodeGodRunTimExcetion, CodeGodException;
}
