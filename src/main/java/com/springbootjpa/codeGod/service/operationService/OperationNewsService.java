package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/20 12:06
 */
public interface OperationNewsService {

    /**
     * 添加新闻
     * @param title 新闻标题
     * @param time 发布时间
     * @param views 访问量
     * @param content 内容
     * @param display 是否显示，0是，1否
     * @param request 请求req
     * @return
     */
    OperationNewsEntity addNews(String title, String time, Long views, String content, Integer display, HttpServletRequest request) throws ParseException;

    /**
     * 删除新闻，软删除，只是改变了状态
     * @param id 新闻id
     * @return
     */
    OperationNewsEntity deleteNews(Long id);

    /**
     * 修改新闻
     * @param id 新闻id
     * @param title 新闻新标题
     * @param views 访问量
     * @param content 内容
     * @param display 是否显示，0是1否
     * @return
     */
    OperationNewsEntity updateNews(Long id, String title, Long views, String content, Integer display);

}
