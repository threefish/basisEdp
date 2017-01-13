package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.util.MenuTree;
import com.sgaop.entity.sys.Menu;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@Action("/sysMenu")
@RequiresRoles("admin") //只有admin角色组才能访问本模块
public class MenuAction extends BaseAction {


    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.menu.index")
    @GET
    @Path("/index")
    public List<HashMap> index() {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<Menu> menus = dao.query(Menu.class, condition);
        return MenuTree.createHashMap(menus, 0);
    }

    @OK("btl:sys.menu.child")
    @POST
    @Path("/child")
    public List<HashMap> child(@Parameter("pid") int pid) {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<Menu> menus = dao.query(Menu.class, condition);
        return MenuTree.createHashMap(menus, pid);
    }


    @OK("json")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<Menu> menuList = dao.query(Menu.class, pager);
        int count = dao.count(Menu.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(menuList);
        return dataResult;
    }


    @OK("json:{ignoreNull:false,locked:'createTime|updateTime'}")
    @POST
    @Path("/tree")
    public List<Menu> tree() {
        Condition condition = new Condition();
        condition.asc("short_no");
        List<Menu> menus = dao.query(Menu.class, condition);
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
    @Path("/modify")
    @RequiresPermissions("sys.yw.menu.modify")
    public Result modify(@Parameter("id") int id, @Parameter("action") String action) {
        Menu uMenu = dao.fetch(Menu.class, id);
        try {
            switch (action) {
                case "lock":
                    uMenu.setLocked(true);
                    dao.update(uMenu);
                    return Result.sucess(uMenu, "修改成功");
                case "unlock":
                    uMenu.setLocked(false);
                    dao.update(uMenu);
                    return Result.sucess(uMenu, "修改成功");
            }
            return Result.error("参数不符");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    @OK("json")
    @POST
    @Path("/update")
    @RequiresPermissions("sys.yw.menu.update")
    public Result update(@Parameter("data>>") Menu menu) {
        if (menu.getPid() != 0 && menu.getId() == menu.getPid()) {
            return Result.error("不能选择自己作为自己的上级菜单");
        }
        Menu uMenu = dao.fetch(Menu.class, menu.getId());
        uMenu.setMenuName(menu.getMenuName());
        uMenu.setLocked(menu.isLocked());
        uMenu.setPid(menu.getPid());
        uMenu.setMenuTarget(menu.getMenuTarget());
        uMenu.setMenuIcon(menu.getMenuIcon());
        uMenu.setMenuType(menu.getMenuType());
        uMenu.setPermission(menu.getPermission());
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
    @Path("/move")
    @RequiresPermissions("sys.yw.menu.short")
    public Result move(@Parameter("id") int id, @Parameter("type") String type) {
        if (!StringsTool.isNullorEmpty(type)) {
            Menu uMenu = dao.fetch(Menu.class, id);
            //取出同级菜单
            Condition cnd = new Condition();
            cnd.and("pid", "=", uMenu.getPid());
            cnd.asc("short_no");
            List<Menu> menuList = dao.query(Menu.class, cnd);
            //重新整理顺序
            List<Menu> oldMenuList = new ArrayList<>();
            for (int i = 0; i < menuList.size(); i++) {
                Menu menu = menuList.get(i);
                menu.setShortNo(i);
                oldMenuList.add(menu);
            }
            //上移
            if ("up".equals(type)) {
                //升级后的菜单
                List<Menu> upMenuList = new ArrayList<>();
                for (Menu menu : oldMenuList) {
                    if (menu.getId() == id) {
                        if (menu.getShortNo() == 0) {
                            return Result.error("已经是置顶了！");
                        } else {
                            menu.setShortNo(menu.getShortNo() - 1);
                        }
                    }
                    upMenuList.add(menu);
                }
                Collections.sort(upMenuList, new Menu());
                //重新整理顺序
                List<Menu> newMenuList = new ArrayList<>();
                for (int i = 0; i < upMenuList.size(); i++) {
                    Menu menu = upMenuList.get(i);
                    menu.setShortNo(i);
                    newMenuList.add(menu);
                }
                dao.update(newMenuList);
            } else {//下移
                //降级级后的菜单
                List<Menu> upMenuList = new ArrayList<>();
                int last = 1;
                for (Menu menu : oldMenuList) {
                    if (menu.getId() == id) {
                        if (last == oldMenuList.size()) {
                            return Result.error("已经是置底了！");
                        } else {
                            menu.setShortNo(menu.getShortNo() + 1);
                        }
                    }
                    last++;
                    upMenuList.add(menu);
                }
                Collections.sort(upMenuList, new Menu());
                //重新整理顺序
                List<Menu> newMenuList = new ArrayList<>();
                for (int i = 0; i < upMenuList.size(); i++) {
                    Menu menu = upMenuList.get(i);
                    menu.setShortNo(i);
                    newMenuList.add(menu);
                }
                dao.update(newMenuList);
            }
        }
        return Result.sucess("修改成功");
    }

    @OK("json")
    @POST
    @Path("/add")
    @RequiresPermissions("sys.yw.menu.add")
    public Result add(@Parameter("data>>") Menu menu) {
        try {
            menu.setCreateTime(new Timestamp(new Date().getTime()));
            int id = dao.insert(menu);
            menu.setId(id);
            if (id > 0) {
                return Result.sucess(menu, "添加成功");
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
    @RequiresPermissions("sys.yw.menu.del")
    public Result del(@Parameter("data>>") Menu menu) {
        try {
            Menu uMenu = dao.fetch(Menu.class, menu.getId());
            if (uMenu.isCanDelect()) {
                List<Menu> menuList = dao.query(Menu.class, "pid", uMenu.getId());
                if (menuList.size() == 0) {
                    boolean flag = dao.delete(menu);
                    return Result.sucess(menu, flag ? "删除成功" : "删除成功");
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
