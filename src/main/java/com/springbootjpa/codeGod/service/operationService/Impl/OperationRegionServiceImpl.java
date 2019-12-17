package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationRegionRepository;
import com.springbootjpa.codeGod.service.operationService.OperationRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public OperationRegionEntity addCity(String name, String order, Integer display) {
        //判断城市是否已存在
        OperationRegionEntity ce = operationRegionRepository.findByCityName(name);
        if(!ObjectUtils.isEmpty(ce)){
            throw new CodeGodRunTimExcetion("该城市已存在", this.getClass());
        }

        //添加新城市
        OperationRegionEntity city = new OperationRegionEntity();
        city.setCityName(name);
        city.setCityOrder(order);
        city.setDisplay(display);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        city.setCreateTime(now);
        log.info("添加的新城市：" + city.toString());
        operationRegionRepository.save(city);

        return city;
    }

    /**
     * 修改城市
     * @param oldName 城市原名称
     * @param newName 城市新名称
     * @param order 城市排序编号
     * @param display 是否显示，0显示，1不显示
     * @return
     */
    @Override
    public OperationRegionEntity updateCity(String oldName, String newName, String order, Integer display) {
        //查询需要修改的城市
        OperationRegionEntity city = operationRegionRepository.findByCityName(oldName);
        log.info("城市修改前：" + city.toString());

        //修改该城市属性
        city.setCityName(newName);
        city.setCityOrder(order);
        city.setDisplay(display);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        city.setModifyTime(now);
        log.info("城市修改后：" + city.toString());

        //保存修改后的城市
        operationRegionRepository.save(city);

        return city;
    }

    /**
     * 城市分页
     * @param pageable 分页
     * @return
     */
    @Override
    public Page<OperationRegionEntity> findAll(Pageable pageable) {
        Page<OperationRegionEntity> all = operationRegionRepository.findAll(pageable);
        List<OperationRegionEntity> list = new ArrayList<>();
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationRegionEntity operationRegionEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationRegionEntity.getDisplay().toString(), DataBaseFinal.OPERATIONREGION_DISPLAY);
                operationRegionEntity.setDisplayStr(bdd.getDataValue());
                list.add(operationRegionEntity);
            }
        }
        return new PageImpl<OperationRegionEntity>(list);
    }
}
