package com.sgaop.entity.sys;


import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Table;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 黄川
 * @time 2016-12-10 20:51:17
 */
@Table("sys_file_attach")
public class FileAttach implements Serializable {
    private static final long serialVersionUID = 1L;

    @ID
    @Colum("attachid")
    private int attachid;

    @Colum("attachtype")
    private String attachtype;

    @Colum("attachfilename")
    private String attachfilename;

    @Colum("savedfilename")
    private String savedfilename;

    @Colum("savedpath")
    private String savedpath;

    @Colum("filesize")
    private long filesize;

    @Colum("adduser")
    private String adduser;

    @Colum("addtime")
    private java.sql.Timestamp addtime;

    public int getAttachid() {
        return attachid;
    }

    public void setAttachid(int attachid) {
        this.attachid = attachid;
    }

    public String getAttachtype() {
        return attachtype;
    }

    public void setAttachtype(String attachtype) {
        this.attachtype = attachtype;
    }

    public String getAttachfilename() {
        return attachfilename;
    }

    public void setAttachfilename(String attachfilename) {
        this.attachfilename = attachfilename;
    }

    public String getSavedfilename() {
        return savedfilename;
    }

    public void setSavedfilename(String savedfilename) {
        this.savedfilename = savedfilename;
    }

    public String getSavedpath() {
        return savedpath;
    }

    public void setSavedpath(String savedpath) {
        this.savedpath = savedpath;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public Timestamp getAddtime() {
        return addtime;
    }

    public void setAddtime(Timestamp addtime) {
        this.addtime = addtime;
    }
}
