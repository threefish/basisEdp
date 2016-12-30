package com.sgaop.basis.plugin.shiro.realm;

import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.entity.Record;
import com.sgaop.basis.i18n.LanguageManager;
import com.sgaop.basis.ioc.Ioc;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.cons.Cons;
import com.sgaop.entity.sys.UserAccount;
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
import java.util.List;
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
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        SimpleAuthorizationInfo authorizationInfo = (SimpleAuthorizationInfo) session.getAttribute(Cons.AUTHORIZATION_INFO);
        if (!subject.isAuthenticated() || authorizationInfo == null) {
            authorizationInfo = new SimpleAuthorizationInfo();
            UserAccount user = (UserAccount) session.getAttribute(Cons.SESSION_USER);
            if (user != null) {
                Set<String> roles = new HashSet<>();
                Set<String> permissions = new HashSet<>();
                String sql = "SELECT r.id,r.role_name,r.role_code from sys_useraccountrole as ur,sys_role as r  WHERE ur.role_id=r.id and ur.user_id=?";
                List<Record> roleList = dao.query(sql, user.getId());
                String roleids = "";
                for (Record mapro : roleList) {
                    roles.add(mapro.getString("role_code"));
                    roleids += mapro.getInt("id") + ",";
                }
                sql = "SELECT r.id,r.role_name,r.role_code,p.id,p.permission_name from sys_role as r,sys_role_menu as rp,sys_permission as p ";
                sql += "WHERE r.id=rp.role_id and rp.menu_id=p.id AND FIND_IN_SET(r.id,?);";
                List<Record> permissionList = dao.query(sql, roleids);
                for (Record mappo : permissionList) {
                    permissions.add(mappo.getString("permission_name"));
                }
                authorizationInfo.addRoles(roles);
                authorizationInfo.addStringPermissions(permissions);
            }
            session.setAttribute(Cons.AUTHORIZATION_INFO, authorizationInfo);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (dao == null) {
            dao = (Dao) Ioc.getBean("dao");
        }
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        if (username == null || password == null) {
            throw new AccountException(LanguageManager.get("illegal"));
        }
        UserAccount userAccount = dao.fetch(UserAccount.class, "userName", username);
        if (userAccount == null) {
            throw new AuthenticationException(String.format(LanguageManager.get("loginuserNoFoud"), username));
        }
        if (userAccount.isLocked()) {
            throw new AuthenticationException(LanguageManager.get("loginUserAccountIsLocked"));
        }
        Sha256Hash sha = new Sha256Hash(password, userAccount.getSalt());
        if (!sha.toHex().equals(userAccount.getUserPass())) {
            throw new AuthenticationException(LanguageManager.get("loginPassError"));
        } else {
            Mvcs.getSession().setAttribute(Cons.SESSION_USER, userAccount);
        }
        upToken.setPassword(password.toCharArray());
        return new SimpleAuthenticationInfo(upToken, password, getClass().getName());
    }
}
