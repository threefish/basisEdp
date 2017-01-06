package com.sgaop.action.news;

import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/4 0004
 * To change this template use File | Settings | File Templates.
 * 首页头图管理
 */
@IocBean
@Action("/operation/news/slide")
public class NewsIndexSlide {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:operation.news.slide.index")
    @GET
    @Path("/index")
    public void index() {

    }

}
