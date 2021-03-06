package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.dao.entity.Record;
import com.sgaop.basis.trans.TransAop;
import com.sgaop.basis.util.RecordUtil;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.aop.LogsAop;
import com.sgaop.common.util.MenuTree;
import com.sgaop.entity.sys.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequiresRoles("admin")//只有admin角色组才能访问本模块
public class RoleAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.role.manager")
    @GET
    @Path("/manager")
    @RequiresPermissions("sys.yw.role.manager")
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
    @RequiresPermissions("sys.yw.role.add")
    @LogsAop.Slog(tag = "after", msg = "添加角色：[{1}]!")
    public Result add(@Parameter("data>>") Role role,
                      @Parameter("data.roleName")String roleName) {
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
    @RequiresPermissions("sys.yw.role.update")
    @LogsAop.Slog(tag = "after", msg = "修改角色：[{1}]!ID=[{2}] ")
    public Result update(@Parameter("data>>") Role role,
                         @Parameter("data.roleName") String roleName,
                         @Parameter("data.id") int id
    ) {
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
    @RequiresPermissions("sys.yw.role.changelock")
    @LogsAop.Slog(tag = "after", msg = "启用角色：ID=[{0}] ")
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
    @RequiresPermissions("sys.yw.role.changelock")
    @LogsAop.Slog(tag = "after", msg = "禁用角色：ID=[{0}] ")
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
    @Aop(TransAop.READ_UNCOMMITTED)
    @RequiresPermissions("sys.yw.role.del")
    @LogsAop.Slog(tag = "after", msg = "删除角色：ID=[{0}] ")
    public Result del(@Parameter("id") int id) {
        Role role = dao.fetch(Role.class, id);
        if (role == null) {
            return Result.error("角色不存在！");
        }
        if ("admin".equals(role.getRoleCode())) {
            return Result.error("admin管理员组不能删除");
        }
        Condition condition = new Condition();
        condition.and("role_id", "=", id);
        List<RoleMenus> roleMenuses = dao.query(RoleMenus.class, condition);
        dao.delete(roleMenuses);
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
        return MenuTree.createTree(menus, 0);
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
            menus = MenuTree.createTree(menus, 0);
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
    @RequiresPermissions("sys.yw.role.authorization")
    @LogsAop.Slog(tag = "after", msg = "更新角色：ID=[{0}] ")
    public Result roleMenusUpdate(@Parameter("id") int roleId, @Parameter("ids") int[] ids) {
        Role role = dao.fetch(Role.class, roleId);
        if (role == null) {
            return Result.error("角色不存在");
        }
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

    /**
     * 为角色添加用户
     *
     * @return
     */
    @OK("json")
    @POST
    @Path("/roleUsers/addUser")
    @Aop(TransAop.READ_UNCOMMITTED)
    @RequiresPermissions("sys.yw.role.authuser")
    @LogsAop.Slog(tag = "after", msg = "为ID=[{0}]的角色添加[{1}]用户")
    public Result roleUsersAddUser(@Parameter("roleId") int roleId, @Parameter("ids") int[] ids) {
        Role role = dao.fetch(Role.class, roleId);
        if (role == null) {
            return Result.error("角色不存在");
        }
        Condition cnd = new Condition();
        cnd.and("role_id", "=", roleId);
        List<UserAccountRole> oldRoleUsers = dao.query(UserAccountRole.class, cnd);
        List<Integer> integers = new ArrayList<>();
        for (int i = 0, len = ids.length; i < len; i++) {
            boolean b = true;
            sw:
            for (UserAccountRole accountRole : oldRoleUsers) {
                if (accountRole.getUserId() == ids[i]) {
                    b = false;
                    break sw;
                }
            }
            if (b) {
                integers.add(ids[i]);
            }
        }
        List<UserAccountRole> newRoleUsers = new ArrayList<>();
        for (int i = 0, len = integers.size(); i < len; i++) {
            newRoleUsers.add(new UserAccountRole(roleId, integers.get(i)));
        }
        dao.insert(newRoleUsers);
        return Result.sucess("添加成功");
    }

    /**
     * 为角色删除用户
     *
     * @return
     */
    @OK("json")
    @POST
    @Path("/roleUsers/delUser")
    @Aop(TransAop.READ_UNCOMMITTED)
    @RequiresPermissions("sys.yw.role.delauthuser")
    @LogsAop.Slog(tag = "after", msg = "为ID=[{0}]的角色删除[{1}]用户")
    public Result roleUsersDelUser(@Parameter("roleId") int roleId, @Parameter("ids") int[] ids) {
        Role role = dao.fetch(Role.class, roleId);
        if (role == null) {
            return Result.error("角色不存在");
        }
        List<UserAccountRole> accountRoles = new ArrayList<>();
        for (int i = 0, len = ids.length; i < len; i++) {
            accountRoles.add(new UserAccountRole(roleId, ids[i]));
        }
        dao.delete(accountRoles);
        return Result.sucess("删除成功");
    }

    /**
     * 按角色分配人员
     *
     * @return
     */
    @OK("btl:sys.role.privileges")
    @GET
    @Path("/roleMenus/privileges")
    public void privileges(@Parameter("id") int roleId) {
        Role role = dao.fetch(Role.class, roleId);
        request.setAttribute("role", role);
    }

    /**
     * 按角色查询已分配人员
     *
     * @return
     */
    @OK("json:{locked:'userPass|salt',ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/roleUsers/RoleUsersGrid")
    public DataTableResult RoleUsersGrid(@Parameter("roleId") int roleId, @Parameter("name") String name, @Parameter("status") int status) {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<Record> records = dao.query("SELECT user_id FROM sys_useraccount_role WHERE role_id=?", roleId);
        String userids = "";
        for (Record record : records) {
            userids += record.getInt("user_id") + ",";
        }
        Condition cnd = new Condition();
        cnd.strs("where FIND_IN_SET(id,?)", userids);

        if (!StringsTool.isNullorEmpty(name)) {
            cnd.and("userName", "like", "%" + name + "%");
        }
        if (status != -1) {
            cnd.and("locked", "=", status);
        }
        List<UserAccount> userAccounts = dao.query(UserAccount.class, pager, cnd);
        int count = dao.count(UserAccount.class, cnd);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }

}
