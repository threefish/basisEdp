package com.sgaop.entity.sys;

import com.sgaop.basis.annotation.Colum;
import com.sgaop.basis.annotation.ID;
import com.sgaop.basis.annotation.Table;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/15 0015
 * To change this template use File | Settings | File Templates.
 */
@Table("sys_QuartzJob")
public class QuartzJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @ID
    @Colum("id")
    private int id;
    /**
     * 任务名称
     */
    @Colum("job_name")
    private String jobName;
    /**
     * 任务类路径
     */
    @Colum("job_klass")
    private String jobKlass;

    /**
     * 任务类表达式
     */
    @Colum("job_corn")
    private String jobCorn;

    /**
     * 任务描述
     */
    @Colum("job_desc")
    private String jobDesc;

    /**
     * 任务排序号
     */
    @Colum("job_short")
    private int jobShort;

    /**
     * 任务组
     */
    @Colum("job_group")
    private String jobGroup;

    /**
     * 任务状态
     */
    @Colum("job_status")
    private String jobStatus;

    /**
     * 任务类型 （0随服务启动|1手动启动）
     */
    @Colum("job_type")
    private int jobType;

    /**
     * 启动后的任务名
     */
    @Colum("job_run_name")
    private String jobRunName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobKlass() {
        return jobKlass;
    }

    public void setJobKlass(String jobKlass) {
        this.jobKlass = jobKlass;
    }

    public String getJobCorn() {
        return jobCorn;
    }

    public void setJobCorn(String jobCorn) {
        this.jobCorn = jobCorn;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public int getJobShort() {
        return jobShort;
    }

    public void setJobShort(int jobShort) {
        this.jobShort = jobShort;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getJobRunName() {
        return jobRunName;
    }

    public void setJobRunName(String jobRunName) {
        this.jobRunName = jobRunName;
    }
}
