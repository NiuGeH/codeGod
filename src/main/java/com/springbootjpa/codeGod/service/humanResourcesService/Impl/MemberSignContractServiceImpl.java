package com.springbootjpa.codeGod.service.humanResourcesService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.UploadFile;
import com.springbootjpa.codeGod.entity.operation.OperationCompanyEntity;
import com.springbootjpa.codeGod.entity.operation.OperationRegionEntity;
import com.springbootjpa.codeGod.eunm.HumanRecourcesStatus;
import com.springbootjpa.codeGod.entity.humanResources.MemberEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberPrivacyEntity;
import com.springbootjpa.codeGod.entity.humanResources.MemberSignContractEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.repository.HumanResources.MemberPrivacyentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberSignContractentityRepository;
import com.springbootjpa.codeGod.repository.HumanResources.MemberentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationCompanyRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationRegionRepository;
import com.springbootjpa.codeGod.repository.UploadFileRepository;
import com.springbootjpa.codeGod.service.humanResourcesService.MemberSignContractService;
import com.springbootjpa.codeGod.utils.DateTimeUtils;
import com.springbootjpa.codeGod.utils.SaveFileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * 用户签约 Service
 *
 * @author NiuGeH
 * @date 2019/12/14
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class MemberSignContractServiceImpl extends MemberServiceBase implements MemberSignContractService  {


    /**
     * 根据验证码和签约结果分页
     *
     * @param pageable       分页实体
     * @param validationCode 验证码
     * @param siginEnd       签约结果
     * @return Page<MemberSignContractEntity>
     */
    @Override
    public Page<MemberSignContractEntity> doPageByValidationCodeAndSiginEnd(Pageable pageable, Integer validationCode, Integer siginEnd) {

        return memberSignContractentityRepository.findAll(getSpecification(validationCode, siginEnd), pageable);
    }

    /**
     * 根据Id 删除签约结果
     *
     * @param id memberSignContract的 Id
     */
    @Override
    public void byIdDelMemberSignContract(Long id) {
        Optional<MemberSignContractEntity> byId = memberSignContractentityRepository.findById(id);
        if (ObjectUtils.isEmpty(byId)) {
            throw new CodeGodRunTimExcetion("需要删除的Id不存在 byIdDelMemberSignContract", this.getClass());
        }
        MemberSignContractEntity entity = byId.get();
        if (ObjectUtils.isEmpty(entity.getSiginResults())) {
            throw new CodeGodRunTimExcetion("签约删除的状态为空", this.getClass());
        }
        //未申请状态删除
        if (entity.getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_WSQ.getIndex()) {
            memberSignContractentityRepository.delete(entity);
        } else if (entity.getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_YJJ.getIndex()) {
            //已拒绝删除方式
            MemberEntity memberEndId = entity.getMemberEndId();
            memberSignContractentityRepository.delete(entity);
            //删除私密信息表
            memberPrivacyentityRepository.delete(memberPrivacyentityRepository.findAllByMemberId(memberEndId.getId()));
            //删除用户信息表
            memberentityRepository.delete(memberEndId);

        }
    }

    /**
     * 签约设置保存 资料编辑 添加用户 ===> 签约成功 / 签约失败
     *
     * @param memberSignContractEntity             用户签约审核实体类
     * @param memberEntity                         用户基础信息表
     * @param memberPrivacyEntity                  用户私密信息表
     * @param memberPhotoFileMultipartFile         形象照
     * @param memberPhotoHeadPortraitMultipartFile 头像
     * @param memberPersonalDataMultipartFile      个人资料（可多个）
     * @param memberCardFrontMultipartFile         身份证正面
     * @param memberCardReverseSideMultipartFile   身份证反面
     * @param siginAgreementMultipartFile          签约协议（可多个）
     * @param delKey 删除的文件
     * @param request                              请求req
     * @return MemberSignContractEntity
     */
    @Override
    public MemberSignContractEntity signSetting(MemberSignContractEntity memberSignContractEntity, MemberEntity memberEntity, MemberPrivacyEntity memberPrivacyEntity,
                                                MultipartFile memberPhotoFileMultipartFile, MultipartFile memberPhotoHeadPortraitMultipartFile, MultipartFile[] memberPersonalDataMultipartFile,
                                                MultipartFile memberCardFrontMultipartFile, MultipartFile memberCardReverseSideMultipartFile, MultipartFile[] siginAgreementMultipartFile,String delKey,
                                                HttpServletRequest request) throws CodeGodRunTimExcetion, CodeGodException {
        //添加用户 memberSignContract 的Id为空
        MemberEntity jdbc_memberEntity = null;
        MemberPrivacyEntity jdbc_memberPrivacy = null;
        Optional<MemberSignContractEntity> byId = null;
        MemberSignContractEntity jdbc_SignContrac = null;
        if (ObjectUtils.isEmpty(memberSignContractEntity.getId())) {
            OperationRegionEntity byCityName = operationRegionRepository.findByCityName(memberEntity.getMemebrCity());
            //如果没有这个城市
            if (ObjectUtils.isEmpty(byCityName)) {
                OperationRegionEntity operationRegionEntity = new OperationRegionEntity();
                operationRegionEntity.setCreateTime(new Date());
                operationRegionEntity.setDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_BXS.getIndex());
                operationRegionEntity.setCityName(memberEntity.getMemebrCity());
                OperationRegionEntity save = operationRegionRepository.save(operationRegionEntity);
                memberEntity.setMemebrCityEntity(save);
            } else {
                memberEntity.setMemebrCityEntity(byCityName);
            }
            memberEntity.setCreateTime(new Date());
            //如果企业名称不为空 就设置先查找企业名称 然后保存所属企业
            if (!(ObjectUtils.isEmpty(memberEntity.getMemberCompany())) && !(StringUtils.isEmpty(memberEntity.getMemberCompany()))) {
                OperationCompanyEntity allByCompanyName = operationCompanyRepository.findAllByCompanyName(memberEntity.getMemberCompany());
                if (ObjectUtils.isEmpty(allByCompanyName)) {
                    //没有先创建 设置为不显示
                    allByCompanyName = new OperationCompanyEntity();
                    allByCompanyName.setCompanyName(memberEntity.getMemberCompany());
                    allByCompanyName.setCreateTime(new Date());
                    allByCompanyName.setCompanyDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_BXS.getIndex());
                    operationCompanyRepository.save(allByCompanyName);
                    memberEntity.setOperationCompanyEntity(allByCompanyName);
                } else {
                    //有就直接设置企业
                    memberEntity.setOperationCompanyEntity(allByCompanyName);
                }
            }

            jdbc_memberEntity = memberentityRepository.save(memberEntity);
            memberPrivacyEntity.setMemberId(jdbc_memberEntity.getId());
            jdbc_memberPrivacy = memberPrivacyentityRepository.save(memberPrivacyEntity);
            //形象照
            if (!memberPhotoFileMultipartFile.isEmpty()) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberPhotoFileMultipartFile));
                jdbc_memberPrivacy.setMemberPhotoFile(uploadFile);
            }
            //头像
            if (!(ObjectUtils.isEmpty(memberPhotoHeadPortraitMultipartFile)) && memberPhotoHeadPortraitMultipartFile.getSize() != 0) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberPhotoHeadPortraitMultipartFile));
                jdbc_memberPrivacy.setMemberPhotoHeadPortrait(uploadFile);
            }
            //个人资料（可多个）
            if (!ObjectUtils.isEmpty(memberPersonalDataMultipartFile) && memberPersonalDataMultipartFile.length > 0) {
                //循环获取file数组中得文件
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < memberPersonalDataMultipartFile.length; i++) {
                    MultipartFile file = memberPersonalDataMultipartFile[i];
                    //保存文件
                    if (!(ObjectUtils.isEmpty(file)) && file.getSize() != 0) {
                        UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                        sb.append(uploadFile.getId()).append(",");
//                            System.out.println(save.toString());
                    }
                }
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.delete(sb.length() - 1, sb.length());
                    jdbc_memberPrivacy.setMemberPersonalData(sb.toString());
                }
            }
            //身份证正面
            if (!(ObjectUtils.isEmpty(memberCardFrontMultipartFile)) && memberCardFrontMultipartFile.getSize() != 0) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberCardFrontMultipartFile));
                jdbc_memberPrivacy.setMemberCardFront(uploadFile);
            }
            //身份证反面
            if (!(ObjectUtils.isEmpty(memberCardReverseSideMultipartFile)) && memberCardReverseSideMultipartFile.getSize() != 0) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberCardReverseSideMultipartFile));
                jdbc_memberPrivacy.setMemberCardReverseSide(uploadFile);
            }
            //签约协议(可多个)
            if (siginAgreementMultipartFile != null && siginAgreementMultipartFile.length > 0) {
                //循环获取file数组中得文件
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < siginAgreementMultipartFile.length; i++) {
                    MultipartFile file = siginAgreementMultipartFile[i];
                    //保存文件
                    if (!(ObjectUtils.isEmpty(file)) && file.getSize() != 0) {
                        UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                        sb.append(uploadFile.getId()).append(",");
                    }
                }
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.delete(sb.length() - 1, sb.length());
                    jdbc_memberEntity.setMemberSigningAgreement(sb.toString());
                }
            }
        }
        if(!ObjectUtils.isEmpty(memberSignContractEntity.getId())){
            byId = memberSignContractentityRepository.findById(memberSignContractEntity.getId());
            if (ObjectUtils.isEmpty(byId)) {
                throw new CodeGodRunTimExcetion("Id 不存在", this.getClass());
            }
            //签约表
            jdbc_SignContrac = byId.get();
        }

        if (!(ObjectUtils.isEmpty(jdbc_SignContrac)) /*&& jdbc_SignContrac.getSiginResults() == HumanRecourcesStatus.MEMBERSIGNCONTRACT_SIGIN_RESULTS_DDSH.getIndex()*/) {
            //设置签约结果
            jdbc_SignContrac.setSiginResults(memberEntity.getMemberSigningStatus());

            //如果是待审核状态，此时用户已经在pc前端申请过了 所有 MemberSignContract 中的 memberEndId 是用户申请的信息
            jdbc_memberEntity = jdbc_SignContrac.getMemberEndId();
            //修改时间
            jdbc_memberEntity.setUpdateTime(new Date());
            jdbc_memberEntity.setNickName(memberEntity.getNickName());
            //设置城市
            jdbc_memberEntity.setMemebrCity(memberEntity.getMemebrCity());
            //用城市名字进行查询
            OperationRegionEntity byCityName = operationRegionRepository.findByCityName(memberEntity.getMemebrCity());
            //如果没有这个城市
            if (ObjectUtils.isEmpty(byCityName)) {
                OperationRegionEntity operationRegionEntity = new OperationRegionEntity();
                operationRegionEntity.setCreateTime(new Date());
                operationRegionEntity.setDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_BXS.getIndex());
                operationRegionEntity.setCityName(memberEntity.getMemebrCity());
                OperationRegionEntity save = operationRegionRepository.save(operationRegionEntity);
                jdbc_memberEntity.setMemebrCityEntity(save);
            } else {
                jdbc_memberEntity.setMemebrCityEntity(byCityName);
            }

            //如果企业名称不为空 就设置先查找企业名称 然后保存所属企业
            if (!(ObjectUtils.isEmpty(memberEntity.getMemberCompany())) && !(StringUtils.isEmpty(memberEntity.getMemberCompany()))) {
                OperationCompanyEntity allByCompanyName = operationCompanyRepository.findAllByCompanyName(memberEntity.getMemberCompany());
                if (ObjectUtils.isEmpty(allByCompanyName)) {
                    //没有先创建 设置为不显示
                    allByCompanyName = new OperationCompanyEntity();
                    allByCompanyName.setCompanyName(memberEntity.getMemberCompany());
                    allByCompanyName.setCreateTime(new Date());
                    allByCompanyName.setCompanyDisplay(OperationEnum.OPERATION_ENUM_REGION_DISPLAY_BXS.getIndex());
                    operationCompanyRepository.save(allByCompanyName);
                    jdbc_memberEntity.setOperationCompanyEntity(allByCompanyName);
                } else {
                    //有就直接设置企业
                    jdbc_memberEntity.setOperationCompanyEntity(allByCompanyName);
                }
            }


            jdbc_memberEntity.setMemberWord(memberEntity.getMemberWord());
            jdbc_memberEntity.setMemberIntroduce(memberEntity.getMemberIntroduce());
            jdbc_memberEntity.setMemberType(memberEntity.getMemberType());
            jdbc_memberEntity.setMemberSigningPost(memberEntity.getMemberSigningPost());
            jdbc_memberEntity.setMemberSigningMode(memberEntity.getMemberSigningMode());
            jdbc_memberEntity.setMemberMedal(memberEntity.getMemberMedal());
            jdbc_memberEntity.setMemberStationing(memberEntity.getMemberStationing());
            jdbc_memberEntity.setMemberStatus(memberEntity.getMemberStatus());
            jdbc_memberEntity.setMemberDisplay(memberEntity.getMemberDisplay());
            jdbc_memberEntity.setMemberSigningStatus(memberEntity.getMemberSigningStatus());
            jdbc_memberEntity.setMemberLongRange(memberEntity.getMemberLongRange());
            jdbc_memberEntity.setMemberOnSiteDevelopment(memberEntity.getMemberOnSiteDevelopment());
            //用户私人信息表
            jdbc_memberPrivacy = memberPrivacyentityRepository.findAllByMemberId(memberEntity.getId());
            jdbc_memberPrivacy.setMemberRealName(memberPrivacyEntity.getMemberRealName());
            jdbc_memberPrivacy.setMemberEmail(memberPrivacyEntity.getMemberEmail());
            jdbc_memberPrivacy.setMemberQq(memberPrivacyEntity.getMemberQq());
            jdbc_memberPrivacy.setMemberWechat(memberPrivacyEntity.getMemberWechat());
            jdbc_memberPrivacy.setMemberContactAddress(memberPrivacyEntity.getMemberContactAddress());
            jdbc_memberPrivacy.setMemberCashWithdrawal(memberPrivacyEntity.getMemberCashWithdrawal());
            jdbc_memberPrivacy.setMemberCardno(memberPrivacyEntity.getMemberCardno());
            jdbc_memberPrivacy.setMemberMobile(memberPrivacyEntity.getMemberMobile());
            jdbc_memberPrivacy.setMemberPwd(memberPrivacyEntity.getMemberPwd());

            //根据delKey删除字段属性
            if (!ObjectUtils.isEmpty(delKey)){
                String[] split = delKey.split(",");
                for (String s : split) {
                    if(!ObjectUtils.isEmpty(jdbc_memberPrivacy.getMemberPhotoFile())){
                        Long id = jdbc_memberPrivacy.getMemberPhotoFile().getId();
                        if(id.toString().equals(s)){
                            jdbc_memberPrivacy.setMemberPhotoFile(null);
                            continue;
                        }
                    }
                    if (!ObjectUtils.isEmpty(jdbc_memberPrivacy.getMemberPhotoHeadPortrait())){
                        Long id = jdbc_memberPrivacy.getMemberPhotoHeadPortrait().getId();
                        if(id.toString().equals(s)){
                            jdbc_memberPrivacy.setMemberPhotoHeadPortrait(null);
                            continue;
                        }
                    }
                    if(!ObjectUtils.isEmpty(jdbc_memberPrivacy.getMemberPersonalData())){
                        String[] split1 = jdbc_memberPrivacy.getMemberPersonalData().split(",");
                        StringBuilder sb = new StringBuilder();
                        for (String s1 : split1) {
                            if(!(s1.equals(s))){
                                sb.append(s1).append(",");
                            }
                        }

                        jdbc_memberPrivacy.setMemberPersonalData(sb.toString());
                        if(!ObjectUtils.isEmpty(jdbc_memberPrivacy.getMemberPersonalData()))
                            jdbc_memberPrivacy.setMemberPersonalData(jdbc_memberPrivacy.getMemberPersonalData().substring(0,jdbc_memberPrivacy.getMemberPersonalData().length()-1));
                    }

                    if(!(ObjectUtils.isEmpty(jdbc_memberPrivacy.getMemberCardFront()))){
                        Long id = jdbc_memberPrivacy.getMemberCardFront().getId();
                        if(s.equals(id.toString())){
                            jdbc_memberPrivacy.setMemberCardFront(null);
                            continue;
                        }
                    }
                    if(!ObjectUtils.isEmpty(jdbc_memberPrivacy.getMemberCardReverseSide())){
                        Long id = jdbc_memberPrivacy.getMemberCardReverseSide().getId();
                        if(s.equals(id.toString())){
                            jdbc_memberPrivacy.setMemberCardReverseSide(null);
                        }
                    }
                    if(!ObjectUtils.isEmpty(jdbc_memberEntity.getMemberSigningAgreement())){
                        String[] split1 = jdbc_memberEntity.getMemberSigningAgreement().split(",");
                        StringBuilder sb = new StringBuilder();
                        for (String s1 : split1) {
                            if(!(s1.equals(s))){
                                sb.append(s1).append(",");
                            }
                        }
                        jdbc_memberEntity.setMemberSigningAgreement(sb.toString());
                        if(!ObjectUtils.isEmpty(jdbc_memberEntity.getMemberSigningAgreement()))jdbc_memberEntity.setMemberSigningAgreement(jdbc_memberEntity.getMemberSigningAgreement().substring(0,jdbc_memberEntity.getMemberSigningAgreement().length()-1));
                    }
                }
            }
            //形象照
            if (!memberPhotoFileMultipartFile.isEmpty()) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberPhotoFileMultipartFile));
                jdbc_memberPrivacy.setMemberPhotoFile(uploadFile);
            }
            //头像
            if (!(ObjectUtils.isEmpty(memberPhotoHeadPortraitMultipartFile)) && memberPhotoHeadPortraitMultipartFile.getSize() != 0) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberPhotoHeadPortraitMultipartFile));
                jdbc_memberPrivacy.setMemberPhotoHeadPortrait(uploadFile);
            }
            //个人资料（可多个）
            if (!ObjectUtils.isEmpty(memberPersonalDataMultipartFile) && memberPersonalDataMultipartFile.length > 0) {
                //循环获取file数组中得文件
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < memberPersonalDataMultipartFile.length; i++) {
                    MultipartFile file = memberPersonalDataMultipartFile[i];
                    //保存文件
                    if (!(ObjectUtils.isEmpty(file)) && file.getSize() != 0) {
                        UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                        sb.append(uploadFile.getId()).append(",");
//                            System.out.println(save.toString());
                    }
                }
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.delete(sb.length() - 1, sb.length());
                    jdbc_memberPrivacy.setMemberPersonalData(sb.toString()+","+jdbc_memberPrivacy.getMemberPersonalData());
                }
            }
            //身份证正面
            if (!(ObjectUtils.isEmpty(memberCardFrontMultipartFile)) && memberCardFrontMultipartFile.getSize() != 0) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberCardFrontMultipartFile));
                jdbc_memberPrivacy.setMemberCardFront(uploadFile);
            }
            //身份证反面
            if (!(ObjectUtils.isEmpty(memberCardReverseSideMultipartFile)) && memberCardReverseSideMultipartFile.getSize() != 0) {
                UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(memberCardReverseSideMultipartFile));
                jdbc_memberPrivacy.setMemberCardReverseSide(uploadFile);
            }
            //签约协议(可多个)
            if (siginAgreementMultipartFile != null && siginAgreementMultipartFile.length > 0) {
                //循环获取file数组中得文件
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < siginAgreementMultipartFile.length; i++) {
                    MultipartFile file = siginAgreementMultipartFile[i];
                    //保存文件
                    if (!(ObjectUtils.isEmpty(file)) && file.getSize() != 0) {
                        UploadFile uploadFile = uploadFileRepository.save(saveFileUtils.saveFile(file));
                        sb.append(uploadFile.getId()).append(",");
                    }
                }
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.delete(sb.length() - 1, sb.length());
                    jdbc_memberEntity.setMemberSigningAgreement(sb.toString()+","+jdbc_memberEntity.getMemberSigningAgreement());
                }
            }

        }


        return memberSignContractEntity;

    }

    /**
     * 根据关键词进行分页
     *
     * @param validationCode 验证码
     * @param siginEnd       签约结果
     * @return Specification<MemberSignContractEntity>
     */
    private Specification<MemberSignContractEntity> getSpecification(Integer validationCode, Integer siginEnd) {
        return new Specification<MemberSignContractEntity>() {
            @Override
            public Predicate toPredicate(Root<MemberSignContractEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate preValida = !ObjectUtils.isEmpty(validationCode) ? criteriaBuilder.equal(root.get("siginVerificationCode"), validationCode) : criteriaBuilder.like(root.get("siginJobs"), "%");
                Predicate preSigin = !ObjectUtils.isEmpty(siginEnd) ? criteriaBuilder.equal(root.get("siginResults"), siginEnd) : criteriaBuilder.like(root.get("siginJobs"), "%");
                return criteriaBuilder.and(preValida, preSigin);
            }
        };
    }


}
