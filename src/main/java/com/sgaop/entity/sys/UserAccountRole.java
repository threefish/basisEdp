package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.Pk;
import com.sgaop.basis.annotation.Table;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/30 0030
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_useraccount_role")
@Pk({"role_id", "user_id"})
public class UserAccountRole {

    @Colum("role_id")
    private int roleId;

    @Colum("user_id")
    private int userId;

    public UserAccountRole() {
    }

    public UserAccountRole(int roleId, int userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
