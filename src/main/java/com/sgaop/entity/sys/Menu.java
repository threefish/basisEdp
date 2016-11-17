package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.Pk;
import com.sgaop.basis.annotation.Table;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/11 0011
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_menu")
public class Menu {

    @Pk
    @Colum
    private int id;

    @Colum
    private int pid;

    @Colum("menu_name")
    private String menuName;

    @Colum("menu_target")
    private String menuTarget;

    @Colum("menu_type")
    private int menuType;

    @Colum("description")
    private String description;

    @Colum("short_no")
    private int shortNo;

    @Colum("ct")
    private Timestamp createTime;

    @Colum("ut")
    private Timestamp updateTime;

    private List<Menu> childs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<Menu> getChilds() {
        return childs;
    }

    public int getShortNo() {
        return shortNo;
    }

    public void setShortNo(int shortNo) {
        this.shortNo = shortNo;
    }

    public void setChilds(List<Menu> childs) {
        this.childs = childs;
    }
}
