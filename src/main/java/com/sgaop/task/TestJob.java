package com.sgaop.task;

import com.sgaop.basis.annotation.Inject;
import com.sgaop.basis.annotation.IocBean;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.util.DateUtil;
import org.quartz.*;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/15 0015
 * To change this template use File | Settings | File Templates.
 */
@IocBean
public class TestJob implements Job {

    @Inject("dao")
    protected Dao dao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    }
}
