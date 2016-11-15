package com.sgaop.task;

import com.sgaop.basis.annotation.Inject;
import com.sgaop.basis.annotation.IocBean;
import com.sgaop.basis.cache.PropertiesManager;
import com.sgaop.basis.dao.Dao;
import com.sgaop.common.gather.CPUGather;
import com.sgaop.common.gather.DISKGather;
import com.sgaop.common.gather.MemoryGather;
import com.sgaop.common.gather.NetInterfaceGather;
import com.sgaop.common.util.Numbers;
import com.sgaop.entity.sys.APMAlarm;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/11/14 0014
 * To change this template use File | Settings | File Templates.
 */
@IocBean
public class ApmJob implements Job {

    @Inject("dao")
    protected Dao dao;

    private Sigar sigar = new Sigar();

    /**
     * 时间点
     */
    private List<Date> timePoints = new ArrayList();

    private List<Double> cpuUsages = new ArrayList();

    private List<Double> ramUsages = new ArrayList();

    private List<Double> jvmUsages = new ArrayList();

    private List<Double> swapUsages = new ArrayList();

    private List<Double> niUsages = new ArrayList();

    private List<Double> noUsages = new ArrayList();

    private int monitorCount = 25;


    /**
     * 取得临时缓存的最近系统状态信息
     *
     * @return
     */
    public HashMap getTempAll() {
        HashMap map = new HashMap();
        map.put("timePoints", timePoints);
        map.put("cpuUsages", cpuUsages);
        map.put("ramUsages", ramUsages);
        map.put("jvmUsages", jvmUsages);
        map.put("swapUsages", swapUsages);
        map.put("niUsages", niUsages);
        map.put("noUsages", noUsages);
        return map;
    }


    /**
     * 添加数据
     *
     * @param list 列表
     * @param obj  待添加数据
     */
    private void add(List list, Object obj) {
        if (obj instanceof Number) {
            list.add(Numbers.keepPrecision((Number) obj, 2));
        } else {
            list.add(obj);
        }
        if (list.size() > monitorCount) {
            list.remove(0);
        }
    }


    /**
     * @param type
     * @param title
     * @param device
     * @param usage
     * @param alarmPoint
     */
    private void alarm(APMAlarm.Type type, String title, String device, double usage, int alarmPoint) {
        final APMAlarm alarm = new APMAlarm();
        alarm.setType(type);
        alarm.setIp("127.0.0.1");
        alarm.setMsg(String.format("%s:当前 %s 使用率 %f,高于预警值 %d", title, device, usage, alarmPoint));
        alarm.setTitle(title);
        alarm.setDevice(device);
        alarm.setUsage(usage);
        alarm.setAlarm(alarmPoint);
        try {
            dao.insert(alarm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {

            MemoryGather memory = MemoryGather.gather(sigar);

            // 内存
            double jvmUsage, ramUsage, swapUsage = 0;
            if ((jvmUsage = memory.getJvm().getUsedPercent()) > PropertiesManager.getInt("jvm.alarm.percent")) {
                alarm(APMAlarm.Type.MEM, "内存警告", "JVM", jvmUsage, PropertiesManager.getInt("jvm.alarm.percent"));
            }
            if ((ramUsage = memory.getMem().getUsedPercent()) > PropertiesManager.getInt("ram.alarm.percent")) {
                alarm(APMAlarm.Type.MEM, "内存警告", "RAM", ramUsage, PropertiesManager.getInt("ram.alarm.percent"));
            }
            if (memory.getSwap().getTotal() != 0) {
                if ((swapUsage = memory.getSwap().getUsed() * 100 / memory.getSwap().getTotal()) > PropertiesManager.getInt("swap.alarm.percent")) {
                    alarm(APMAlarm.Type.MEM, "内存警告", "SWAP", swapUsage, PropertiesManager.getInt("swap.alarm.percent"));
                }
            }

            CPUGather cpu = CPUGather.gather(sigar);

            // CPU
            double cpuUsage;

            if ((cpuUsage = 100 - cpu.getPerc().getIdle() * 100) > PropertiesManager.getInt("cpu.alarm.percent")) {
                alarm(APMAlarm.Type.MEM, "CPU警告", "CPU", cpuUsage, PropertiesManager.getInt("cpu.alarm.percent"));
            }
            // 磁盘

            List<DISKGather> disks = DISKGather.gather(sigar);
            for (DISKGather disk : disks) {
                if (disk.getStat() != null && disk.getStat().getUsePercent() * 100 > PropertiesManager.getInt("disk.alarm.percent")) {
                    alarm(APMAlarm.Type.DISK, "磁盘警告", "DISK", disk.getStat().getUsePercent(), PropertiesManager.getInt("disk.alarm.percent"));
                }
            }

            // 网络流量
            double niUsage, noUsage;
            NetInterfaceGather ni = NetInterfaceGather.gather(sigar);
            if ((niUsage = ni.getRxbps() * 100 / ni.getStat().getSpeed()) > PropertiesManager.getInt("network.alarm.percent")) {
                alarm(APMAlarm.Type.NETWORK, "流量警告", "NETWORK", niUsage, PropertiesManager.getInt("network.alarm.percent"));
            }
            if ((noUsage = ni.getTxbps() * 100 / ni.getStat().getSpeed()) > PropertiesManager.getInt("network.alarm.percent")) {
                alarm(APMAlarm.Type.NETWORK, "流量警告", "NETWORK", noUsage, PropertiesManager.getInt("network.alarm.percent"));
            }
            add(timePoints, new SimpleDateFormat("HH:mm:ss").format(new Date()));
            add(jvmUsages, jvmUsage);
            add(ramUsages, ramUsage);
            add(swapUsages, swapUsage);
            add(cpuUsages, cpuUsage);
            add(niUsages, niUsage);
            add(noUsages, noUsage);
        } catch (SigarException e) {
            e.printStackTrace();
        }

    }


    public static class SigarData {
        private Date gatherTime = new Date();
        private double apuUsage;
        private double ramUsage;
        private double jvmUsage;
        private double swapUsage;
        private double niUsage;
        private double noUsage;
        private double ioUsage;
        private double diskUsage;

        public Date getGatherTime() {
            return gatherTime;
        }

        public double getIoUsage() {
            return ioUsage;
        }

        public void setIoUsage(double ioUsage) {
            this.ioUsage = ioUsage;
        }

        public void setGatherTime(Date gatherTime) {
            this.gatherTime = gatherTime;
        }

        public double getApuUsage() {
            return apuUsage;
        }

        public void setApuUsage(double apuUsage) {
            this.apuUsage = apuUsage;
        }

        public double getRamUsage() {
            return ramUsage;
        }

        public void setRamUsage(double ramUsage) {
            this.ramUsage = ramUsage;
        }

        public double getJvmUsage() {
            return jvmUsage;
        }

        public void setJvmUsage(double jvmUsage) {
            this.jvmUsage = jvmUsage;
        }

        public double getSwapUsage() {
            return swapUsage;
        }

        public void setSwapUsage(double swapUsage) {
            this.swapUsage = swapUsage;
        }

        public double getNiUsage() {
            return niUsage;
        }

        public void setNiUsage(double niUsage) {
            this.niUsage = niUsage;
        }

        public double getNoUsage() {
            return noUsage;
        }

        public void setNoUsage(double noUsage) {
            this.noUsage = noUsage;
        }

        public double getDiskUsage() {
            return diskUsage;
        }

        public void setDiskUsage(double diskUsage) {
            this.diskUsage = diskUsage;
        }

    }


}
