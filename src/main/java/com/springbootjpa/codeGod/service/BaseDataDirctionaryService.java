package com.springbootjpa.codeGod.service;

import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;

import java.util.List;

public interface BaseDataDirctionaryService {
    BaseDataDictionaryEntity findByDataKeyAndColumReturnDataValue(String key,String colum_name);

    List<BaseDataDictionaryEntity> findByColumNameRetrunDirctionaryAryList(String colum_Name);
}
