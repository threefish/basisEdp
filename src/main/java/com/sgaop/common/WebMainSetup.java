package com.sgaop.common;

import com.alibaba.druid.pool.DruidDataSource;
import com.sgaop.basis.annotation.Setup;
import com.sgaop.basis.cache.PropertiesManager;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.DaosRegister;
import com.sgaop.basis.dao.impl.DaoImpl;
import com.sgaop.basis.mvc.view.ViewsRegister;
import com.sgaop.basis.quartz.QuartzRegister;
import com.sgaop.basis.register.Registers;
import com.sgaop.basis.web.WebSetup;
import com.sgaop.common.view.BeetlView;
import com.sgaop.entity.sys.QuartzJob;
import org.apache.log4j.Logger;
import org.quartz.*;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/8 0008
 * To change this template use File | Settings | File Templates.
 */
@Setup
public class WebMainSetup implements WebSetup {

    private static final Logger log = Logger.getRootLogger();

    private Dao dao;

    private Scheduler scheduler;

    public void init(ServletContextEvent servletContextEvent) {
        DataSource dataSource = getDsA();
        //注册视图
        Registers.view("btl", BeetlView.class);
        //注册数据源
        dao = Registers.dao("dao", DaoImpl.class, dataSource);
        //注册quartz任务管理器
        scheduler = Registers.Scheduler("schedulder");
        //初始化任务
        initQuartz();
    }


    /**
     * 销毁
     *
     * @param servletContextEvent
     */
    public void destroy(ServletContextEvent servletContextEvent) {
        destroyQuartz();
    }

    /**
     * 销毁任务
     *
     * @throws Exception
     */
    private void destroyQuartz() {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化任务
     *
     * @throws Exception
     */
    private void initQuartz() {
        try {
            List<QuartzJob> jobList = dao.query(QuartzJob.class);
            for (QuartzJob qjob : jobList) {
                if (qjob.getJobType() == 0) {
                    String jobKlass = qjob.getJobKlass();
                    String jobCron = qjob.getJobCorn();
                    log.debug(String.format("job define jobKlass=%s jobCron=%s", jobKlass, jobCron));
                    Class<?> klass = Class.forName(jobKlass);
                    JobDetail job = JobBuilder.newJob((Class<? extends Job>) klass).build();
                    CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobKlass)
                            .withSchedule(CronScheduleBuilder.cronSchedule(jobCron))
                            .build();
                    scheduler.scheduleJob(job, trigger);
                    /**
                     * 启动任务
                     */
                    scheduler.start();
                    /**
                     * 更新状态
                     */
                    JobKey jobKey = trigger.getJobKey();
                    qjob.setJobRunName(jobKey.getName());
                    qjob.setJobGroup(jobKey.getGroup());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    qjob.setJobStatus(triggerState.name());
                } else {
                    qjob.setJobStatus("NONE");
                    qjob.setJobType(1);
                }
                dao.update(qjob);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }


    /**
     * 取得数据源
     *
     * @return
     */
    private DataSource getDsA() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setPassword(PropertiesManager.getCacheStr("db.password"));
        dataSource.setUsername(PropertiesManager.getCacheStr("db.user"));
        dataSource.setUrl(PropertiesManager.getCacheStr("db.jdbcUrl"));
        dataSource.setMaxActive(PropertiesManager.getIntCache("db.maxActive"));
        dataSource.setDriverClassName(PropertiesManager.getCacheStr("db.driverClassName"));
        dataSource.setValidationQuery(PropertiesManager.getCacheStr("db.validationQuery"));
        dataSource.setValidationQueryTimeout(PropertiesManager.getIntCache("db.validationQueryTimeout"));
        dataSource.setInitialSize(PropertiesManager.getIntCache("db.initialSize"));
        dataSource.setMinIdle(PropertiesManager.getIntCache("db.minIdle"));
        dataSource.setMaxWait(PropertiesManager.getIntCache("db.maxWait"));
        dataSource.setTimeBetweenEvictionRunsMillis(PropertiesManager.getIntCache("db.timeBetweenEvictionRunsMillis"));
        dataSource.setMinEvictableIdleTimeMillis(PropertiesManager.getIntCache("db.minEvictableIdleTimeMillis"));
        dataSource.setTestWhileIdle(PropertiesManager.getBooleanCache("db.testWhileIdle"));
        dataSource.setTestOnBorrow(PropertiesManager.getBooleanCache("db.testOnBorrow"));
        dataSource.setTestOnReturn(PropertiesManager.getBooleanCache("db.testOnReturn"));
        dataSource.setPoolPreparedStatements(PropertiesManager.getBooleanCache("db.poolPreparedStatements"));
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(60 * 60 * 1000);
        Properties properties = new Properties();
        String[] strings = PropertiesManager.getCacheStr("db.connectionProperties").split("=");
        properties.setProperty(strings[0], strings[1]);
        dataSource.setConnectProperties(properties);
        try {
            dataSource.setFilters(PropertiesManager.getCacheStr("db.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
