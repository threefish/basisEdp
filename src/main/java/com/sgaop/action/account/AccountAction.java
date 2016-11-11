package com.sgaop.action.account;

import com.google.gson.Gson;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.mvc.AjaxResult;
import com.sgaop.common.cons.Cons;
import com.sgaop.common.util.Tree;
import com.sgaop.entity.sys.Menu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/10/12 0012
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/account")
public class AccountAction {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpSession session;

    private ServletContext context;

    @Inject("dao")
    private Dao dao;


    @OK("jsp:login.jsp")
    @GET
    @Path("/login")
    public void loginPage() {
    }

    @OK("jsp:login.jsp")
    @GET
    @Path("/logout")
    public void logout() {
        Subject user = SecurityUtils.getSubject();
        user.logout();
        try {
            session.invalidate();
        } catch (Exception e) {}
        session = request.getSession(true);
    }


    @OK("json")
    @POST
    @Path("/login")
    public AjaxResult doLogin(@Parameter("username") String username, @Parameter("password") String password, @Parameter("rememberMe") boolean isRememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(isRememberMe);
        Subject user = SecurityUtils.getSubject();
        try {
            user.login(token);
            List<Menu> menus=dao.queryAll(Menu.class);
            menus= Tree.createTree(menus, 0);
            System.out.println(new Gson().toJson(menus));
            session.setAttribute(Cons.SESSION_MENUS, menus);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, e.getMessage());
        }
        return new AjaxResult(true, "登录成功");
    }
}
