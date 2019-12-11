package com.springbootjpa.codeGod.shiro;

import com.springbootjpa.codeGod.entity.sys.SysUsersEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
            return super.onAccessDenied(request, response);

    }

    /**
     * 重写 登录成功后跳转页面
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        SysUsersEntity userEntity = (SysUsersEntity) SecurityUtils.getSubject().getPrincipal();
//        session.setAttribute("roleName", userEntity.getRoleId().getDescription());
//        System.err.println(userEntity.getRoleId().getDescription());
        WebUtils.issueRedirect(request, response,"/tex",null,true);
    }
}
