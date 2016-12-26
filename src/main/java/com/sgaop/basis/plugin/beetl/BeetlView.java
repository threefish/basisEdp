package com.sgaop.basis.plugin.beetl;

import com.sgaop.basis.i18n.LanguageManager;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.basis.mvc.view.View;
import com.sgaop.common.beetl.function.DateFunction;
import com.sgaop.basis.plugin.shiro.beetl.ShiroExt;
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
import java.nio.file.Paths;
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
            HttpSession session = Mvcs.getSession();
            ClassLoader classLoader = session.getServletContext().getClassLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
            resourceLoader.setCharset("utf-8");
            resourceLoader.setRoot(session.getServletContext().getRealPath("/"));
            cfg.add(Paths.get(classLoader.getResource("/view/beetl.properties").toURI()).toFile());
            gt = new GroupTemplate(resourceLoader, cfg);
            gt.registerFunctionPackage("so", new ShiroExt());
            gt.registerFunction("dateTime", new DateFunction());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void render(String path, HttpServletRequest request, HttpServletResponse response, Object data) {
        try {
            Template tpl = gt.getTemplate(path.replace(".", File.separator) + _suffix);
            if (data instanceof Map) {
                tpl.binding("data", data, false);
            } else {
                tpl.binding("data", data, true);
            }
            HashMap<String, String> langMap = LanguageManager.getMap(Mvcs.getI18nLang());
            tpl.binding("i18n", langMap);

            tpl.binding("lang", Mvcs.getI18nLang());

            Enumeration<String> attrs = request.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String attrName = attrs.nextElement();
                tpl.binding(attrName, request.getAttribute(attrName));
            }
            WebVariable webVariable = new WebVariable();
            webVariable.setRequest(request);
            webVariable.setResponse(response);
            webVariable.setSession(request.getSession());
            tpl.binding("session", new SessionWrapper(request, webVariable.getSession()));
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

    @Override
    public void afterProcess(HttpServletRequest request, HttpServletResponse response) {
        //设置盒式布局
        String layBoxed = Mvcs.getCookie("layBoxed");
        if (layBoxed != null) {
            request.setAttribute("layBoxed", "layout-boxed");
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
