package com.sgaop.action.news;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.basis.mvc.view.DownFile;
import com.sgaop.common.cons.Cons;
import com.sgaop.common.util.NewsMenuTree;
import com.sgaop.entity.news.NewsContent;
import com.sgaop.entity.news.NewsMenu;
import com.sgaop.entity.sys.FileAttach;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/news")
public class NewsAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:news.add")
    @GET
    @Path("/add")
    public void addPage() {
        request.setAttribute("UE_ALL_TOOL", Cons.UE_ALL_TOOL);
    }


    @OK("btl:news.index")
    @GET
    @Path("/index.html")
    public void index() {
        Condition condition = new Condition();
        condition.and("locked", "=", false);
        condition.asc("short_no");
        List<NewsMenu> newsMenus = NewsMenuTree.createTree(dao.query(NewsMenu.class, condition), 0);
        session.setAttribute("indexMenu", newsMenus);

    }

    @OK("btl:news.newslist")
    @GET
    @Path("/newslist")
    public void newslist(@Parameter("id")int id) {
        Condition condition = new Condition();
        condition.and("locked", "=", false);
        condition.asc("short_no");
        List<NewsMenu> menus= dao.query(NewsMenu.class, condition);
        List<NewsMenu> newsMenus = NewsMenuTree.createTree(menus, 0);
        NewsMenu news= dao.fetch(NewsMenu.class, id);
        session.setAttribute("indexMenu", newsMenus);
        request.setAttribute("menus", NewsMenuTree.createTree(menus, news.getPid()));
        request.setAttribute("menu", news);

        Condition cc = new Condition();
        cc.and("menuid", "=", id);
        cc.and("status", "=", 1);
        cc.desc("toped");
        List<NewsContent> newsContents=dao.query(NewsContent.class,cc);
        request.setAttribute("newsList", newsContents);

    }

    @OK("btl:news.newsinfo")
    @GET
    @Path("/newsInfo")
    public void newsInfo(@Parameter("id") int id) {
        NewsContent news = dao.fetch(NewsContent.class, id);
        request.setAttribute("news", news);
    }

    /**
     * 新闻
     *
     * @param id
     * @return
     */
    @Path("/newsAttachAct")
    @OK("raw")
    @POST
    @GET
    public DownFile newsAttachAct(@Parameter("id") int id) {
        Condition condition = new Condition();
        condition.and("attachid", "=", id);
        condition.and("attachtype", "=", "news");
        FileAttach attach = dao.fetch(FileAttach.class, condition);
        return new DownFile(attach.getAttachfilename(), new File(Mvcs.getServletContext().getRealPath("/") + attach.getSavedpath()));
    }


}
