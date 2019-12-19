package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.operation.OperationMedalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/19 10:36
 */
public interface OperationMedalService {

    /**
     * 添加勋章
     * @param name 勋章名称
     * @param medalPhotoFile 上传的勋章图标文件
     * @return
     */
    OperationMedalEntity addMedal(String name, MultipartFile medalPhotoFile) throws CodeGodException;

    /**
     * 修改勋章
     * @param id 勋章id
     * @param newName 勋章新名称
     * @param medalPhotoFile 上传的勋章图标文件
     * @return
     */
    OperationMedalEntity updateMedal(Long id, String newName, MultipartFile medalPhotoFile) throws CodeGodException;

    /**
     * 删除勋章，软删除，只是改变了状态
     * @param id 勋章id
     * @return
     */
    OperationMedalEntity deleteMedal(Long id);

    /**
     * 未被删除的勋章分页
     * @param pageable 分页
     * @return
     */
    Page<OperationMedalEntity> findAll(Pageable pageable);
}
