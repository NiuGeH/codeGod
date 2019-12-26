package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceSkillEntity;
import com.springbootjpa.codeGod.entity.operation.OperationSkillEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationResourceRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationResourceSkillRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationSkillRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.operationService.OperationResourceService;
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
import java.util.*;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/23 18:07
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationResourceServiceImpl implements OperationResourceService {

    @Autowired
    private OperationResourceRepository operationResourceRepository;

    @Autowired
    private OperationSkillRepository operationSkillRepository;

    @Autowired
    private OperationResourceSkillRepository operationResourceSkillRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private SaveFileUtils saveFileUtils = new SaveFileUtils();

    /**
     * 添加资源类型
     * @param name 资源类型名称
     * @param resourcePhotoFile 上传的图标文件
     * @param amount 人数
     * @param order 资源排序
     * @param skillMap 关联技术栈map
     * @param display 是否显示：0是，1否
     * @return
     * @throws CodeGodException
     */
    @Override
    public OperationResourceEntity addResource(String name, MultipartFile resourcePhotoFile, Long amount, Long order, Map<Long,String> skillMap, Integer display) throws CodeGodException {
        //参数验证
        if(ObjectUtils.isEmpty(name)) throw new CodeGodRunTimExcetion("资源类型名称不能为空", this.getClass());
        if(ObjectUtils.isEmpty(resourcePhotoFile) || resourcePhotoFile.getSize()==0) throw new CodeGodRunTimExcetion("资源类型图标不能为空", this.getClass());
        //判断资源类型是否存在
        OperationResourceEntity re = operationResourceRepository.findByResourceName(name);
        if(!ObjectUtils.isEmpty(re)){
            throw new CodeGodRunTimExcetion("该资源类型名称已存在", this.getClass());
        }
        //添加
        OperationResourceEntity resourceEntity = new OperationResourceEntity();
        //资源类型名称
        resourceEntity.setResourceName(name);
        //上传图标文件
        UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(resourcePhotoFile));
        resourceEntity.setResourcePhoto(uploadFile);
        //人数
        resourceEntity.setAmount(amount);
        //排序
        if(ObjectUtils.isEmpty(order)){
            resourceEntity.setResourceOrder(operationResourceRepository.findMaxResourceOrder()+1);
        }else {
            resourceEntity.setResourceOrder(order);
        }
        //技术栈名称
        if(!ObjectUtils.isEmpty(skillMap)){
            List<String> skillNames = new ArrayList<>(skillMap.values());
            resourceEntity.setSkillNames(skillNames);
        }
        //是否显示
        if(ObjectUtils.isEmpty(display)){
            resourceEntity.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            resourceEntity.setDisplay(display);
        }
        //创建时间
        resourceEntity.setCreateTime(Calendar.getInstance().getTime());

        //保存资源类型
        OperationResourceEntity resource = operationResourceRepository.save(resourceEntity);

        //关联技术栈
        OperationResourceSkillEntity rse = new OperationResourceSkillEntity();
        Set<Long> skillIds = skillMap.keySet();
        for(Long skillId : skillIds){
            OperationSkillEntity skillEntity = operationSkillRepository.findById(skillId).orElseThrow(()->new CodeGodRunTimExcetion(skillMap.get(skillId)+"技术栈不存在",this.getClass()));
            rse.setResource(resource);
            rse.setSkill(skillEntity);
            //保存关联技术栈
            operationResourceSkillRepository.save(rse);
        }

        return resource;
    }

    /**
     * 修改资源类型
     * @param id 资源类型id
     * @param newName  资源类型新名称
     * @param resourcePhotoFile 上传的图标文件
     * @param amount 人数
     * @param order 资源排序
     * @param skillMap 关联技术栈map
     * @param display 是否显示：0是，1否
     * @return
     * @throws CodeGodException
     */
    @Override
    public OperationResourceEntity updateResource(Long id, String newName, MultipartFile resourcePhotoFile, Long amount, Long order, Map<Long,String> skillMap, Integer display) throws CodeGodException {
        //参数验证
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("被修改的资源类型id不能为空",this.getClass());
        if(ObjectUtils.isEmpty(newName)) throw new CodeGodRunTimExcetion("资源类型名称不能为空", this.getClass());
        if(ObjectUtils.isEmpty(resourcePhotoFile) || resourcePhotoFile.getSize()==0) throw new CodeGodRunTimExcetion("资源类型图标不能为空", this.getClass());

        OperationResourceEntity resource = operationResourceRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("资源类型id错误，未查到匹配资源类型", this.getClass()));

        //修改资源类型
        //资源类型名称
        if (!resource.getResourceName().equals(newName)) {
            OperationResourceEntity re = operationResourceRepository.findByResourceName(newName);
            if(!ObjectUtils.isEmpty(re)) throw new CodeGodRunTimExcetion("资源类型名称已存在",this.getClass());
            resource.setResourceName(newName);
        }
        //上传图标文件
        UploadFile uploadFile = saveFileUtils.saveFile(resourcePhotoFile);
        uploadFile.setId(resource.getResourcePhoto().getId());
        uploadFileRepository.save(uploadFile);
        resource.setResourcePhoto(uploadFile);
        //人数
        resource.setAmount(amount);
        //资源排序
        if(!ObjectUtils.isEmpty(order)) resource.setResourceOrder(order);
        //技术栈名称
        if(!ObjectUtils.isEmpty(skillMap)){
            List<String> skillNames = new ArrayList<>(skillMap.values());
            resource.setSkillNames(skillNames);
        }else {
            resource.setSkillNames(null);
        }
        //是否显示
        if(!ObjectUtils.isEmpty(display)) resource.setDisplay(display);
        //修改时间
        resource.setModifyTime(Calendar.getInstance().getTime());

        //保存资源类型
        operationResourceRepository.save(resource);

        //修改关联技术栈
        //oldSkillIds原关联的技术栈集合
        //newSkillIds修改后关联的技术栈集合
        List<OperationResourceSkillEntity> orsList = operationResourceSkillRepository.findByResourceId(id);
        List<Long> oldSkillIds = new ArrayList<>();
        Map<Long,Long> map = new HashMap<>();
        for (OperationResourceSkillEntity operationResourceSkillEntity : orsList){
            oldSkillIds.add(operationResourceSkillEntity.getSkill().getId());
            map.put(operationResourceSkillEntity.getSkill().getId(), operationResourceSkillEntity.getId());
        }
        //判断skillMap是否为空
        if(!ObjectUtils.isEmpty(skillMap)){
            List<Long> newSkillIds = new ArrayList<>(skillMap.keySet());
            OperationResourceSkillEntity ors = new OperationResourceSkillEntity();
            if(!oldSkillIds.equals(newSkillIds)){
                //删除修改后不关联的技术栈
                for(Long skillId : oldSkillIds){
                    if(!newSkillIds.contains(skillId)) operationResourceSkillRepository.deleteById(map.get(skillId));
                }
                //添加新关联的技术栈
                for(Long skillId : newSkillIds){
                    if(!oldSkillIds.contains(skillId)){
                        OperationSkillEntity skillEntity = operationSkillRepository.findById(skillId).orElseThrow(()->new CodeGodRunTimExcetion(skillMap.get(skillId)+"技术栈不存在",this.getClass()));
                        ors.setResource(resource);
                        ors.setSkill(skillEntity);
                        //保存关联技术栈
                        operationResourceSkillRepository.save(ors);
                    }
                }
            }
        }else {
            for (Long skillId : oldSkillIds){
                operationResourceSkillRepository.deleteById(map.get(skillId));
            }
        }

        return resource;
    }

    /**
     * 查询全部资源类型分页
     * @param pageable
     * @return
     */
    @Override
    public Page<OperationResourceEntity> findAll(Pageable pageable) {
        Specification<OperationResourceEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<OperationResourceEntity> all = operationResourceRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            //遍历资源类型
            for(OperationResourceEntity operationResourceEntity : all){
                Long resourceId = operationResourceEntity.getId();
                List<OperationResourceSkillEntity> orsList = operationResourceSkillRepository.findByResourceId(resourceId);
                List<String> list = new ArrayList<>();
                if(!ObjectUtils.isEmpty(orsList) && orsList.size()>0){
                    for(OperationResourceSkillEntity operationResourceSkillEntity : orsList){
                        list.add(operationResourceSkillEntity.getSkill().getSkillName());
                    }
                }
                operationResourceEntity.setSkillNames(list);
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationResourceEntity.getDisplay().toString(), DataBaseFinal.OPERATION_RESOURCE_DISPLAY);
                operationResourceEntity.setDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }
}
