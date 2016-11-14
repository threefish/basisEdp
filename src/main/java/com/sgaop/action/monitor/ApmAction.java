package com.sgaop.action.monitor;

import com.sgaop.basis.annotation.*;
import com.sgaop.basis.mvc.Mvcs;
import com.sgaop.common.gather.*;
import org.hyperic.sigar.Sigar;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
@Action("/monitor/apm")
public class ApmAction {

    private HttpServletRequest request;

    public static enum APIType {
        /**
         * cpu信息
         */
        CPU,
        /**
         * 内存信息
         */
        MEM,
        /**
         * 磁盘信息
         */
        DISK,
        /**
         * 网卡信息
         */
        NI,
        /**
         * 系统信息
         */
        SYS,
        /**
         * 全部
         */
        ALL,
        /**
         * 默认
         */
        DEFAULT;

        public static APIType form(String t) {
            for (APIType type : values()) {
                if (type.name().equalsIgnoreCase(t)) {
                    return type;
                }
            }
            return DEFAULT;
        }
    }

    @OK("json")
    @POST
    @Path("/data")
    public HashMap index(@Parameter("type") String _type) {
        HashMap result = new HashMap();
        try {
            APIType type = APIType.form(_type);
            Sigar sigar = new Sigar();
            type = null == type ? APIType.DEFAULT : type;
            switch (type) {
                case CPU:
                    result.put("cpugather", CPUGather.gather((sigar)));
                    break;
                case DISK:
                    result.put("disks", DISKGather.gather(sigar));
                    break;
                case NI:
                    result.put("adapters", NetInterfaceGather.gather(sigar));
                    break;
                case SYS:
                    result.put("os", OSGather.init(sigar));
                    break;
                case MEM:
                    result.put("memory", MemoryGather.gather(sigar));
                    break;
                case ALL:
                    result.put("data",Gathers.all());
                    break;
                default:
                    result.put("apis", APIType.values());
                    result.put("discription", "use type parameter to invoke apis like type=SYS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
