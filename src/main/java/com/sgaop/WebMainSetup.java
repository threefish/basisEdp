package com.sgaop;

import com.alibaba.druid.pool.DruidDataSource;
import com.sgaop.basis.annotation.Setup;
import com.sgaop.basis.cache.PropertiesManager;
import com.sgaop.basis.dao.DaosRegister;
import com.sgaop.basis.dao.impl.DaoImpl;
import com.sgaop.basis.mvc.view.ViewsRegister;
import com.sgaop.basis.web.WebSetup;
import com.sgaop.common.view.BeetlView;

import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/8 0008
 * To change this template use File | Settings | File Templates.
 */
@Setup
public class WebMainSetup implements WebSetup {
    public void init(ServletContextEvent servletContextEvent) {
        ViewsRegister.registerView("beetl", BeetlView.class);
        //注册数据源
        DaosRegister.registerDao("dao", DaoImpl.class, getDsA());
    }

    public void destroy(ServletContextEvent servletContextEvent) {

    }

    /**
     * 数据源
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
        return dataSource;
    }

}
