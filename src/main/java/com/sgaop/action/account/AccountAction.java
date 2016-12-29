package com.sgaop.action.account;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.noise.StraightLineNoiseProducer;
import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.cons.Cons;
import com.sgaop.common.util.Tree;
import com.sgaop.entity.sys.Menu;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.awt.image.BufferedImage;
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

    @Inject("java:isCaptcha")
    private boolean isCaptcha;

    @OK("btl:login")
    @GET
    @Path("/login")
    public void loginPage() {
        request.setAttribute("randomInt", new Random().nextInt(3));
        request.setAttribute("isCaptcha", isCaptcha);
    }

    @OK("raw")
    @GET
    @Path("/captcha")
    public BufferedImage captcha() {
        int w = 130;
        int h = 60;
        Captcha captcha = new Captcha.Builder(w, h)
                .addText()
                .addBackground(new GradiatedBackgroundProducer())
                .addNoise(new StraightLineNoiseProducer())
                .addBorder()
                .gimp(new FishEyeGimpyRenderer()).addBorder()
                .build();
        session.setAttribute(Cons.CAPTCHA_ATTR, captcha.getAnswer());
        return captcha.getImage();
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
        request.setAttribute("randomInt", new Random().nextInt(3));
        request.setAttribute("isCaptcha", isCaptcha);
    }

    /**
     * 无权限提示页面
     */
    @OK("btl:unauthorized")
    @GET
    @Path("/unauthorized")
    public void unauthorized() {
    }


    @OK("json")
    @POST
    @Path("/login")
    public Result doLogin(@Parameter("username") String username, @Parameter("password") String password, @Parameter("captcha") String captcha, @Parameter("rememberMe") boolean isRememberMe) {
        if (isCaptcha) {
            String _captcha = (String) session.getAttribute(Cons.CAPTCHA_ATTR);
            if (StringsTool.isNullorEmpty(captcha) || StringsTool.isNullorEmpty(_captcha)) {
                return Result.error("验证码不能为空！");
            }
            if (!_captcha.equalsIgnoreCase(captcha)) {
                session.removeAttribute(Cons.CAPTCHA_ATTR);
                return Result.error("验证码错误！");
            }
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(isRememberMe);
            Subject user = SecurityUtils.getSubject();
            user.login(token);
            Condition condition = new Condition();
            condition.and("locked", "=", false);
            condition.and("menu_type", "=", 0);
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
