package com.springbootjpa.codeGod.service.operationService.Impl;

import com.springbootjpa.codeGod.codeException.CodeGodRunTimExcetion;
import com.springbootjpa.codeGod.entity.operation.OperationNewsEntity;
import com.springbootjpa.codeGod.eunm.OperationEnum;
import com.springbootjpa.codeGod.repository.Operation.OperationNewsRepository;
import com.springbootjpa.codeGod.service.operationService.OperationNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        OperationNewsEntity ne = operationNewsRepository.findByTitle(title);
        if(!ObjectUtils.isEmpty(ne)){
            throw new CodeGodRunTimExcetion("该新闻标题已存在", this.getClass());
        }
        //添加
        OperationNewsEntity newsEntity = new OperationNewsEntity();
        newsEntity.setTitle(title);
        newsEntity.setPublisher(request.getSession().getAttribute("user").toString());
        newsEntity.setContent(content);
        newsEntity.setState(OperationEnum.OPERATION_STATE_ZC.getIndex());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        if(ObjectUtils.isEmpty(time)){
            newsEntity.setPublishTime(sdf2.format(Calendar.getInstance().getTime()));
        }else {
            newsEntity.setPublishTime(sdf2.format(sdf1.parse(time)));
        }
        if(ObjectUtils.isEmpty(views)){
            newsEntity.setViews((long)OperationEnum.OPERATION_NEWS_VIEWS.getIndex());
        }else {
            newsEntity.setViews(views);
        }
        if(ObjectUtils.isEmpty(display)){
            newsEntity.setDisplay(OperationEnum.OPERATION_DISPLAY_YES.getIndex());
        }else {
            newsEntity.setDisplay(display);
        }
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
        Assert.notNull(id,"参数id不能为空");
        //查询要被删除的新闻
        OperationNewsEntity newsEntity = operationNewsRepository.findById(id).orElseThrow(()->new CodeGodRunTimExcetion("参数id错误，未查到匹配新闻",this.getClass()));
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
        if(ObjectUtils.isEmpty(id)){
            throw new CodeGodRunTimExcetion("参数id不能为空", this.getClass());
        }
        OperationNewsEntity newsEntity = operationNewsRepository.findByIdAndState(id, OperationEnum.OPERATION_STATE_ZC.getIndex());
        if(ObjectUtils.isEmpty(newsEntity)){
            throw new CodeGodRunTimExcetion("未查到匹配新闻", this.getClass());
        }
        log.info("勋章修改前：" + newsEntity.toString());

        //修改属性
        if (!ObjectUtils.isEmpty(title)) {
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
}
