package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.BaseDataDictionaryEntity;
import com.springbootjpa.codeGod.entity.operation.OperationCommentEntity;
import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.fnalclass.DataBaseFinal;
import com.springbootjpa.codeGod.repository.BaseDataDictionaryentityRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationCommentRepository;
import com.springbootjpa.codeGod.repository.Operation.OperationNewsRepository;
import com.springbootjpa.codeGod.service.operationService.OperationNewsService;
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
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lixin
 * @version 1.0
 * @description TODO
 * @date 2019/12/20 12:28
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class OperationNewsServiceImpl implements OperationNewsService {

    @Autowired
    private OperationNewsRepository operationNewsRepository;

    @Autowired
    private OperationCommentRepository operationCommentRepository;

    @Autowired
    private BaseDataDictionaryentityRepository baseDataDictionaryentityRepository;

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
    @Override
    public OperationNewsEntity addNews(String title, String time, Long views, String content, Integer display, HttpServletRequest request) throws ParseException {
        //验证参数
        if(ObjectUtils.isEmpty(title)){
            throw new CodeGodRunTimExcetion("新闻标题不能为空",this.getClass());
        }

        //判断类型是否已存在
        OperationNewsEntity ne = operationNewsRepository.findByTitleAndState(title, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(!ObjectUtils.isEmpty(ne)){
            throw new CodeGodRunTimExcetion("该新闻标题已存在", this.getClass());
        }
        //添加
        OperationNewsEntity newsEntity = new OperationNewsEntity();
        //新闻标题
        newsEntity.setTitle(title);
        //发布人
        newsEntity.setPublisher(request.getSession().getAttribute("user").toString());
        //内容
        newsEntity.setContent(content);
        //状态
        newsEntity.setState(OperationEnum.OPERATION_STATE_ZC.getIndex());
        //发布时间
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        if(ObjectUtils.isEmpty(time)){
            newsEntity.setPublishTime(sdf2.format(Calendar.getInstance().getTime()));
        }else {
            newsEntity.setPublishTime(sdf2.format(sdf1.parse(time)));
        }
        //访问量
        newsEntity.setViews(views);
        //是否显示
        if(ObjectUtils.isEmpty(display)){
            newsEntity.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            newsEntity.setDisplay(display);
        }
        //创建时间
        newsEntity.setCreateTime(Calendar.getInstance().getTime());

        //保存
        operationNewsRepository.save(newsEntity);

        return newsEntity;
    }

    /**
     * 删除新闻，软删除，只是改变了状态
     * @param id 新闻id
     * @return
     */
    @Override
    public OperationNewsEntity deleteNews(Long id) {
        //验证参数
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("新闻id不能为空",this.getClass());
        //查询要被删除的新闻
        OperationNewsEntity newsEntity = operationNewsRepository.findByIdAndState(id, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(ObjectUtils.isEmpty(newsEntity)) throw new CodeGodRunTimExcetion("新闻id错误或者该新闻已删除",this.getClass());
        //修改被删除新闻的状态
        newsEntity.setState(OperationEnum.OPERATION_STATE_SC.getIndex());
        newsEntity.setModifyTime(Calendar.getInstance().getTime());

        return newsEntity;
    }

    /**
     * 修改新闻
     * @param id 新闻id
     * @param title 新闻新标题
     * @param views 访问量
     * @param content 内容
     * @param display 是否显示，0是1否
     * @return
     */
    @Override
    public OperationNewsEntity updateNews(Long id, String title, Long views, String content, Integer display) {
        //查询需要修改的新闻
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("新闻id不能为空",this.getClass());
        if(ObjectUtils.isEmpty(title)) throw new CodeGodRunTimExcetion("新闻标题不能为空",this.getClass());
        OperationNewsEntity newsEntity = operationNewsRepository.findByIdAndState(id, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(ObjectUtils.isEmpty(newsEntity)) throw new CodeGodRunTimExcetion("新闻id错误或者该新闻已删除",this.getClass());
        log.info("新闻修改前：" + newsEntity.toString());

        //修改属性
        if (!newsEntity.getTitle().equals(title)) {
            OperationNewsEntity ne = operationNewsRepository.findByTitleAndState(title, OperationEnum.OPERATION_STATE_ZC.getIndex());
            if(!ObjectUtils.isEmpty(ne)) throw new CodeGodRunTimExcetion("该新闻标题已存在",this.getClass());
            newsEntity.setTitle(title);
        }
        newsEntity.setViews(views);
        newsEntity.setContent(content);
        if (!ObjectUtils.isEmpty(display)) {
            newsEntity.setDisplay(display);
        }
        newsEntity.setModifyTime(Calendar.getInstance().getTime());

        //保存
        operationNewsRepository.save(newsEntity);

        return newsEntity;
    }

    /**
     * 查询未被删除的全部新闻
     * @return
     */
    @Override
    public Page<OperationNewsEntity> findAllNews(Pageable pageable) {
        Specification<OperationNewsEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("state"),String.valueOf(OperationEnum.OPERATION_STATE_ZC.getIndex()));
            }
        };

        Page<OperationNewsEntity> all = operationNewsRepository.findAll(specification, pageable);
        if(!ObjectUtils.isEmpty(all) && all.getSize()>0){
            for(OperationNewsEntity operationNewsEntity : all){
                BaseDataDictionaryEntity bdd = baseDataDictionaryentityRepository.findDistinctByDataColumnNameAndAndDataKey(operationNewsEntity.getDisplay().toString(), DataBaseFinal.OPERATION_NEWS_DISPLAY);
                operationNewsEntity.setDisplayStr(bdd.getDataValue());
            }
        }
        return all;
    }

    /**
     * 添加评论
     * @param newsId 关联的新闻id
     * @param content 评论内容
     * @param request 请求req
     * @return
     */
    @Override
    public OperationCommentEntity addComment(Long newsId, String content, HttpServletRequest request) {
        //验证参数
        if(ObjectUtils.isEmpty(newsId)) throw new CodeGodRunTimExcetion("关联的新闻id不能为空",this.getClass());
        if(ObjectUtils.isEmpty(content)){
            throw new CodeGodRunTimExcetion("评论内容不能为空",this.getClass());
        }

        //添加
        OperationCommentEntity commentEntity = new OperationCommentEntity();
        OperationNewsEntity newsEntity = operationNewsRepository.findByIdAndState(newsId, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(ObjectUtils.isEmpty(newsEntity)) throw new CodeGodRunTimExcetion("新闻id错误或者该新闻已删除",this.getClass());
        commentEntity.setNews(newsEntity);
        commentEntity.setContent(content);
        commentEntity.setState(OperationEnum.OPERATION_STATE_ZC.getIndex());
        commentEntity.setCommentator(request.getSession().getAttribute("user").toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date now = Calendar.getInstance().getTime();
        commentEntity.setPublishTime(sdf.format(now));
        commentEntity.setCreateTime(now);

        //保存
        operationCommentRepository.save(commentEntity);

        return commentEntity;
    }

    /**
     * 修改评论
     * @param id 评论id
     * @param content 评论内容
     * @return
     */
    @Override
    public OperationCommentEntity updateComment(Long id, String content) {
        //查询需要修改的评论
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("评论id不能为空",this.getClass());
        OperationCommentEntity commentEntity = operationCommentRepository.findByIdAndState(id, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(ObjectUtils.isEmpty(commentEntity)) throw new CodeGodRunTimExcetion("评论id错误或者该评论已删除",this.getClass());
        log.info("评论修改前：" + commentEntity.toString());

        //修改
        commentEntity.setContent(content);
        commentEntity.setModifyTime(Calendar.getInstance().getTime());

        //保存
        operationCommentRepository.save(commentEntity);

        return commentEntity;
    }

    /**
     * 删除评论，软删除，只是改变了状态
     * @param id 评论id
     * @return
     */
    @Override
    public OperationCommentEntity deleteComment(Long id) {
        //验证参数
        if(ObjectUtils.isEmpty(id)) throw new CodeGodRunTimExcetion("评论id不能为空",this.getClass());
        //查询要被删除的评论
        OperationCommentEntity commentEntity = operationCommentRepository.findByIdAndState(id, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(ObjectUtils.isEmpty(commentEntity)) throw new CodeGodRunTimExcetion("评论id错误或者该评论已删除",this.getClass());
        //修改
        commentEntity.setState(OperationEnum.OPERATION_STATE_SC.getIndex());
        commentEntity.setModifyTime(Calendar.getInstance().getTime());

        return commentEntity;
    }

    /**
     * 根据新闻id查询未被删除的所有评论；若新闻id为空，查询未被删除的全部评论
     * @param pageable
     * @param newsId 新闻id
     * @return
     */
    @Override
    public Page<OperationCommentEntity> findAllComment(Pageable pageable, Long newsId) {
        Specification<OperationCommentEntity> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate pre1 = null;
                if(!ObjectUtils.isEmpty(newsId)){
                    OperationNewsEntity newsEntity = operationNewsRepository.findByIdAndState(newsId, OperationEnum.OPERATION_STATE_ZC.getIndex());
                    if(ObjectUtils.isEmpty(newsEntity)) throw new CodeGodRunTimExcetion("新闻id错误或者该新闻已删除",this.getClass());
                    pre1 = criteriaBuilder.equal(root.get("news").get("id"), newsId.toString());
                }else {
                    pre1 = criteriaBuilder.equal(root.get("news").get("state"), String.valueOf(OperationEnum.OPERATION_STATE_ZC.getIndex()));
                }
                Predicate pre2 = criteriaBuilder.equal(root.get("state"), String.valueOf(OperationEnum.OPERATION_STATE_ZC.getIndex()));
                return criteriaBuilder.and(pre1, pre2);
            }
        };
        return operationCommentRepository.findAll(specification, pageable);
    }
}
