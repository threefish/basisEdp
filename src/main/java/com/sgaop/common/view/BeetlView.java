package com.sgaop.common.view;

import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.basis.mvc.view.View;
import org.apache.log4j.Logger;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.web.SessionWrapper;
import org.beetl.ext.web.WebVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/9/13 0013
 * To change this template use File | Settings | File Templates.
 * 自定义的Beetl视图
 */
public class BeetlView implements View {

    private static final Logger logger = Logger.getRootLogger();
    private final static String _prefix = "/_view_/";
    private final static String _suffix = ".html";
    private static GroupTemplate gt = null;

    static {
        try {
            WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            resourceLoader.setCharset("utf-8");
            resourceLoader.setRoot(Mvcs.getSession().getServletContext().getRealPath(_prefix));
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(String path, HttpServletRequest request, HttpServletResponse response, Object data) {
        try {
            Template tpl = gt.getTemplate(path + _suffix);
            if (data instanceof Map) {
                tpl.binding("data", data, false);
            } else {
                tpl.binding("data", data, true);
            }
            Enumeration<String> attrs = request.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String attrName = attrs.nextElement();
                tpl.binding(attrName, request.getAttribute(attrName));
            }
            WebVariable webVariable = new WebVariable();
            webVariable.setRequest(request);
            webVariable.setResponse(response);
            webVariable.setSession(request.getSession());
            tpl.binding("session", new SessionWrapper(webVariable.getSession()));
            tpl.binding("servlet", webVariable);
            tpl.binding("request", request);
            tpl.binding("ctxPath", request.getContextPath());
            tpl.binding("base", request.getContextPath());

            OutputStream out = response.getOutputStream();
            tpl.renderTo(out);
            out.flush();
        } catch (IOException e) {
            handleClientError(e);
        }
    }

    /**
     * 处理客户端抛出的IO异常
     *
     * @param ex
     */
    protected void handleClientError(IOException ex) {
        logger.error(ex);
    }
}
