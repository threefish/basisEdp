package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.i18n.LanguageManager;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.entity.sys.UserAccount;
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
@Action("/userAccount")
public class UserAccountAction extends BaseAction {


    @Inject("dao")
    protected Dao dao;

    @Inject("java:default.password")
    private String defaultPassword;

    @OK("btl:sys.user.manager")
    @GET
    @Path("/manager")
    public void manager() {
    }


    @OK("json:{locked:'userPass|salt',ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(Mvcs.getReq());
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<UserAccount> userAccounts = dao.query(UserAccount.class, pager);
        int count = dao.count(UserAccount.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(Mvcs.getReq().getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }

    @OK("json:{ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/update")
    public Result update(@Parameter("id") int id, @Parameter("action") String action) {
        UserAccount account = dao.fetch(UserAccount.class, id);
        if(account==null){
            return Result.error(LanguageManager.get("userNoFoud"));
        }
        switch (action) {
            case "enable":
                account.setLocked(false);
                break;
            case "disable":
                account.setLocked(true);
                break;
            case "repass":
                String salt = UUID.randomUUID().toString().replaceAll("-", "");
                Sha256Hash sha = new Sha256Hash(defaultPassword, salt);
                account.setUserPass(sha.toHex());
                account.setSalt(salt);
                break;
        }
        try {
            dao.update(account);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
        return Result.sucess(LanguageManager.get("AjaxSuccessMsg"));
    }

    @OK("json:{ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/add")
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
            account.setCreateTime(new Timestamp(new Date().getTime()));
            dao.insert(account);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
        return Result.sucess(LanguageManager.get("AjaxSuccessMsg"));
    }

}
