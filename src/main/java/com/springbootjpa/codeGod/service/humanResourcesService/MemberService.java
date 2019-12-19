package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface MemberService {
    /**
     *  用户列表分页
     * @param pageable pageable
     * @param memberDisplay 是否公开
     * @param memberStationing 是否驻场
     * @param memberSigningPost  所有角色
     * @param memberType 所有用户
     * @param keyWord 关键测
     * @return 集合实体
     */
    Page<MemberEntity> findAll(Pageable pageable,String memberDisplay,String memberStationing,String memberSigningPost,String memberType,String keyWord);

    /**
     *
     * @return 返回Member 数据字典中对应的数据 HashMap
     */
    HashMap<String , Object> allDataFromDictionary();


    /**
     * 封装文件的,号拆分 换成 List<UploadFile>
     * @param memberEndId 实体类
     * @return 封装后的实体
     */
    MemberEntity doStringConvarToList(MemberEntity memberEndId);
}
