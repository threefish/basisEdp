package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Condition;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.util.StringsTool;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.entity.sys.SysLogs;
import com.sgaop.entity.sys.UserAccount;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2017/1/23 0023
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sys/logs")
@RequiresRoles("admin")  //只有admin角色组才能访问本模块
public class SysLogsAction extends BaseAction{

    @Inject("dao")
    protected Dao dao;


    @OK("btl:sys.logs.manager")
    @GET
    @Path("/manager")
    @RequiresPermissions("sys.yw.logs")
    public void manager() {

    }

    @OK("json:{ignoreNull:true,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/grid")
    @RequiresPermissions("sys.yw.logs")
    public DataTableResult DataTableResult(@Parameter("account") String account,@Parameter("module") String module, @Parameter("status") int status) {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        Condition cnd = new Condition();
        if (!StringsTool.isNullorEmpty(module)) {
            cnd.and("module", "like", "%" + module + "%");
        }
        if (!StringsTool.isNullorEmpty(account)) {
            cnd.and("account", "like", "%" + account + "%");
        }
        if (status != -1) {
            cnd.and("status", "=", status);
        }
        List<SysLogs> userAccounts = dao.query(SysLogs.class, pager, cnd);
        int count = dao.count(SysLogs.class, cnd);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }

}
