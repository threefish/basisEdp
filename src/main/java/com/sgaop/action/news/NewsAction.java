package com.sgaop.action.news;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.mvc.AjaxResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.cons.Cons;
import com.sgaop.common.gather.*;
import com.sgaop.entity.sys.AlarmOption;
import com.sgaop.task.ApmJob;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hyperic.sigar.Sigar;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/news")
public class NewsAction extends BaseAction {


    @OK("btl:news.add")
    @GET
    @Path("/add")
    public void addPage() {
        request.setAttribute("UE_ALL_TOOL", Cons.UE_ALL_TOOL);
    }

}
