package com.sgaop.action.account;

import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.mvc.AjaxResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    public void loginPage() {}

    @OK("json")
    @POST
    @Path("/login")
    public AjaxResult doLogin(@Parameter("username") String username, @Parameter("password") String password, @Parameter("rememberMe") boolean isRememberMe) {
        // 创建 Token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(isRememberMe);
        // 获取当前用户，并进行登录操作
        Subject user = SecurityUtils.getSubject();
        try {
            user.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, e.getMessage());
        }
        return new AjaxResult(true, "登录成功");
    }
}
