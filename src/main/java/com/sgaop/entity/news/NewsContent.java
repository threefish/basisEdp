package com.sgaop.entity.news;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Table;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/4 0004
 * To change this template use File | Settings | File Templates.
 */
@Table("tb_new_content")
public class NewsContent {
    @ID
    @Colum
    private int id;
    @Colum
    private int menuid;
    @Colum
    private int viewed;
    @Colum//1发布 0暂停发布
    private int status;
    @Colum
    private boolean toped;
    @Colum
    private String title;
    @Colum
    private String menuName;
    @Colum
    private String imgPath;
    @Colum
    private String form;
    @Colum
    private String content;
    @Colum
    private Timestamp createTime;
    @Colum
    private String createUser;
    @Colum
    private Timestamp publishTime;

    public int getId() {
        return id;
    }

    public boolean isToped() {
        return toped;
    }

    public void setToped(boolean toped) {
        this.toped = toped;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public String getTitle() {
        return title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getContent() {
        return content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }
}
