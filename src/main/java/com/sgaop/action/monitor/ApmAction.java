package com.sgaop.action.monitor;

import com.sgaop.action.BaseAction;
import com.sgaop.basis.annotation.*;
import com.sgaop.basis.mvc.AjaxResult;
import com.sgaop.common.gather.*;
import com.sgaop.task.ApmJob;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
@RequiresAuthentication
public class ApmAction extends BaseAction {


    @OK("beetl:apm.index")
    @GET
    @Path("/index")
    public void index() {

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
    ApmJob apmJob;

    @OK("json")
    @POST
    @Path("/dashboard")
    public AjaxResult dashboard() {
        HashMap data = apmJob.getTempAll();
        return new AjaxResult(true, "", data);
    }
}
