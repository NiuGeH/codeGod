package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceEentity;
import com.springbootjpa.codeGod.entity.humanResources.MemberResourceSkillEntity;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.HumanResources.MemberResourceSkillentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberResourceentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.Operation.*;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.baseService.BaseDataDirctionaryService;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用户表Service
 * @author NiuGeH
 * @date 2019/12/19
 */
@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class MemberServiceImpl extends MemberServiceBase implements MemberService {



    private Specification<MemberEntity> getSpecification( String memberDisplay, String memberStationing, String memberSigningPost, String memberType, String keyWord, String memebrCityEntityId) {
        return new Specification<MemberEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //是否公开
                Predicate perState = criteriaBuilder.or(criteriaBuilder.equal(root.get("memberState"), HumanRecourcesStatus.MEMBER_STATE_ZC.getIndex()),criteriaBuilder.isNull(root.get("memberState")));
                Predicate perMemberDisplay = !ObjectUtils.isEmpty(memberDisplay) ? criteriaBuilder.equal(root.get("memberDisplay"), memberDisplay) : criteriaBuilder.like(root.get("nickName"), "%");
                Predicate perMemberStationing = !ObjectUtils.isEmpty(memberStationing) ? criteriaBuilder.equal(root.get("memberStationing"),memberStationing) : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perMemberSigningPost = !ObjectUtils.isEmpty(memberSigningPost) ? criteriaBuilder.equal(root.get("memberSigningPost"),memberSigningPost) : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perMemberType = !ObjectUtils.isEmpty(memberType) ? criteriaBuilder.equal(root.get("memberType"),memberType) : criteriaBuilder.like(root.get("nickName") , "%");
                Predicate perCity = !ObjectUtils.isEmpty(memebrCityEntityId) ? criteriaBuilder.equal(root.get("memebrCityEntity").get("id"),Long.valueOf(memebrCityEntityId)) : criteriaBuilder.like(root.get("nickName") , "%");
                Predicate perNickName = !ObjectUtils.isEmpty(keyWord) ? criteriaBuilder.like(root.get("nickName"),"%"+keyWord+"%") : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perRealName = !ObjectUtils.isEmpty(keyWord) ? criteriaBuilder.like(root.get("memberPricacy").get("memberRealName"), "%"+keyWord+"%") : criteriaBuilder.like(root.get("nickName"),"%");
                Predicate perMobile = !ObjectUtils.isEmpty(keyWord) ? criteriaBuilder.like(root.get("memberPricacy").get("memberMobile"),"%"+keyWord+"%") :  criteriaBuilder.like(root.get("nickName"),"%");
                Predicate or = criteriaBuilder.or(perNickName, perRealName, perMobile);
                return criteriaBuilder.and(perState,perMemberDisplay, perMemberSigningPost, perMemberStationing, perMemberType, perCity, or);
            }
        };
    }


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
    @Override
    public Page<MemberEntity> findAll(Pageable pageable, String memberDisplay, String memberStationing, String memberSigningPost, String memberType, String keyWord, String memebrCityEntityId) {

        return memberentityRepository.findAll(getSpecification(memberDisplay, memberStationing, memberSigningPost, memberType, keyWord,memebrCityEntityId),pageable);
    }


    /**
     *
     * @return 返回Member 数据字典中对应的数据 HashMap
     */
    @Override
    public HashMap<String, Object> allDataFromDictionary() {
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("memberType",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERTYPE));
        hashMap.put("memberSigningPost",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGPOST));
        hashMap.put("memberSigningMode",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGMODE));
        hashMap.put("memberMedal", operationMedalRepository.findAllByState(OperationEnum.OPERATION_STATE_ZC.getIndex()));
        hashMap.put("memberStationing", baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSTATIONING));
        hashMap.put("memberStatus",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSTATUS));
        hashMap.put("memberDisplay",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERDISPLAY));
        hashMap.put("memberSigningStatus",baseDataDirctionaryService.findByColumNameRetrunDirctionaryAryList(DataBaseFinal.MEMBER_MEMBERSIGNINGSTATUS));
        hashMap.put("memberTeam", operationTeamRepository.findAllByState(OperationEnum.OPERATION_STATE_ZC.getIndex()));
//        hashMap.put("")
        return hashMap;
    }



    /**
     * 封装文件的,号拆分 换成 List<UploadFile>
     * @param memberEndId 实体类
     * @return 封装后的实体
     */
    @Override
    public MemberEntity doStringConvarToList(MemberEntity memberEndId) {
        MemberPrivacyEntity memberPrivacyEntity = memberEndId.getMemberPricacy();
        if(ObjectUtils.isEmpty(memberPrivacyEntity)){
            log.debug("用户私密表为空 直接返回");
            return memberEndId;
        }
        //用户个人资料信息表 用户信息可以是多个
        String memberPersonalData = memberPrivacyEntity.getMemberPersonalData();
        if(!(StringUtils.isEmpty(memberPersonalData))){
            List<UploadFile> list = new ArrayList<>();
            String[] split = memberPersonalData.split(",");
            for (String s : split) {
                if(!StringUtils.isEmpty(s)){
                    list.add(uploadFileRepository.findById(Long.valueOf(s)).get());
                }
            }
            memberEndId.getMemberPricacy().setMemberPersonalDataList(list);
        }
        //签约协议也可以是多个
        String memberSigningAgreement = memberEndId.getMemberSigningAgreement();
        if(!(StringUtils.isEmpty(memberSigningAgreement))){
            List<UploadFile> list = new ArrayList<>();
            String[] split = memberSigningAgreement.split(",");
            for (String s : split) {
                if(!StringUtils.isEmpty(s)){
                    list.add(uploadFileRepository.findById(Long.valueOf(s)).get());
                }
            }
            memberEndId.setMemberSigningAgreementList(list);
        }
        return memberEndId;
    }


    /**
     * 对技能管理进行提交数据库
     * @param str 前端传来的json
     */
    @Override
    public void doSaveResourceAndSkillList(String str) {
        Gson gson = new Gson();
        HashMap<String,Object> hashMap = gson.fromJson(str, HashMap.class);
        String memberId = (String)hashMap.get("memberId");
        if(StringUtils.isEmpty("memberId")){
            throw new CodeGodRunTimExcetion("memberId为空",this.getClass());
        }
        List<MemberResourceEentity> allByMemberId = memberResourceentityRepository.findAllByMemberId(Long.valueOf(memberId));
        for (MemberResourceEentity memberResourceEentity : allByMemberId) {
            List<MemberResourceSkillEntity> allByMemberResourceId = memberResourceSkillentityRepository.findAllByMemberResourceId(memberResourceEentity.getId());
            OperationResourceEntity op = operationResourceRepository.findById(memberResourceEentity.getMemberOperationResource().getId()).orElseThrow(() -> new CodeGodRunTimExcetion("资源Id不存在", this.getClass()));
            op.setAmount(op.getAmount()-1);
            memberResourceSkillentityRepository.deleteAll(allByMemberResourceId);
            memberResourceentityRepository.deleteAll(allByMemberId);
        }

        List<LinkedTreeMap> json1 = (List<LinkedTreeMap>)hashMap.get("ResourceAndListSkill");
        for (LinkedTreeMap linkedTreeMap : json1) {
            //资源Id
            String resourceId = (String) linkedTreeMap.get("resourceId");
            //资源的定位
            String resourceProficiency = (String) linkedTreeMap.get("resourceProficiency");
            MemberResourceEentity memberResourceEentity = new MemberResourceEentity();
            memberResourceEentity.setMemberId(Long.valueOf(memberId));
            OperationResourceEntity operationResourceEntity = operationResourceRepository.findById(Long.valueOf(resourceId)).orElseThrow(() -> new CodeGodRunTimExcetion("resourceId 不存在", this.getClass()));
            operationResourceEntity.setAmount(operationResourceEntity.getAmount()+1);
            memberResourceEentity.setMemberOperationResource(operationResourceEntity);
            memberResourceEentity.setMemberProficiency(Integer.valueOf(resourceProficiency));
            MemberResourceEentity save = memberResourceentityRepository.save(memberResourceEentity);

            List<LinkedTreeMap> k =(List<LinkedTreeMap>) linkedTreeMap.get("skillList");
            for (LinkedTreeMap treeMap : k) {
                //技术Id
                String skillId = (String)treeMap.get("skillId");
                //技术定位
                String skillProficiency = (String)treeMap.get("skillProficiency");
                MemberResourceSkillEntity memberResourceSkillEntity = new MemberResourceSkillEntity();
                memberResourceSkillEntity.setMemberResourceId(save.getId());
                memberResourceSkillEntity.setSkillProficiency(Integer.valueOf(skillProficiency));
                if(StringUtils.isEmpty(skillId)) throw new CodeGodRunTimExcetion("技术Id为空",this.getClass());
                memberResourceSkillEntity.setSkillId(operationSkillRepository.findById(Long.valueOf(skillId)).orElseThrow(() -> new CodeGodRunTimExcetion("资源技术Id不存在",this.getClass())));
                memberResourceSkillentityRepository.save(memberResourceSkillEntity);
            }
        }
    }

    @Override
    public MemberEntity doItengerCoanvarToData(MemberEntity memberEntity) {

        return null;
    }

    @Override
    public List<MemberResourceEentity> findByMemberIdReturnResourceAndKill(Long memberId) {
        List<MemberResourceEentity> allByMemberId = memberResourceentityRepository.findAllByMemberId(memberId);
        for (MemberResourceEentity memberResourceEentity : allByMemberId) {
            List<MemberResourceSkillEntity> allByMemberResourceId = memberResourceSkillentityRepository.findAllByMemberResourceId(memberResourceEentity.getId());
            memberResourceEentity.setMemberResourceSkillEntityList(allByMemberResourceId);
        }
        return allByMemberId;
    }
}
