package com.sgaop.action.setting;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.mvc.Mvcs;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/22 0022
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/setting/icon")
@RequiresAuthentication
public class IconAction extends BaseAction{

    @OK("btl:inc.icon")
    @GET
    @Path("/index")
    public void index(@Parameter("domid")String domid) {
        request.setAttribute("domid", domid);
    }
}
