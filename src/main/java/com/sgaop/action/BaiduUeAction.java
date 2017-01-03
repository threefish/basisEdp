package com.sgaop.action;

import com.baidu.ueditor.ActionHandler;
import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.storage.StorageManager;
import com.baidu.ueditor.storage.impl.LocalStorageService;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.mvc.Mvcs;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/3 0003
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/ue")
@RequiresAuthentication
public class BaiduUeAction extends BaseAction {

    @GET
    @POST
    @Path("/controller")
    @OK("raw")
    public void controller(@Parameter("action")String action, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //屏蔽其他全部方法
        if("config".equals(action)){
            ServletContext application = Mvcs.getServletContext();
            ActionHandler actionHandler = null;
            String cachedKey = "ueditorActionHandler";
            Object cachedActionHandler = application.getAttribute(cachedKey);
            if (cachedActionHandler instanceof ActionHandler) {
                actionHandler = (ActionHandler) cachedActionHandler;
            } else {
                String contextPath = request.getContextPath();
                String rootPath = request.getServletContext().getRealPath("/");
                String currentPath = request.getRequestURI();
                if (contextPath.length() > 0) {
                    currentPath = currentPath.replaceFirst(contextPath, "/");
                }
                int lastPost = currentPath.lastIndexOf("/", currentPath.length() - 1);
                if (lastPost >= 0) {
                    currentPath = currentPath.substring(0, lastPost);
                }
                String configPath = null;
                try {
                    configPath = BaiduUeAction.class.getResource("/ueditor/config.json").toURI().getPath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                ConfigManager configManager = new ConfigManager(rootPath, contextPath, new FileInputStream(configPath));
                StorageManager storageManager = new StorageManager(new LocalStorageService());
                actionHandler = new ActionHandler(configManager, storageManager);
                application.setAttribute(cachedKey, actionHandler);
            }
            actionHandler.handle(request, response);
        }
    }
}
