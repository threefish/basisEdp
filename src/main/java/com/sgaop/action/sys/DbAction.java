package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/22 0022
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/monitor/db")
@RequiresRoles("admin")
public class DbAction extends BaseAction {

    @OK("btl:sys.db.index")
    @GET
    @Path("/dashboard")
    public void dashboard() {
    }

    @Path("/sqlDetail")
    @GET
    @OK("btl:sys.db.sqlDetail")
    public void sqlDetail(@Parameter("sqlId") int sqlId) {
        request.setAttribute("sqlId", sqlId);
    }

    @Path("/connectionPool")
    @GET
    @OK("btl:sys.db.connectionPool")
    public void connectionPool(@Parameter("id") int id) {
        request.setAttribute("id", id);
    }

    @Path("/sessionDetail")
    @GET
    @OK("btl:sys.db.sessionDetail")
    public void sessionDetail(@Parameter("sessionId") String sessionId) {
        request.setAttribute("sessionId", sessionId);
    }

    @Path("/uriDetail")
    @GET
    @OK("btl:sys.db.uriDetail")
    public void uriDetail(@Parameter("uri") String uri) {
        request.setAttribute("uri", uri);
    }

}
