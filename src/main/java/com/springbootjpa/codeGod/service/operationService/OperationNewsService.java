package com.springbootjpa.codeGod.service.operationService;

import com.springbootjpa.codeGod.entity.operation.OperationCommentEntity;
import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * 查询未被删除的全部新闻，分页
     * @return
     */
    Page<OperationNewsEntity> findAllNews(Pageable pageable);

    /**
     * 添加评论
     * @param newsId 关联的新闻id
     * @param content 评论内容
     * @param request 请求req
     * @return
     */
    OperationCommentEntity addComment(Long newsId, String content, HttpServletRequest request);

    /**
     * 修改评论
     * @param id 评论id
     * @param content 评论内容
     * @return
     */
    OperationCommentEntity updateComment(Long id, String content);

    /**
     * 删除评论，软删除，只是改变了状态
     * @param id 评论id
     * @return
     */
    OperationCommentEntity deleteComment(Long id);

    /**
     * 根据新闻id查询未被删除的所有评论；若新闻id为空，查询未被删除的全部评论
     * @param pageable
     * @param newsId 新闻id
     * @return
     */
    Page<OperationCommentEntity> findAllComment(Pageable pageable, Long newsId);

}
