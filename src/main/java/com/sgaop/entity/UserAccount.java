package com.sgaop.entity;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Pk;
import com.sgaop.basis.annotation.Table;

import java.sql.Timestamp;

/**
 * Created by 30695 on 2016/9/24 0024.
 */
@Table("UserAccount")
public class UserAccount {

    @ID
    @Colum
    private int id;

    @Colum
    private String userName;

    @Colum
    private String userPass;

    @Colum
    private boolean locked;

    @Colum
    private Timestamp createTime;

    @Colum
    private String salt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
