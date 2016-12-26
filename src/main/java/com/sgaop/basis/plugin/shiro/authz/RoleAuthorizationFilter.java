package com.sgaop.basis.plugin.shiro.authz;

import com.sgaop.basis.mvc.view.DefaultViewsRender;
import com.sgaop.basis.plugin.shiro.aop.BasisShiroInterceptorProxy;
import com.sgaop.basis.util.WebUtil;
import com.sgaop.common.WebPojo.Result;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class RoleAuthorizationFilter extends AuthorizationFilter {

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
            if (WebUtil.isAjax(httpRequest)) {
                DefaultViewsRender.RenderJSON(httpResponse, "", Result.error("您尚未登录或登录时间过长,请重新登录!"));
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (WebUtil.isAjax(httpRequest)) {
                DefaultViewsRender.RenderJSON(httpResponse, "", Result.error("您没有足够的权限执行该操作!"));
            } else {
                String unauthorizedUrl = getUnauthorizedUrl();
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(401);
                }
            }
        }
        return false;
    }

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        super.setUnauthorizedUrl(unauthorizedUrl);
        BasisShiroInterceptorProxy.unauthorizedUrl = unauthorizedUrl;
    }

}