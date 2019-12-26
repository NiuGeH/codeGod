package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationTeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 17:56
 */
public interface OperationTeamService {

    /**
     * 添加服务团队
     * @param cityId 所属城市id
     * @param teamName 团队名称
     * @param teamPhone 团队电话
     * @param teamEmail 团队邮箱
     * @param teamAddress 团队地址
     * @param longitude 经度
     * @param latitude 纬度
     * @param display 是否显示：0是，1否
     * @param remark 备注或口号
     * @return
     */
    OperationTeamEntity addTeam(Long cityId, String teamName, String teamPhone, String teamEmail, String teamAddress, String longitude, String latitude, Integer display, String remark);

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
     * @param display 是否显示：0是，1否
     * @param remark 备注或口号
     * @return
     */
    OperationTeamEntity updateTeam(Long teamId, Long cityId, String teamName, String teamPhone, String teamEmail, String teamAddress, String longitude, String latitude, Integer display, String remark);

    /**
     * 查询全部服务团队分页
     * @param pageable
     * @return
     */
    Page<OperationTeamEntity> findAll(Pageable pageable);
}
