package com.sgaop.action;

import com.sgaop.basis.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/8 0008
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/main")
public class MainAction {

    @OK("beetl:index")
    @GET
    @Path("/index")
    public void index(){}
}
