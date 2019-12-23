package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationRegionRepository;
import com.springbootjpa.codeGod.service.operationService.OperationRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/17 11:44
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationRegionServiceImpl implements OperationRegionService {

    @Autowired
    private OperationRegionRepository operationRegionRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加城市
     * @param name 城市名称
     * @param order 城市排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationRegionEntity addCity(String name, Long order, Integer display) {
        if(ObjectUtils.isEmpty(name)){
            throw new CodeGodRunTimExcetion("城市名称不能为空", this.getClass());
        }

        //判断城市是否已存在
        OperationRegionEntity ce = operationRegionRepository.findByCityName(name);
        if(!ObjectUtils.isEmpty(ce)){
            throw new CodeGodRunTimExcetion("该城市已存在", this.getClass());
        }

        //添加新城市
        OperationRegionEntity city = new OperationRegionEntity();
        city.setCityName(name);
        if(ObjectUtils.isEmpty(order)){
            city.setCityOrder(operationRegionRepository.findMaxCityOrder()+1);
        }else {
            city.setCityOrder(order);
        }
        if(ObjectUtils.isEmpty(display)){
            city.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            city.setDisplay(display);
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        city.setCreateTime(now);
        log.info("添加的新城市：" + city.toString());
        operationRegionRepository.save(city);

        return city;
    }

    /**
     * 修改城市
     * @param id 城市id
     * @param newName 城市新名称
     * @param order 城市排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationRegionEntity updateCity(Long id, String newName, Long order, Integer display) {
        if(ObjectUtils.isEmpty(id)){
            throw new CodeGodRunTimExcetion("参数id不能为空", this.getClass());
        }
        //查询需要修改的城市
        OperationRegionEntity city = operationRegionRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("参数id错误，未查到匹配栏目",this.getClass()));
        log.info("城市修改前：" + city.toString());

        //修改该城市属性
        if (!ObjectUtils.isEmpty(newName)) {
            city.setCityName(newName);
        }
        if (!ObjectUtils.isEmpty(order)) {
            city.setCityOrder(order);
        }
        if (!ObjectUtils.isEmpty(display)) {
            city.setDisplay(display);
        }
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        city.setModifyTime(now);
        log.info("城市修改后：" + city.toString());

        //保存修改后的城市
        operationRegionRepository.save(city);

        return city;
    }

    /**
     * 查询全部城市
     * @return
     */
    @Override
    public Page<OperationRegionEntity> findAll(Pageable pageable) {
        Specification<OperationRegionEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        Page<OperationRegionEntity> all = operationRegionRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationRegionEntity operationRegionEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationRegionEntity.getDisplay().toString(), DataBaseFinal.OPERATION_REGION_DISPLAY);
                operationRegionEntity.setDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }
}
