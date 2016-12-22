package com.sgaop.action.sys;

import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysOrg")
public class OrganizationAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.org.index")
    @GET
    @Path("/index")
    public void index() {
    }

}
