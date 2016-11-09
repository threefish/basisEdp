package com.sgaop.common.shiro.realm;

import com.sgaop.common.cons.Cons;
import com.sgaop.common.entity.UserAccount;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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

    private PasswordService passwordService = new DefaultPasswordService();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
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
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String account = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        if (account == null || password == null) {
            throw new AccountException("参数非法");
        }
        UserAccount user = new UserAccount();
        user.setUserName("admin");
        user.setUserPass(passwordService.encryptPassword("1234"));

        if (!passwordService.passwordsMatch(password, user.getUserPass())) {
            throw new AuthenticationException("用户名或密码错误");
        } else {
            //登录成功保存session信息
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            session.setAttribute(Cons.SESSION_USER, user);
        }
        // SimpleAuthenticationInfo里面会比较new UsernamePasswordToken("account", "password");的值,不相同会抛出异常
        return new SimpleAuthenticationInfo(account, password, getClass().getName());
    }
}
