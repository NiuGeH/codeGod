package com.springbootjpa.codeGod.service.Impl;

import com.springbootjpa.codeGod.controller.UserRolesController;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.service.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class BaseDataDirctionaryServiceImpl implements BaseDataDirctionaryService {

    private static Logger logger = LoggerFactory.getLogger(BaseDataDirctionaryServiceImpl.class);

    @Resource
    private RedisUtils redisUtils = new RedisUtils();

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;
    @Override
    public BaseDataDictionaryEntity findByDataKeyAndColumReturnDataValue(String key, String colum_name) {

        BaseDataDictionaryEntity baseDataDictionaryEntity = (BaseDataDictionaryEntity) redisUtils.get("dirctionary_"+key+"_"+colum_name);
        if(ObjectUtils.isEmpty(baseDataDictionaryEntity)){
            logger.debug("findByDataKeyAndColumReturnDataValue no Redis");
            BaseDataDictionaryEntity distinctByDataColumnNameAndAndDataKey = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(key, colum_name);
            redisUtils.set("dirctionary_" + key + "_" + colum_name, distinctByDataColumnNameAndAndDataKey,10800);
            return distinctByDataColumnNameAndAndDataKey;
        }else {
            logger.debug(" findByDataKeyAndColumReturnDataValue Redis");
            return baseDataDictionaryEntity;
        }

    }

    @Override
    public List<BaseDataDictionaryEntity> findByColumNameRetrunDirctionaryAryList(String colum_Name) {
        List<BaseDataDictionaryEntity> list =  (List<BaseDataDictionaryEntity>)redisUtils.get("dirctionary_"+colum_Name);
        if(ObjectUtils.isEmpty(list)){
            logger.debug("findByColumNameRetrunDirctionaryAryList  No Redis");
            List<BaseDataDictionaryEntity> baseDataDictionaryEntityByDataColumnName = baseDataDictionaryentityRepository.findBaseDataDictionaryEntityByDataColumnName(colum_Name);
            redisUtils.set("dirctionary_"+colum_Name,baseDataDictionaryEntityByDataColumnName,10800);
            return baseDataDictionaryEntityByDataColumnName;
        }else {
            logger.debug("findByColumNameRetrunDirctionaryAryList  Redis");
            return list;
        }
    }
}
