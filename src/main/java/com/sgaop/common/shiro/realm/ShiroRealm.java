package com.sgaop.common.shiro.realm;

import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.entity.Record;
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
                try {
                    Set<String> roles = new HashSet<>();
                    Set<String> permissions = new HashSet<>();
                    String sql = "SELECT r.id,r.role_name from useraccountrole as ur,role as r  WHERE ur.role_id=r.id and ur.user_id=?";
                    List<Record> roleList = dao.queryRecordList(sql, user.getId());
                    String roleids = "";
                    for (Record mapro : roleList) {
                        roles.add(mapro.getString("role_name"));
                        roleids += mapro.getInt("id") + ",";
                    }
                    sql = "SELECT r.id,r.role_name,p.id,p.permission_name from role as r,rolepermission as rp,permission as p ";
                    sql += "WHERE r.id=rp.role_id and rp.permission_id=p.id AND FIND_IN_SET(r.id,?);";
                    List<Record> permissionList = dao.queryRecordList(sql, roleids);
                    for (Record mappo : permissionList) {
                        permissions.add(mappo.getString("permission_name"));
                    }
                    authorizationInfo.addRoles(roles);
                    authorizationInfo.addStringPermissions(permissions);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
