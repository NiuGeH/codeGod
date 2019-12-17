package com.springbootjpa.codeGod.common;

import com.springbootjpa.codeGod.utils.AesUtils;
import com.springbootjpa.codeGod.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Ajax工具类
 */
@PropertySource({"classpath:application.properties","classpath:application-dev.properties"})
@Component
public class AjaxUtils {

    public static String RSA_PUBLICAKEY ;

    @Value("${RSA_PUBLICAKEY}")
    public void setUrl(String RSA_PUBLICAKEY) {
        AjaxUtils.RSA_PUBLICAKEY = RSA_PUBLICAKEY;
    }

    private static AesUtils aesUtils = new AesUtils();

    /**
     * 处理ajax结果
     *
     * @param action
     * @param <T>
     * @return
     */
    public static <T> AjaxResult<T> process(Func_T<T> action) {
        AjaxResult<T> ajaxResult = new AjaxResult<T>();
        ajaxResult.setSuccess(false);
        try {
            T result = action.invoke();
            String s = JSONUtils.beanToJson(result);
            //上线或前后端连调时放开
//            result = (T)aesUtils.enCode(s,RSA_PUBLICAKEY);
//            System.out.println(result.toString());
            ajaxResult.setSuccess(true);
            ajaxResult.setCode(200);
            ajaxResult.setData(result);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMessage(e.getMessage());
        }

        return ajaxResult;
    }

    /**
     * 处理列表ajax结果
     *
     * @param page
     * @param action
     * @param <T>
     * @return
     */
    public static <T> PageResult<T> process(PageRequestParam page, Sort sort, Func_T1<Pageable, Page<T>> action) {
        PageResult<T> pageResult = new PageResult<T>();
        try {
            Pageable pageable = new PageRequest(page.getPage() - 1, page.getRows(), sort);
            Page<T> pageList = action.invoke(pageable);
            pageResult.setRecords(pageList.getTotalElements());
            pageResult.setPage(page.getPage());
            if (pageList.getContent() == null) {
                pageResult.setRows(new ArrayList<>());
                pageResult.setRowsPublic("");
            } else {
                pageResult.setRows(pageList.getContent());
                //加密 上线或前后端对调放开
//                pageResult.setRowsPublic(aesUtils.enCode(JSONUtils.beanToJson(pageResult.getRows()),RSA_PUBLICAKEY));
            }
            if (pageResult.getRecords() % page.getRows() == 0) {
                pageResult.setTotal(pageResult.getRecords() / page.getRows());
            } else {
                pageResult.setTotal(pageResult.getRecords() / page.getRows() + 1);
            }

        } catch (Exception e) {
            pageResult.setRows(new ArrayList<>());
            pageResult.setTotal(0l);
            e.printStackTrace();
        }
        //上线或测试放开
//        pageResult.setRows(new ArrayList<>());
        return pageResult;
    }
}
