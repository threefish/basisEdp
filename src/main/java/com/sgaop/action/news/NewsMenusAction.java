package com.sgaop.action.news;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.util.NewsMenuTree;
import com.sgaop.entity.news.NewsMenu;
import com.sgaop.entity.sys.Menu;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/17 0017
 * To change this template use File | Settings | File Templates.
 * 菜单管理模块
 */
@IocBean
@Action("/operation/news")
public class NewsMenusAction extends BaseAction {


    @Inject("dao")
    protected Dao dao;

    @OK("btl:operation.news.menus")
    @GET
    @Path("/menus")
    public List<HashMap> index() {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<NewsMenu> newsMenus = dao.query(NewsMenu.class, condition);
        return NewsMenuTree.createHashMap(newsMenus, 0);
    }




    @OK("btl:operation.news.child")
    @POST
    @Path("/child")
    public List<HashMap> child(@Parameter("pid") int pid) {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<NewsMenu> newsMenus = dao.query(NewsMenu.class, condition);
        return NewsMenuTree.createHashMap(newsMenus, pid);
    }


    @OK("json")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<NewsMenu> newsMenuList = dao.query(NewsMenu.class, pager);
        int count = dao.count(NewsMenu.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(newsMenuList);
        return dataResult;
    }


    @OK("json:{ignoreNull:false,locked:'createTime|updateTime'}")
    @POST
    @Path("/tree")
    public List<NewsMenu> tree() {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<NewsMenu> newsMenus = dao.query(NewsMenu.class, condition);
        NewsMenu newsMenu = new NewsMenu();
        newsMenu.setId(0);
        newsMenu.setPid(0);
        newsMenu.setMenuName("新闻首页");
        newsMenu.setLocked(false);
        newsMenu.setDescription("");
        newsMenu.setMenuTarget("");
        newsMenus.add(newsMenu);
        return newsMenus;
    }

    @OK("json:{ignoreNull:false,locked:'createTime|updateTime'}")
    @POST
    @Path("/cktree")
    public List<NewsMenu> treeList() {
        Condition condition = new Condition();
        condition.and("locked", "=", false);
        condition.asc("short_no");
        List<NewsMenu> menus = dao.query(NewsMenu.class, condition);
        return NewsMenuTree.createTree(menus,0);
    }

    @OK("json")
    @POST
    @Path("/modify")
    public Result modify(@Parameter("id") int id, @Parameter("action") String action) {
        NewsMenu uNewsMenu = dao.fetch(NewsMenu.class, id);
        try {
            switch (action) {
                case "lock":
                    uNewsMenu.setLocked(true);
                    dao.update(uNewsMenu);
                    return Result.sucess(uNewsMenu, "修改成功");
                case "unlock":
                    uNewsMenu.setLocked(false);
                    dao.update(uNewsMenu);
                    return Result.sucess(uNewsMenu, "修改成功");
            }
            return Result.error("参数不符");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    @OK("json")
    @POST
    @Path("/update")
    public Result update(@Parameter("data>>") NewsMenu newsMenu) {
        if (newsMenu.getPid() != 0 && newsMenu.getId() == newsMenu.getPid()) {
            return Result.error("不能选择自己作为自己的上级菜单");
        }
        NewsMenu uNewsMenu = dao.fetch(NewsMenu.class, newsMenu.getId());
        uNewsMenu.setMenuName(newsMenu.getMenuName());
        uNewsMenu.setLocked(newsMenu.isLocked());
        uNewsMenu.setPid(newsMenu.getPid());
        uNewsMenu.setMenuTarget(newsMenu.getMenuTarget());
        uNewsMenu.setMenuIcon(newsMenu.getMenuIcon());
        uNewsMenu.setMenuType(newsMenu.getMenuType());
        uNewsMenu.setPermission(newsMenu.getPermission());
        uNewsMenu.setDescription(newsMenu.getDescription());
        uNewsMenu.setUpdateTime(new Timestamp(new Date().getTime()));
        try {
            dao.update(uNewsMenu);
            return Result.sucess(newsMenu, "修改成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @OK("json")
    @POST
    @Path("/move")
    public Result move(@Parameter("id") int id, @Parameter("type") String type) {
        if (!StringsTool.isNullorEmpty(type)) {
            NewsMenu uNewsMenu = dao.fetch(NewsMenu.class, id);
            //取出同级菜单
            Condition cnd = new Condition();
            cnd.and("pid", "=", uNewsMenu.getPid());
            cnd.asc("short_no");
            List<NewsMenu> newsMenuList = dao.query(NewsMenu.class, cnd);
            //重新整理顺序
            List<NewsMenu> oldNewsMenuList = new ArrayList<>();
            for (int i = 0; i < newsMenuList.size(); i++) {
                NewsMenu newsMenu = newsMenuList.get(i);
                newsMenu.setShortNo(i);
                oldNewsMenuList.add(newsMenu);
            }
            //上移
            if ("up".equals(type)) {
                //升级后的菜单
                List<NewsMenu> upNewsMenuList = new ArrayList<>();
                for (NewsMenu newsMenu : oldNewsMenuList) {
                    if (newsMenu.getId() == id) {
                        if (newsMenu.getShortNo() == 0) {
                            return Result.error("已经是置顶了！");
                        } else {
                            newsMenu.setShortNo(newsMenu.getShortNo() - 1);
                        }
                    }
                    upNewsMenuList.add(newsMenu);
                }
                Collections.sort(upNewsMenuList, new NewsMenu());
                //重新整理顺序
                List<NewsMenu> newNewsMenuList = new ArrayList<>();
                for (int i = 0; i < upNewsMenuList.size(); i++) {
                    NewsMenu newsMenu = upNewsMenuList.get(i);
                    newsMenu.setShortNo(i);
                    newNewsMenuList.add(newsMenu);
                }
                dao.update(newNewsMenuList);
            } else {//下移
                //降级级后的菜单
                List<NewsMenu> upNewsMenuList = new ArrayList<>();
                int last = 1;
                for (NewsMenu newsMenu : oldNewsMenuList) {
                    if (newsMenu.getId() == id) {
                        if (last == oldNewsMenuList.size()) {
                            return Result.error("已经是置底了！");
                        } else {
                            newsMenu.setShortNo(newsMenu.getShortNo() + 1);
                        }
                    }
                    last++;
                    upNewsMenuList.add(newsMenu);
                }
                Collections.sort(upNewsMenuList, new NewsMenu());
                //重新整理顺序
                List<NewsMenu> newNewsMenuList = new ArrayList<>();
                for (int i = 0; i < upNewsMenuList.size(); i++) {
                    NewsMenu newsMenu = upNewsMenuList.get(i);
                    newsMenu.setShortNo(i);
                    newNewsMenuList.add(newsMenu);
                }
                dao.update(newNewsMenuList);
            }
        }
        return Result.sucess("修改成功");
    }

    @OK("json")
    @POST
    @Path("/add")
    public Result add(@Parameter("data>>") NewsMenu newsMenu) {
        try {
            newsMenu.setCreateTime(new Timestamp(new Date().getTime()));
            int id = dao.insert(newsMenu);
            newsMenu.setId(id);
            if (id > 0) {
                return Result.sucess(newsMenu, "添加成功");
            } else {
                return Result.error("未知原因");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @OK("json")
    @POST
    @Path("/del")
    public Result del(@Parameter("data>>") NewsMenu newsMenu) {
        try {
            NewsMenu uNewsMenu = dao.fetch(NewsMenu.class, newsMenu.getId());
            if (uNewsMenu.isCanDelect()) {
                List<NewsMenu> newsMenuList = dao.query(NewsMenu.class, "pid", uNewsMenu.getId());
                if (newsMenuList.size() == 0) {
                    boolean flag = dao.delete(newsMenu);
                    return Result.sucess(newsMenu, flag ? "删除成功" : "删除成功");
                } else {
                    return Result.error("当前菜单下还有子菜单不允许删除");
                }
            } else {
                return Result.error("系统菜单不允许删除");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
