package com.sgaop.action;

import com.sgaop.basis.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/11 0011
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/test")
public class TestAction {

    @OK("beetl:test")
    @GET
    @Path("/index")
    public void index() {

    }
}
