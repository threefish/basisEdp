package com.sgaop.common.shiro.realm;

import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.ioc.BasisIoc;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.cons.Cons;
import com.sgaop.entity.UserAccount;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2016/1/22 002216:12
 */
public class ShiroRealm extends AuthorizingRealm {

    private Dao dao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("dao:" + dao);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        SimpleAuthorizationInfo authorizationInfo = (SimpleAuthorizationInfo) session.getAttribute(Cons.AUTHORIZATION_INFO);
        UserAccount user = (UserAccount) session.getAttribute(Cons.SESSION_USER);
        if (!subject.isAuthenticated() || authorizationInfo == null) {
            authorizationInfo = new SimpleAuthorizationInfo();
//			/* 添加多个角色名 */
            Set<String> roles = new HashSet<String>();
//			/* 添加多个权限名 */
            Set<String> permissions = new HashSet<String>();
            permissions.add("看帖子");

            authorizationInfo.addRoles(roles);
            authorizationInfo.addStringPermissions(permissions); // 添加权限名
            session.setAttribute(Cons.AUTHORIZATION_INFO, authorizationInfo);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (dao == null) {
            dao = (Dao) BasisIoc.getBean("dao");
        }
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        UserAccount userAccount = null;
        if (username == null || password == null) {
            throw new AccountException("参数非法");
        }
        try {
            userAccount = dao.querySinge(UserAccount.class, "userName=?", username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userAccount == null) {
            throw new AuthenticationException(String.format("账户[%s]不存在！", username));
        }
        if (userAccount.isLocked()) {
            throw new AuthenticationException("账户已被锁定无法登陆，请联系管理员解锁！");
        }
        Sha256Hash sha = new Sha256Hash(password, userAccount.getSalt());
        if (!sha.toHex().equals(userAccount.getUserPass())) {
            throw new AuthenticationException("用户名或密码错误");
        } else {
            //登录成功保存session信息
//            Subject subject = SecurityUtils.getSubject();
//            Session session = subject.getSession(false);
//            session.setAttribute(Cons.SESSION_USER, userAccount);
            Mvcs.getSession().setAttribute(Cons.SESSION_USER, userAccount);
        }
        upToken.setPassword(password.toCharArray());
        return new SimpleAuthenticationInfo(upToken, password, getClass().getName());
    }
}
