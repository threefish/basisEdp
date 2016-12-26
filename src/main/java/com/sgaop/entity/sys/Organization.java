package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/26 0026
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_organization")
public class Organization implements Serializable, Comparator<Organization> {

    private static final long serialVersionUID = 1L;

    @ID
    @Colum
    private int id;

    @Colum
    private int pid;

    @Colum
    private String name;

    @Colum("short_name")
    private String shortName;

    @Colum("locked")
    private boolean locked;

    @Colum("orgIcon")
    private String orgIcon;

    private String iconSkin;

    @Colum("description")
    private String description;

    @Colum("short_no")
    private int shortNo;

    @Colum("create_userid")
    private int createUserid;

    @Colum("update_userid")
    private int updateUserid;

    @Colum("ct")
    private Timestamp createTime;

    @Colum("ut")
    private Timestamp updateTime;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(int createUserid) {
        this.createUserid = createUserid;
    }

    public int getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(int updateUserid) {
        this.updateUserid = updateUserid;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getOrgIcon() {
        return orgIcon;
    }

    public void setOrgIcon(String orgIcon) {
        this.orgIcon = orgIcon;
        this.iconSkin = "fa " + orgIcon + " ";
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShortNo() {
        return shortNo;
    }

    public void setShortNo(int shortNo) {
        this.shortNo = shortNo;
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

    @Override
    public int compare(Organization o0, Organization o1) {
        if (o0.getShortNo() > o1.getShortNo()) {
            return 0;
        } else if (o0.getShortNo() == o1.getShortNo()) {
            return -1;
        } else {
            return -1;
        }
    }
}
