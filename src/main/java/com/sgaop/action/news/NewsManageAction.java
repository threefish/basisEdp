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
import com.sgaop.common.cons.Cons;
import com.sgaop.entity.news.NewsContent;
import com.sgaop.entity.sys.UserAccount;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/4 0004
 * To change this template use File | Settings | File Templates.
 * 首页
 */
@IocBean
@Action("/operation/news/manage")
public class NewsManageAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:operation.news.management")
    @GET
    @Path("/index")
    public void index() {

    }

    @OK("json:{ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/dataList")
    public DataTableResult dataList(@Parameter("status") int status,
                                    @Parameter("title") String title,
                                    @Parameter("menuType") int menuid,
                                    @Parameter("toped") int toped
    ) {
        Condition condition = new Condition();
        if (status != -1) {
            condition.and("status", "=", status);
        }
        if (toped != -1) {
            condition.and("toped", "=", toped);
        }
        if (!StringsTool.isNullorEmpty(title)) {
            condition.and("title", "like", "%" + title + "%");
        }
        if (menuid != 0) {
            condition.and("menuid", "=", menuid);
        }
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<NewsContent> userAccounts = dao.query(NewsContent.class, pager, condition);
        int count = dao.count(NewsContent.class, condition);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }

    @OK("btl:operation.news.edit")
    @GET
    @Path("/edit")
    public void edit(@Parameter("id") int id) {
        NewsContent newsContent = dao.fetch(NewsContent.class, id);
        request.setAttribute("news", newsContent);
        request.setAttribute("UE_ALL_TOOL", Cons.UE_ALL_TOOL);
    }


    @OK("json")
    @POST
    @Path("/addOrUpdate")
    public Result addOrUpdate(@Parameter("data>>") NewsContent news) {
        UserAccount userAccount = (UserAccount) session.getAttribute(Cons.SESSION_USER);
        if (news.getId() == 0) {
            news.setCreateUser(userAccount.getUserName());
            news.setCreateTime(new Timestamp(new Date().getTime()));
            news.setStatus(0);
            dao.insert(news);
            return Result.sucess("保存成功");
        } else {
            NewsContent newsContent = dao.fetch(NewsContent.class, news.getId());
            newsContent.setForm(news.getForm());
            newsContent.setMenuid(news.getMenuid());
            newsContent.setMenuName(news.getMenuName());
            newsContent.setContent(news.getContent());
            newsContent.setTitle(news.getTitle());
            newsContent.setImgPath(news.getImgPath());
            dao.update(newsContent);
            return Result.sucess("修改成功");
        }
    }

    @OK("json")
    @POST
    @Path("/del")
    public Result del(@Parameter("id") int id) {
        NewsContent newsContent = dao.fetch(NewsContent.class, id);
        if (newsContent == null) {
            return Result.error("新闻不存在");
        }
        dao.delete(newsContent);
        return Result.sucess("删除成功");
    }

}
