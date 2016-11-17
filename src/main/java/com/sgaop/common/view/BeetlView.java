package com.sgaop.common.view;

import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.basis.mvc.view.View;
import com.sgaop.common.shiro.beetl.ShiroExt;
import com.sun.tools.doclets.standard.Standard;
import org.apache.log4j.Logger;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.web.SessionWrapper;
import org.beetl.ext.web.WebVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
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
    private final static String _suffix = ".html";
    private static GroupTemplate gt = null;

    static {
        try {
            HttpSession  session= Mvcs.getSession();
            Configuration cfg = Configuration.defaultConfiguration();
            WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
            resourceLoader.setCharset("utf-8");
            resourceLoader.setRoot(session.getServletContext().getRealPath("/"));
            File file= new File(session.getServletContext().getClassLoader().getResource("/view/beetl.properties").toURI().getPath());
            cfg.add(file);
            gt = new GroupTemplate(resourceLoader, cfg);
            gt.registerFunctionPackage("so", new ShiroExt());
            HashMap sysinfo=new HashMap();
            sysinfo.put("productName","BasisMVC企业平台");
            sysinfo.put("productNameMiNi","Basis");
            sysinfo.put("productUrl","www.sgaop.com");
            gt.setSharedVars(sysinfo);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void render(String path, HttpServletRequest request, HttpServletResponse response, Object data) {
        try {
            Template tpl = gt.getTemplate(path.replace(".",File.separator)+_suffix);
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
            tpl.binding("session", new SessionWrapper(request,webVariable.getSession()));
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
