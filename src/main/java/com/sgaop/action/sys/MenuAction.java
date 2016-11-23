package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.util.DateUtil;
import com.sgaop.entity.sys.Menu;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/17 0017
 * To change this template use File | Settings | File Templates.
 * 菜单管理模块
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


    @OK("json")
    @POST
    @Path("/tree")
    public List<Menu> tree() {
        List<Menu> menus = dao.queryAll(Menu.class, "order by short_no asc");
        Menu menu = new Menu();
        menu.setId(0);
        menu.setPid(0);
        menu.setMenuName("根节点");
        menu.setLocked(false);
        menu.setDescription("");
        menu.setMenuTarget("");
        menus.add(menu);
        return menus;
    }

    @OK("json")
    @POST
    @Path("/update")
    public Result update(@Parameter("data>>") Menu menu) {
        Menu uMenu = dao.querySingePK(Menu.class, menu.getId());
        uMenu.setMenuName(menu.getMenuName());
        uMenu.setLocked(menu.isLocked());
        uMenu.setPid(menu.getPid());
        uMenu.setMenuTarget(menu.getMenuTarget());
        uMenu.setDescription(menu.getDescription());
        uMenu.setUpdateTime(new Timestamp(new Date().getTime()));
        try {
            dao.update(uMenu);
            return Result.sucess(menu, "修改成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @OK("json")
    @POST
    @Path("/add")
    public Result add(@Parameter("data>>") Menu menu) {
        try {
            menu.setCreateTime(new Timestamp(new Date().getTime()));
            menu.setMenuType(1);
            int id = dao.insert(menu);
            menu.setId(id);
            if (id > 0) {
                return Result.sucess(menu, "修改成功");
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
    public Result del(@Parameter("data>>") Menu menu) {
        try {
            Menu uMenu = dao.querySingePK(Menu.class, menu.getId());
            if(uMenu.getMenuType()==0){
                return Result.error("系统菜单不允许删除");
            }else{
                boolean flag = dao.delect(menu);
                return Result.sucess(menu, flag ? "删除成功" : "删除成功");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
