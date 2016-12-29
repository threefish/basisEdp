package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.dao.Pager;
import com.sgaop.common.WebPojo.DataTablePager;
import com.sgaop.common.WebPojo.DataTableResult;
import com.sgaop.entity.sys.Role;
import com.sgaop.entity.sys.UserAccount;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/27 0027
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/sysRole")
@RequiresRoles("admin")
public class RoleAction extends BaseAction {

    @Inject("dao")
    protected Dao dao;

    @OK("btl:sys.role.manager")
    @GET
    @Path("/manager")
    public void manager() {
    }


    @OK("json:{locked:'userPass|salt',ignoreNull:false,DateFormat:'yyyy-MM-dd HH:mm:ss'}")
    @POST
    @Path("/grid")
    public DataTableResult grid() {
        DataTablePager dataTablePager = DataTablePager.CreateDataTablePager(request);
        Pager pager = new Pager(dataTablePager.getPageNumber(), dataTablePager.getPageSize());
        List<Role> userAccounts = dao.query(Role.class, pager);
        int count = dao.count(Role.class);
        DataTableResult dataResult = new DataTableResult();
        dataResult.setRecordsTotal(count);
        dataResult.setRecordsFiltered(count);
        dataResult.setDraw(Integer.valueOf(request.getParameter("draw")));
        dataResult.setData(userAccounts);
        return dataResult;
    }
}
