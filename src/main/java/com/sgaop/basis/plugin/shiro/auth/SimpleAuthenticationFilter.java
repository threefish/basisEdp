package com.sgaop.basis.plugin.shiro.auth;

import com.sgaop.basis.plugin.shiro.aop.BasisShiroInterceptorProxy;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/23 0023
 * To change this template use File | Settings | File Templates.
 */
public class SimpleAuthenticationFilter extends AuthenticationFilter {

    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
        return false;
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (pathsMatch(getLoginUrl(), request))
            return true;
        return super.isAccessAllowed(request, response, mappedValue);
    }

    public void setLoginUrl(String loginUrl) {
        super.setLoginUrl(loginUrl);
        BasisShiroInterceptorProxy.DefaultLoginURL = loginUrl;
    }
}
