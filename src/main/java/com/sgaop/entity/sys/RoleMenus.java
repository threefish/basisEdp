package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.Table;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/30 0030
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_role_menu")
public class RoleMenus {

    @Colum("role_id")
    private int roleId;

    @Colum("menu_id")
    private int menuId;

    public RoleMenus(){}

    public RoleMenus(int roleId, int menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
