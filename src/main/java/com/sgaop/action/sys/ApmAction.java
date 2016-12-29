package com.sgaop.action.sys;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.dao.Dao;
import com.sgaop.basis.mvc.AjaxResult;
import com.sgaop.common.WebPojo.Result;
import com.sgaop.common.gather.*;
import com.sgaop.entity.sys.AlarmOption;
import com.sgaop.task.ApmJob;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.hyperic.sigar.Sigar;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/monitor/apm")
@RequiresRoles("admin")
public class ApmAction extends BaseAction {


    @OK("btl:sys.apm.index")
    @GET
    @Path("/dashboard")
    public void dashboard() {
    }

    @OK("json")
    @POST
    @Path("/data")
    public AjaxResult index(@Parameter("type") String _type) {
        HashMap result = new HashMap();
        try {
            if (_type != null) {
                Sigar sigar = new Sigar();
                switch (_type) {
                    case "CPU":
                        result.put("cpugather", CPUGather.gather((sigar)));
                        break;
                    case "DISK":
                        result.put("disks", DISKGather.gather(sigar));
                        break;
                    case "NI":
                        result.put("adapters", NetInterfaceGather.gather(sigar));
                        break;
                    case "SYS":
                        result.put("os", OSGather.init(sigar));
                        break;
                    case "MEM":
                        result.put("memory", MemoryGather.gather(sigar));
                        break;
                    case "ALL":
                        result.put("data", Gathers.all());
                        break;
                }
            }
        } catch (Exception e) {
            log.error(e);
            return new AjaxResult(false, e.getMessage());
        }
        return new AjaxResult(true, "", result);
    }

    @Inject
    private ApmJob apmJob;

    @Inject("dao")
    protected Dao dao;

    @OK("json")
    @POST
    @Path("/lineDashboard")
    public Result lineDashboard() {
        return Result.sucess(apmJob.getMoreTempAll());
    }

    @OK("json")
    @POST
    @Path("/tableDashboard")
    public Result tableDashboard() {
        return Result.sucess(apmJob.getOneTempAll());
    }

    @OK("json")
    @POST
    @Path("/alarmOptions")
    public Result AlarmOption() {
        return Result.sucess(dao.query(AlarmOption.class));
    }

    @OK("json")
    @POST
    @Path("/updateAlarmOption")
    public Result updateAlarmOptions(@Parameter("alarmType") String alarmType,
                                     @Parameter("percent") double percent,
                                     @Parameter("listenerTypes") String listenerTypes) {
        try {
            AlarmOption option = new AlarmOption();
            option.setAlarmType(alarmType);
            option.setPercent(percent);
            option.setListenerTypes(listenerTypes);
            if (dao.update(option, "alarmType", option.getAlarmType())) {
                apmJob.setAlarmOptions(dao.query(AlarmOption.class));
                return Result.sucess("更新成功");
            } else {
                return Result.error("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
}
