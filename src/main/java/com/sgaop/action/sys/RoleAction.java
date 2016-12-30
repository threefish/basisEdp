package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.dao.entity.Record;
import com.sgaop.basis.trans.TransAop;
import com.sgaop.basis.util.RecordUtil;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.util.Tree;
import com.sgaop.entity.sys.Menu;
import com.sgaop.entity.sys.Role;
import com.sgaop.entity.sys.RoleMenus;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/27 0027
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysRole")
@RequiresRoles("admin")
public class RoleAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.role.manager")
    @GET
    @Path("/manager")
    public void manager() {
    }


    @OK("json:{ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<Role> userAccounts = dao.query(Role.class, pager);
        int count = dao.count(Role.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }

    @OK("json")
    @POST
    @Path("/add")
    public Result add(@Parameter("data>>") Role role) {
        Condition cnd = new Condition();
        cnd.and("role_name", "=", role.getRoleName());
        cnd.or("role_code", "=", role.getRoleCode());
        Role oldrole = dao.fetch(Role.class, cnd);
        if (oldrole != null) {
            return Result.error("角色名称或角色编码已存在");
        }
        dao.insert(role);
        return Result.sucess("添加成功");
    }

    @OK("json")
    @POST
    @Path("/update")
    public Result update(@Parameter("data>>") Role role) {
        Condition cnd = new Condition();
        cnd.and("id", "!=", role.getId());

        Condition cnd1 = new Condition();
        cnd1.and("role_name", "=", role.getRoleName());
        cnd1.or("role_code", "=", role.getRoleCode());
        cnd.and(cnd1);

        Role oldrole = dao.fetch(Role.class, cnd);
        if (oldrole != null) {
            return Result.error("角色名称或角色编码已存在");
        }
        dao.update(role);
        return Result.sucess("添加成功");
    }

    @OK("json")
    @POST
    @Path("/unlock")
    public Result unlock(@Parameter("id") int id) {
        Role role = dao.fetch(Role.class, id);
        if (role == null) {
            return Result.error("角色不存在！");
        }
        role.setLocked(false);
        dao.update(role);
        return Result.sucess("启用成功");
    }


    @OK("json")
    @POST
    @Path("/lock")
    public Result lock(@Parameter("id") int id) {
        Role role = dao.fetch(Role.class, id);
        if (role == null) {
            return Result.error("角色不存在！");
        }
        role.setLocked(true);
        dao.update(role);
        return Result.sucess("禁用成功");
    }

    @OK("json")
    @POST
    @Path("/del")
    public Result del(@Parameter("id") int id) {
        Role role = dao.fetch(Role.class, id);
        if (role == null) {
            return Result.error("角色不存在！");
        }
        dao.delete(role);
        return Result.sucess("删除成功");
    }


    @OK("json")
    @POST
    @Path("/info")
    public Result info(@Parameter("id") int id) {
        Role role = dao.fetch(Role.class, id);
        if (role == null) {
            return Result.error("角色不存在！");
        }
        return Result.sucess(role);
    }


    @OK("json:{ignoreNull:false,locked:'createTime|updateTime'}")
    @POST
    @Path("/menus/tree")
    public List<Menu> menusTree() {
        Condition condition = new Condition();
        condition.and("locked", "=", false);
        condition.asc("short_no");
        List<Menu> menus = dao.query(Menu.class, condition);
        return Tree.createTree(menus, 0);
    }

    /**
     * 取得当前角色拥有的菜单或数据按钮的ID列表
     *
     * @return
     */
    @OK("json:{ignoreNull:false}")
    @POST
    @Path("/roleMenus")
    public Result roleMenus(@Parameter("id") int roleId) {
        List<RoleMenus> list = dao.query(RoleMenus.class, "role_id", roleId);
        return Result.sucess(list);
    }

    /**
     * 取得当前角色拥有的菜单或数据按钮
     *
     * @return
     */
    @OK("json:{ignoreNull:false}")
    @POST
    @GET
    @Path("/menus/showRoleTree")
    public List<Menu> showRoleTree(@Parameter("id") int roleId) {
        List<Menu> menus = new ArrayList<>();
        try {
            List<Record> records = dao.query("SELECT * from sys_menu m,sys_role_menu r WHERE r.menu_id=m.id and r.role_id=?", roleId);
            menus = RecordUtil.toEntity(Menu.class, records);
            menus = Tree.createTree(menus, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }

    /**
     * 更新当前角色权限
     *
     * @return
     */
    @OK("json")
    @POST
    @Path("/roleMenus/update")
    @Aop(TransAop.READ_UNCOMMITTED)
    public Result roleMenusUpdate(@Parameter("id") int roleId, @Parameter("ids") int[] ids) {
        Role role = dao.fetch(Role.class, roleId);
        if (role == null) {
            return Result.error("角色不存在");
        }
        List<RoleMenus> oldRolelist = dao.query(RoleMenus.class, "role_id", roleId);
        Condition cnd = new Condition();
        cnd.and("role_id", "=", roleId);
        dao.delete(RoleMenus.class, cnd);
        List<RoleMenus> newRolelist = new ArrayList<>();
        for (int i = 0, len = ids.length; i < len; i++) {
            newRolelist.add(new RoleMenus(roleId, ids[i]));
        }
        dao.insert(newRolelist);
        return Result.sucess("操作成功");
    }

}
