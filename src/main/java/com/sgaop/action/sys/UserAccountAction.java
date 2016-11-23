package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.entity.UserAccount;
import com.sgaop.entity.sys.Menu;

import java.util.List;

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

    @OK("beetl:sys.user.manager")
    @GET
    @Path("/manager")
    public void manager() {}


    @OK("json:{locked:'userPass|salt',ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(Mvcs.getReq());
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<UserAccount> userAccounts = dao.queryCndList(UserAccount.class, pager);
        int count = dao.queryCndListCount(UserAccount.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(Mvcs.getReq().getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }


}
