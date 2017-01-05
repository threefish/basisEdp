package com.sgaop.action;

import com.sgaop.basis.util.Logs;
import com.sgaop.common.cons.Cons;
import com.sgaop.entity.sys.UserAccount;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/15 0015
 * To change this template use File | Settings | File Templates.
 */
public class BaseAction {

    protected static final Logger log = Logs.get();

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    protected ServletContext servletContext;

    protected UserAccount getUserAccount() {
        return (UserAccount) session.getAttribute(Cons.SESSION_USER);
    }


    /**
     * 给request设置临时值
     *
     * @param requestAttr
     * @param val
     */
    protected void setAtrr(String requestAttr, Object val) {
        request.setAttribute(requestAttr, val);
    }

    /**
     * 给session设置值
     *
     * @param sessionAttr
     * @param val
     */
    protected void setSessionAtrr(String sessionAttr, Object val) {
        request.getSession().setAttribute(sessionAttr, val);
    }

    /**
     * 获取session值
     *
     * @param sessionAttr
     */
    protected Object getSessionAtrr(String sessionAttr) {
        return request.getSession().getAttribute(sessionAttr);
    }

}
