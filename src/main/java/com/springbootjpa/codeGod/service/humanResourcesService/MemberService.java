package com.springbootjpa.codeGod.service.humanResourcesService;

import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface MemberService {
    /**
     *  用户列表分页
     * @param pageable pageable
     * @param memberDisplay 是否公开
     * @param memberStationing 是否驻场
     * @param memberSigningPost  所有角色
     * @param memberType 所有用户
     * @param keyWord 关键测
     * @param memebrCityEntityId 城市
     * @return 集合实体
     */
    Page<MemberEntity> findAll(Pageable pageable,String memberDisplay,String memberStationing,String memberSigningPost,String memberType,String keyWord,String memebrCityEntityId);

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

    /**
     * 对技能管理进行提交数据库
     * @param str 前端传来的json
     */
    void doSaveResourceAndSkillList(String str);

    MemberEntity doItengerCoanvarToData(MemberEntity memberEntity);

    /**
     * 根据会员Id查询出该会员对应的技能和参与的角色
     * @param memberId 会员id
     * @return 会员对应的技能和角色
     */
    List<MemberResourceEentity> findByMemberIdReturnResourceAndKill(Long memberId);

    /**
     * 根据会员Id查询会员对应的角色
     * @param memberId 会员id
     */
    List<OperationResourceEntity> findByMemberIdReturnResource(Long memberId);
}
