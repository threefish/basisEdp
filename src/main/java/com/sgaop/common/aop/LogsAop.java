package com.sgaop.common.aop;

import com.sgaop.basis.annotation.Action;
import com.sgaop.basis.annotation.Aspect;
import com.sgaop.basis.annotation.Inject;
import com.sgaop.basis.annotation.IocBean;
import com.sgaop.basis.aop.InterceptorProxy;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.log.Logs;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.basis.util.WebUtil;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.cons.Cons;
import com.sgaop.entity.sys.SysLogs;
import com.sgaop.entity.sys.UserAccount;
import org.apache.log4j.Logger;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/10/12 0012
 * To change this template use File | Settings | File Templates.
 * 记录用户访问的所有业务操作
 */
@IocBean
@Aspect(annotation = Action.class, No = 2)
public class LogsAop extends InterceptorProxy {


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Documented
    public @interface Slog {

        String tag()[];// before //  after  //exception  //finalize

        String msg() default "";

        String module() default "sys";
    }

    @Inject("dao")
    protected Dao dao;

    private static final Logger log = Logs.get();

    private static LogsAop.Slog match(String tag, Method method) {
        LogsAop.Slog annotation = method.getAnnotation(LogsAop.Slog.class);
        if (annotation != null) {
            for (String s : annotation.tag()) {
                if (tag.equals(s)) {
                    return annotation;
                }
            }
        }
        return null;
    }

    /**
     * 目标方法执行之前
     *
     * @param cls
     * @param method
     * @param params
     * @throws Throwable
     */
    @Override
    protected void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LogsAop.Slog annotation = match("before", method);
        if (annotation != null) {
            UserAccount account = (UserAccount) Mvcs.getSession().getAttribute(Cons.SESSION_USER);
            SysLogs logs = new SysLogs();
            logs.setTag("before");
            logs.setAccessTime(new Date());
            logs.setModule(annotation.module());
            logs.setIp(WebUtil.ip(Mvcs.getReq()));
            logs.setSrc(cls.getName());
            logs.setMethod(method.getName());
            String msg = "";
            if (annotation != null) {
                msg = MessageFormat.format(annotation.msg(), params);
            }
            logs.setStatus(1);
            logs.setMsg(msg);
            if (account != null) {
                logs.setAccount(account.getUserName());
            }
            dao.insert(logs);
        }

    }

    /**
     * 目标方法执行之后
     *
     * @param cls
     * @param method
     * @param params
     * @throws Throwable
     */
    @Override
    protected void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LogsAop.Slog annotation = match("after", method);
        if (annotation != null) {
            UserAccount account = (UserAccount) Mvcs.getSession().getAttribute(Cons.SESSION_USER);
            SysLogs logs = new SysLogs();
            logs.setTag("after");
            logs.setAccessTime(new Date());
            logs.setModule(annotation.module());
            logs.setIp(WebUtil.ip(Mvcs.getReq()));
            logs.setSrc(cls.getName());
            logs.setMethod(method.getName());
            String msg = "";
            if (annotation != null) {
                msg = MessageFormat.format(annotation.msg(), params);
            }
            logs.setMsg(msg);
            if (result instanceof Result) {
                Result re = (Result) result;
                if (re.isOk()) {
                    if (re.getData() instanceof String) {
                        logs.setMsg(logs.getMsg() + re.getData());
                    }
                    logs.setStatus(1);
                } else {
                    logs.setStatus(0);
                    logs.setMsg(logs.getMsg() + re.getMsg());
                }
            } else {
                logs.setStatus(1);
            }
            if (account != null) {
                logs.setAccount(account.getUserName());
            }
            dao.insert(logs);
        }
    }

    /**
     * 目标方法执行出错
     *
     * @param cls
     * @param method
     * @param params
     * @throws Throwable
     */
    @Override
    protected void exception(Class<?> cls, Method method, Object[] params, Throwable e) {
        log.debug(MessageFormat.format("执行 {0}.{1} 任务期间发生了意料之外的错误！{2}", cls.getName(), method.getName(), e.getMessage()));
        LogsAop.Slog annotation = match("after", method);
        if (annotation != null) {
            UserAccount account = (UserAccount) Mvcs.getSession().getAttribute(Cons.SESSION_USER);
            SysLogs logs = new SysLogs();
            logs.setTag("after");
            logs.setAccessTime(new Date());
            logs.setModule(annotation.module());
            logs.setIp(WebUtil.ip(Mvcs.getReq()));
            logs.setSrc(cls.getName());
            logs.setMethod(method.getName());
            try {
                if (e.getMessage() != null) {
                    logs.setMsg(e.getMessage());
                } else if (e.getCause() != null) {
                    logs.setMsg(e.getCause().getMessage());
                } else {
                    logs.setMsg(e.toString());
                }
            } catch (Exception ex) {
                e.printStackTrace();
                log.error(ex);
            }
            logs.setStatus(9);
            if (account != null) {
                logs.setAccount(account.getUserName());
            }
            dao.insert(logs);
        }

    }

    /**
     * 目标方法执行完和当前拦截执行完成
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() {
    }
}
