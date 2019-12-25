package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 11:46
 */
public interface OperationPersonalMenuService {

    /**
     * 添加个人中心菜单
     * @param menuName 菜单名称
     * @param menuPhotoFile 上传的菜单图标文件
     * @param display 是否显示：0是，1否
     * @return
     */
    OperationPersonalMenuEntity addPersonalMenu(String menuName, MultipartFile menuPhotoFile, Integer display) throws CodeGodException;

    /**
     * 修改个人中心菜单
     * @param id 被修改的菜单id
     * @param menuName 菜单名称
     * @param menuPhotoFile 上传的菜单图标文件
     * @param display 是否显示：0是，1否
     * @return
     */
    OperationPersonalMenuEntity updateMenu(Long id, String menuName, MultipartFile menuPhotoFile, Integer display) throws CodeGodException;

    /**
     * 查询全部菜单分页
     * @param pageable
     * @return
     */
    Page<OperationPersonalMenuEntity> findAll(Pageable pageable);
}
