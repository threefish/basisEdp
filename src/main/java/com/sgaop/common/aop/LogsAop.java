package com.sgaop.common.aop;

import com.sgaop.basis.annotation.Action;
import com.sgaop.basis.annotation.Aspect;
import com.sgaop.basis.annotation.Inject;
import com.sgaop.basis.annotation.IocBean;
import com.sgaop.basis.aop.InterceptorProxy;
import com.sgaop.basis.dao.Dao;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/10/12 0012
 * To change this template use File | Settings | File Templates.
 * 记录用户访问的所以业务操作
 */
@IocBean
@Aspect(annotation = Action.class, No = 2)
public class LogsAop extends InterceptorProxy {

    private static final Logger log = Logger.getRootLogger();


    @Override
    protected void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        //log.debug(String.format("访问了[%s]的[%s]方法", cls.getName(), method.getName()));
    }

    @Override
    protected void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

    }

    @Override
    protected void exception(Class<?> cls, Method method, Object[] params, Throwable e) {

    }

    @Override
    protected void finalize() {

    }
}
