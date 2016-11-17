package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.entity.sys.Menu;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/17 0017
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysMenu")
public class MenuAction extends BaseAction {


    @Inject("dao")
    protected Dao dao;

    @OK("beetl:sys.menu.index")
    @GET
    @Path("/index")
    public void index() {
    }


    @OK("json")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(Mvcs.getReq());
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<Menu> menuList = dao.queryCndList(Menu.class, pager);
        int count = dao.queryCndListCount(Menu.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(Mvcs.getReq().getParameter("draw")));
        dataResult.setData(menuList);
        return dataResult;
    }

}
