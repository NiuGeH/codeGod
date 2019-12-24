package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import com.springbootjpa.codeGod.entity.operation.OperationResourceEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/23 17:56
 */
public interface OperationResourceService {

    /**
     * 添加资源类型
     * @param name 资源类型名称
     * @param resourcePhotoFile 上传的图标文件
     * @param amount 人数
     * @param order 资源排序
     * @param skillMap 关联技术栈map
     * @param display 是否显示：0是，1否
     * @return
     */
    OperationResourceEntity addResource(String name, MultipartFile resourcePhotoFile, Long amount, Long order, Map<Long,String> skillMap, Integer display) throws CodeGodException;
}
