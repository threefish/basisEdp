package com.sgaop.basis.plugin.shiro.aop;

import com.sgaop.basis.annotation.Action;
import com.sgaop.basis.annotation.Aspect;
import com.sgaop.basis.annotation.IocBean;
import com.sgaop.basis.aop.InterceptorProxy;
import com.sgaop.basis.error.ShiroAutcException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/26 0026
 * To change this template use File | Settings | File Templates.
 * 结合shiro控制用户访问后台方法
 */
@IocBean
@Aspect(annotation = Action.class, No = 1)
@SuppressWarnings("all")
public class BasisShiroInterceptorProxy extends InterceptorProxy {

    /**
     * 默认登录地址
     */
    public static String DefaultLoginURL;

    /**
     * 默认权限不符页面
     */
    public static String unauthorizedUrl;


    /**
     * 定义一个基于授权功能的注解类数组
     */
    private static final Class[] annotationClassArray = {
            RequiresAuthentication.class, RequiresUser.class, RequiresGuest.class, RequiresRoles.class, RequiresPermissions.class
    };


    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        Annotation clasAnn = getAnnotation(cls);
        Annotation methodAnn = getAnnotation(method);
        if (clasAnn != null) {
            matchAuth(clasAnn);
        }
        if (methodAnn != null) {
            matchAuth(methodAnn);
        }
    }

    /**
     * 取得类上的权限注解
     *
     * @param cls
     * @return
     */
    private Annotation getAnnotation(Class<?> cls) {
        for (Class<? extends Annotation> annotationClass : annotationClassArray) {
            if (cls.isAnnotationPresent(annotationClass)) {
                return cls.getAnnotation(annotationClass);
            }
        }
        return null;
    }

    /**
     * 取得方法上的权限注解
     *
     * @param method
     * @return
     */
    private Annotation getAnnotation(Method method) {
        for (Class<? extends Annotation> annotationClass : annotationClassArray) {
            if (method.isAnnotationPresent(annotationClass)) {
                return method.getAnnotation(annotationClass);
            }
        }
        return null;
    }

    /**
     * 权限验证
     *
     * @param annotation
     * @throws ShiroAutcException
     */
    private void matchAuth(Annotation annotation) throws ShiroAutcException {
        if (annotation.annotationType().equals(RequiresAuthentication.class)) {
            matchAuthenticated();
        } else if (annotation.annotationType().equals(RequiresUser.class)) {
            matchUser();
        } else if (annotation.annotationType().equals(RequiresGuest.class)) {
            matchGuest();
        } else if (annotation.annotationType().equals(RequiresRoles.class)) {
            matchHasRoles((RequiresRoles) annotation);
        } else if (annotation.annotationType().equals(RequiresPermissions.class)) {
            matchHasPermissions((RequiresPermissions) annotation);
        }
    }

    /**
     * 是否已认证用户
     *
     * @throws ShiroAutcException
     */
    private void matchAuthenticated() throws ShiroAutcException {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            throw new ShiroAutcException("当前用户尚未认证", unauthorizedUrl);
        }
    }

    /**
     * 是否是已登录用户
     *
     * @throws ShiroAutcException
     */
    private void matchUser() throws ShiroAutcException {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals == null || principals.isEmpty()) {
            throw new ShiroAutcException("当前用户尚未登录", DefaultLoginURL);
        }
    }

    /**
     * 是否访客
     *
     * @throws ShiroAutcException
     */
    private void matchGuest() throws ShiroAutcException {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principals = currentUser.getPrincipals();
        if (principals != null && !principals.isEmpty()) {
            throw new ShiroAutcException("当前用户不是访客", unauthorizedUrl);
        }
    }

    /**
     * 必须匹配全部角色
     *
     * @param hasRoles
     * @throws ShiroAutcException
     */
    private void matchHasRoles(RequiresRoles hasRoles) throws ShiroAutcException {
        String[] roleName = hasRoles.value();
        Collection<String> roles = new ArrayList<String>();
        for (String role : roleName) {
            roles.add(role);
        }
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.hasAllRoles(roles)) {
            throw new ShiroAutcException("当前用户角色不符", unauthorizedUrl);
        }
    }

    /**
     * 必须匹配全部权限
     *
     * @param hasPermissions
     * @throws ShiroAutcException
     */
    private void matchHasPermissions(RequiresPermissions hasPermissions) throws ShiroAutcException {
        String[] permissionName = hasPermissions.value();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isPermittedAll(permissionName)) {
            throw new ShiroAutcException("当前用户没有该操作权限", unauthorizedUrl);
        }
    }


    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

    }

    @Override
    protected void exception(Class<?> cls, Method method, Object[] params, Throwable e) {

    }

    @Override
    protected void finalize() {

    }
}
