package com.springbootjpa.codeGod.service.baseService;

import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;

import java.util.List;

public interface BaseDataDirctionaryService {
    /**
     * 数据字典 根据key 和 表.字段名查询 对应的value
     * @param key 0/1 ...
     * @param colum_name 表.字段名
     * @return BaseDataDictionaryEntity
     */
    BaseDataDictionaryEntity findByDataKeyAndColumReturnDataValue(String key,String colum_name);
    /**
     * 数据字典 根据value 和 表.字段名查询 对应的key
     * @param value
     * @param colum_name 表.字段名
     * @return BaseDataDictionaryEntity
     */
    BaseDataDictionaryEntity findByDataKeyAndColumReturnDataKey(String value,String colum_name);
    /**
     * 根据表.字段名返回所有数据
     * @param colum_Name 表.字段名
     * @return List<BaseDataDictionaryEntity>
     */
    List<BaseDataDictionaryEntity> findByColumNameRetrunDirctionaryAryList(String colum_Name);
}
