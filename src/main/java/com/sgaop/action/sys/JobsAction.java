package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.entity.sys.QuartzJob;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.quartz.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/26 0026
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysJobs")
@RequiresRoles("admin")//只有admin角色组才能访问本模块
public class JobsAction extends BaseAction {

    private final String JOB_GROUP = "DEFAULT";


    @Inject("dao")
    protected Dao dao;

    @Inject("scheduler")
    protected Scheduler scheduler;

    @OK("btl:sys.jobs.manager")
    @GET
    @Path("/index")
    @RequiresPermissions("sys.task.manager")
    public void index() {

    }

    @GET
    @POST
    @Path("/grid")
    @OK("json")
    public DataTableResult query() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<QuartzJob> userAccounts = dao.query(QuartzJob.class, pager);
        int count = dao.count(QuartzJob.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }


    /**
     * 暂停任务
     */
    @Path("/pauseJob")
    @OK("json")
    @POST
    @RequiresPermissions("sys.yw.task.pause")
    public Result pauseJob(@Parameter("id") int id) {
        try {
            //列出当前任务
   /*         GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);*/
            QuartzJob qjob = dao.fetch(QuartzJob.class, id);
            TriggerKey triggerKey = TriggerKey.triggerKey(qjob.getJobKlass(), JOB_GROUP);
            scheduler.pauseTrigger(triggerKey);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            qjob.setJobStatus(triggerState.name());
            dao.update(qjob);
            return Result.sucess("暂停成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.error("暂停失败" + e.getMessage());
        }
    }

    /**
     * 恢复任务
     */
    @Path("/resumJob")
    @OK("json")
    @POST
    @RequiresPermissions("sys.yw.task.resum")
    public Result resumJob(@Parameter("id") int id) {
        try {
            QuartzJob qjob = dao.fetch(QuartzJob.class, id);
            TriggerKey triggerKey = TriggerKey.triggerKey(qjob.getJobKlass(), JOB_GROUP);
            scheduler.resumeTrigger(triggerKey);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            qjob.setJobStatus(triggerState.name());
            dao.update(qjob);
            return Result.sucess("恢复成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.error("恢复失败" + e.getMessage());
        }
    }

    /**
     * 立即执行一次任务
     */
    @Path("/atOnceJob")
    @OK("json")
    @POST
    @RequiresPermissions("sys.yw.task.atonce")
    public Result atOnceJob(@Parameter("id") int id) {
        try {
            QuartzJob qjob = dao.fetch(QuartzJob.class, id);
            JobKey job = JobKey.jobKey(qjob.getJobRunName(), JOB_GROUP);
            scheduler.triggerJob(job);
            TriggerKey triggerKey = TriggerKey.triggerKey(qjob.getJobKlass(), JOB_GROUP);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            qjob.setJobStatus(triggerState.name());
            dao.update(qjob);
            return Result.sucess("执行成功");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return Result.error("执行失败" + e.getMessage());
        }
    }


    /**
     * 启动任务
     */
    @Path("/startJob")
    @OK("json")
    @POST
    @RequiresPermissions("sys.yw.task.start")
    public Result startJob(@Parameter("id") int id) {
        try {
            QuartzJob qjob = dao.fetch(QuartzJob.class, id);
            TriggerKey triggerKey = TriggerKey.triggerKey(qjob.getJobKlass(), JOB_GROUP);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            if (!"NORMAL".equals(triggerState.name())) {
                Class<?> klass = Class.forName(qjob.getJobKlass());
                JobDetail job = JobBuilder.newJob((Class<? extends Job>) klass).build();
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(qjob.getJobKlass())
                        .withSchedule(CronScheduleBuilder.cronSchedule(qjob.getJobCorn()))
                        .build();
                scheduler.scheduleJob(job, trigger);
                triggerState = scheduler.getTriggerState(triggerKey);
                JobKey jobKey = trigger.getJobKey();
                qjob.setJobRunName(jobKey.getName());
                qjob.setJobGroup(jobKey.getGroup());
                qjob.setJobStatus(triggerState.name());
            }
            qjob.setJobStatus(triggerState.name());
            dao.update(qjob);
            return Result.sucess("启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("启动失败" + e.getMessage());
        }
    }


    /**
     * 停止/删除 任务
     */
    @Path("/stopJob")
    @OK("json")
    @POST
    @RequiresPermissions("sys.yw.task.stop")
    public Result delJob(@Parameter("id") int id) {
        try {
            QuartzJob qjob = dao.fetch(QuartzJob.class, id);
            TriggerKey triggerKey = TriggerKey.triggerKey(qjob.getJobKlass(), JOB_GROUP);
            JobKey job = JobKey.jobKey(qjob.getJobKlass(), JOB_GROUP);
            scheduler.pauseTrigger(triggerKey);// 暂停触发器
            scheduler.unscheduleJob(triggerKey);//删除触发器
            scheduler.deleteJob(job);// 删除任务
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
            qjob.setJobStatus(triggerState.name());
            dao.update(qjob);
            return Result.sucess("停止成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("停止失败" + e.getMessage());
        }
    }


    /**
     * 添加任务
     */
    @Path("/addJob")
    @OK("json")
    @POST
    @RequiresPermissions("sys.yw.task.add")
    public Result addJob(@Parameter("id") int id) {
        try {
            QuartzJob qjob = dao.fetch(QuartzJob.class, id);
            Class<?> klass = Class.forName(qjob.getJobKlass());
            JobDetail job = JobBuilder.newJob((Class<? extends Job>) klass).build();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(qjob.getJobKlass())
                    .withSchedule(CronScheduleBuilder.cronSchedule(qjob.getJobCorn()))
                    .build();
            scheduler.scheduleJob(job, trigger);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            qjob.setJobStatus(triggerState.name());
            dao.update(qjob);
            return Result.sucess("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加失败" + e.getMessage());
        }
    }
}
