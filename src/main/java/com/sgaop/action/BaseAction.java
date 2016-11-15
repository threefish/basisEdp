package com.sgaop.action;

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

    protected static final Logger log = Logger.getRootLogger();

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    protected ServletContext servletContext;

}