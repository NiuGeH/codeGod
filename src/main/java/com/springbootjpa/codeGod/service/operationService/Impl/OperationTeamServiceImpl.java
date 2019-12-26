package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationTeamEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationRegionRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationTeamRepository;
import com.springbootjpa.codeGod.service.operationService.OperationTeamService;
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

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 18:19
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationTeamServiceImpl implements OperationTeamService {

    @Autowired
    private OperationTeamRepository operationTeamRepository;

    @Autowired
    private OperationRegionRepository operationRegionRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

    /**
     * 添加服务团队
     * @param cityId 所属城市id
     * @param teamName 团队名称
     * @param teamPhone 团队电话
     * @param teamEmail 团队邮箱
     * @param teamAddress 团队地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param state 状态
     * @param remark 备注或口号
     * @return
     */
    @Override
    public OperationTeamEntity addTeam(Long cityId, String teamName, String teamPhone, String teamEmail, String teamAddress, String longitude, String latitude, Integer state, String remark) {
        //参数验证
        if(ObjectUtils.isEmpty(teamName)) throw new CodeGodRunTimExcetion("团队名称不能为空", this.getClass());

        //判断团队名称是否存在
        OperationTeamEntity teamEntity = operationTeamRepository.findByTeamName(teamName);
        if(!ObjectUtils.isEmpty(teamEntity)) throw new CodeGodRunTimExcetion("该团队名称已存在", this.getClass());

        //添加
        OperationTeamEntity team = new OperationTeamEntity();
        team.setTeamName(teamName);
        team.setRegion(operationRegionRepository.findById(cityId).get());
        team.setTeamPhone(teamPhone);
        team.setTeamEmail(teamEmail);
        team.setTeamAddress(teamAddress);
        team.setLongitude(longitude);
        team.setLatitude(latitude);
        team.setRemark(remark);
        if(ObjectUtils.isEmpty(state)){
            team.setState(OperationEnum.OPERATION_STATE_ZC.getIndex());
        }else {
            team.setState(state);
        }
        team.setCreateTime(Calendar.getInstance().getTime());

        //保存
        operationTeamRepository.save(team);

        return team;
    }

    /**
     * 修改服务团队
     * @param teamId 团队id
     * @param cityId 所属城市id
     * @param teamName 团队名称
     * @param teamPhone 团队电话
     * @param teamEmail 团队邮箱
     * @param teamAddress 团队地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param state 状态
     * @param remark 备注或口号
     * @return
     */
    @Override
    public OperationTeamEntity updateTeam(Long teamId, Long cityId, String teamName, String teamPhone, String teamEmail, String teamAddress, String longitude, String latitude, Integer state, String remark) {
        //参数验证
        if(ObjectUtils.isEmpty(teamId)) throw new CodeGodRunTimExcetion("需要修改的团队id不能为空", this.getClass());
        if(ObjectUtils.isEmpty(teamName)) throw new CodeGodRunTimExcetion("团队名称不能为空", this.getClass());
        //查询需要修改的团队
        OperationTeamEntity teamEntity = operationTeamRepository.findById(teamId).orElseThrow(()->new CodeGodRunTimExcetion("需要修改的团队id可能错误，没有查到服务团队", this.getClass()));
        //修改
        if (!teamEntity.getTeamName().equals(teamName)) {
            OperationTeamEntity te = operationTeamRepository.findByTeamName(teamName);
            if(!ObjectUtils.isEmpty(te)) throw new CodeGodRunTimExcetion("该团队名称已存在",this.getClass());
            teamEntity.setTeamName(teamName);
        }
        teamEntity.setRegion(operationRegionRepository.findById(cityId).get());
        teamEntity.setTeamPhone(teamPhone);
        teamEntity.setTeamEmail(teamEmail);
        teamEntity.setTeamAddress(teamAddress);
        teamEntity.setLongitude(longitude);
        teamEntity.setLatitude(latitude);
        teamEntity.setRemark(remark);
        if(ObjectUtils.isEmpty(state)){
            teamEntity.setState(OperationEnum.OPERATION_STATE_ZC.getIndex());
        }else {
            teamEntity.setState(state);
        }
        teamEntity.setModifyTime(Calendar.getInstance().getTime());
        //保存
        operationTeamRepository.save(teamEntity);

        return teamEntity;
    }

    /**
     * 查询全部服务团队分页
     * @param pageable
     * @return
     */
    @Override
    public Page<OperationTeamEntity> findAll(Pageable pageable) {
        Specification<OperationTeamEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Page<OperationTeamEntity> all = operationTeamRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationTeamEntity operationTeamEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationTeamEntity.getState().toString(), DataBaseFinal.OPERATION_TEAM_STATE);
                operationTeamEntity.setStateStr(bdd.getDataValue());
            }
        }
        return all;
    }
}
