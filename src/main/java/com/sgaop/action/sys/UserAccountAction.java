package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.dao.entity.Record;
import com.sgaop.basis.i18n.LanguageManager;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.aop.LogsAop;
import com.sgaop.entity.sys.UserAccount;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/17 0017
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysAccount")
@RequiresRoles("admin")  //只有admin角色组才能访问本模块
public class UserAccountAction extends BaseAction {


    @Inject("dao")
    protected Dao dao;

    @Inject("java:default.password")
    private String defaultPassword;

    @Inject("java:default.admin")
    private String defaultAdmin;

    @OK("btl:sys.user.manager")
    @GET
    @Path("/manager")
    @RequiresPermissions("sys.yw.account.manager")
    public void manager() {
        request.setAttribute("defaultAdmin", defaultAdmin);
    }

    /**
     * 弹窗选中人员
     */
    @OK("btl:inc.accounts")
    @GET
    @Path("/showUserAccounts")
    public void showUserAccounts() {
    }



    @OK("json:{locked:'userPass|salt',ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/grid")
    public DataTableResult RoleUsersGrid(@Parameter("name") String name, @Parameter("status") int status) {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        Condition cnd = new Condition();
        if (!StringsTool.isNullorEmpty(name)) {
            cnd.and("userName", "like", "%" + name + "%");
        }
        if (status != -1) {
            cnd.and("locked", "=", status);
        }
        List<UserAccount> userAccounts = dao.query(UserAccount.class, pager, cnd);
        int count = dao.count(UserAccount.class, cnd);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }



    @OK("json:{ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/update")
    @RequiresPermissions("sys.yw.account.update")
    @LogsAop.Slog(tag = "after", msg = "修改账号{1} (ID=[{0}])")
    public Result update(@Parameter("id") int id, @Parameter("action") String action) {
        UserAccount account = dao.fetch(UserAccount.class, id);
        if (account == null) {
            return Result.error(LanguageManager.get("userNoFoud"));
        }
        switch (action) {
            case "enable":
                account.setLocked(false);
                break;
            case "disable":
                if (defaultAdmin.equals(account.getUserName())) {
                    return Result.error(defaultAdmin + "是系统管理员账号，不能对其进行启用禁用操作");
                }
                account.setLocked(true);
                break;
            case "repass":
                if (defaultAdmin.equals(account.getUserName())) {
                    return Result.error(defaultAdmin + "是系统管理员账号，不能重置其密码");
                }
                String salt = UUID.randomUUID().toString().replaceAll("-", "");
                Sha256Hash sha = new Sha256Hash(defaultPassword, salt);
                account.setUserPass(sha.toHex());
                account.setSalt(salt);
                break;
        }
        try {
            dao.update(account);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.sucess(LanguageManager.get("AjaxSuccessMsg"));
    }

    @OK("json:{ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/add")
    @RequiresPermissions("sys.yw.account.add")
    @LogsAop.Slog(tag = "after", msg = "添加账号:{0}")
    public Result add(@Parameter("userName") String UserName) {
        UserAccount account = dao.fetch(UserAccount.class, "userName", UserName);
        if (account != null) {
            return Result.error(LanguageManager.get("userIsExist"));
        }
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        Sha256Hash sha = new Sha256Hash(defaultPassword, salt);
        try {
            account = new UserAccount();
            account.setUserName(UserName);
            account.setLocked(false);
            account.setUserPass(sha.toHex());
            account.setSalt(salt);
            account.setCreateTime(new Date());
            dao.insert(account);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.sucess(LanguageManager.get("AjaxSuccessMsg"));
    }

}
