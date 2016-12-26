package com.sgaop.action.account;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.cons.Cons;
import com.sgaop.common.util.Tree;
import com.sgaop.entity.sys.Menu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/10/12 0012
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/account")
public class AccountAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:login")
    @GET
    @Path("/login")
    public void loginPage() {
        request.setAttribute("randomInt", new Random().nextInt(4));
    }

    @OK("btl:login")
    @GET
    @Path("/logout")
    public void logout() {
        Subject user = SecurityUtils.getSubject();
        user.logout();
        try {
            session.invalidate();
        } catch (Exception e) {
        }
        session = request.getSession(true);
        request.setAttribute("randomInt", new Random().nextInt(4));
    }

    /**
     * 无权限提示页面
     */
    @OK("btl:unauthorized")
    @GET
    @Path("/unauthorized")
    public void unauthorized() {}


    @OK("json")
    @POST
    @Path("/login")
    public Result doLogin(@Parameter("username") String username, @Parameter("password") String password, @Parameter("rememberMe") boolean isRememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(isRememberMe);
        Subject user = SecurityUtils.getSubject();
        try {
            user.login(token);
            Condition condition = new Condition();
            condition.and("locked", "=", false);
            condition.asc("short_no");
            List<Menu> menus = dao.query(Menu.class, condition);
            if (menus == null) {
                return Result.error("没有菜单权限");
            }
            menus = Tree.createTree(menus, 0);
            session.setAttribute(Cons.SESSION_MENUS, menus);
        } catch (Exception e) {
            log.debug(e);
            return Result.error(e.getMessage());
        }
        return Result.sucess("登录成功");
    }
}
