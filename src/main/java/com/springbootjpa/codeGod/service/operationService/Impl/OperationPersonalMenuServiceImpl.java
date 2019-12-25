package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationPersonalMenuEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationPersonalMenuRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.operationService.OperationPersonalMenuService;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 11:52
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationPersonalMenuServiceImpl implements OperationPersonalMenuService {

    @Autowired
    private OperationPersonalMenuRepository operationPersonalMenuRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加个人中心菜单
     * @param menuName 菜单名称
     * @param menuPhotoFile 上传的菜单图标文件
     * @param display 是否显示：0是，1否
     * @return
     * @throws CodeGodException
     */
    @Override
    public OperationPersonalMenuEntity addPersonalMenu(String menuName, MultipartFile menuPhotoFile, Integer display) throws CodeGodException {
        //参数验证
        if(ObjectUtils.isEmpty(menuName)) throw new CodeGodRunTimExcetion("菜单名称不能为空", this.getClass());

        //判断菜单名称是否存在
        OperationPersonalMenuEntity menuEntity = operationPersonalMenuRepository.findByMenuName(menuName);
        if(!ObjectUtils.isEmpty(menuEntity)) throw new CodeGodRunTimExcetion("该菜单名称已存在", this.getClass());

        //添加
        OperationPersonalMenuEntity menu = new OperationPersonalMenuEntity();
        menu.setMenuName(menuName);
        if(!ObjectUtils.isEmpty(menuPhotoFile)){
            UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(menuPhotoFile));
            menu.setMenuPhoto(uploadFile);
        }
        if(ObjectUtils.isEmpty(display)){
            menu.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            menu.setDisplay(display);
        }
        menu.setCreateTime(Calendar.getInstance().getTime());

        //保存
        operationPersonalMenuRepository.save(menu);

        return menu;
    }

    /**
     * 修改个人中心菜单
     * @param id 被修改的菜单id
     * @param menuName 菜单名称
     * @param menuPhotoFile 上传的菜单图标文件
     * @param display 是否显示：0是，1否
     * @return
     */
    @Override
    public OperationPersonalMenuEntity updateMenu(Long id, String menuName, MultipartFile menuPhotoFile, Integer display) throws CodeGodException {
        //参数验证
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("需要修改的菜单id不能为空", this.getClass());
        if(ObjectUtils.isEmpty(menuName)) throw new CodeGodRunTimExcetion("菜单名称不能为空", this.getClass());
        //查询需要修改的菜单
        OperationPersonalMenuEntity menuEntity = operationPersonalMenuRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("需要修改的菜单id可能错误，未查到匹配的菜单", this.getClass()));
        //修改
        if (!menuEntity.getMenuName().equals(menuName)) {
            OperationPersonalMenuEntity pme = operationPersonalMenuRepository.findByMenuName(menuName);
            if(!ObjectUtils.isEmpty(pme)) throw new CodeGodRunTimExcetion("该菜单名称已存在",this.getClass());
            menuEntity.setMenuName(menuName);
        }
        if(!ObjectUtils.isEmpty(menuPhotoFile)){
            UploadFile uploadFile = menuEntity.getMenuPhoto();
            Long fileId = uploadFile.getId();
            uploadFile = saveFileUtils.saveFile(menuPhotoFile);
            uploadFile.setId(fileId);
            uploadFileRepository.save(uploadFile);
            menuEntity.setMenuPhoto(uploadFile);
        }
        if (!ObjectUtils.isEmpty(display)) {
            menuEntity.setDisplay(display);
        }
        menuEntity.setModifyTime(Calendar.getInstance().getTime());

        //保存
        operationPersonalMenuRepository.save(menuEntity);

        return menuEntity;
    }

    /**
     * 查询全部菜单分页
     * @param pageable
     * @return
     */
    @Override
    public Page<OperationPersonalMenuEntity> findAll(Pageable pageable) {
        Specification<OperationPersonalMenuEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<OperationPersonalMenuEntity> all = operationPersonalMenuRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationPersonalMenuEntity operationPersonalMenuEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationPersonalMenuEntity.getDisplay().toString(), DataBaseFinal.OPERATION_PERSONAL_MENU_DISPLAY);
                operationPersonalMenuEntity.setDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }
}
