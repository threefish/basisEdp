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
import com.sgaop.entity.sys.AlarmOption;
import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.DecimalFormat;
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
 * 主机状态监控
 */
@IocBean
public class ApmJob implements Job {

    protected static final Logger log = Logger.getRootLogger();


    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Inject("dao")
    protected Dao dao;

    private Sigar sigar = new Sigar();

    /**
     * 时间点
     */
    private List<Date> timePoints = new ArrayList();
    /**
     * cpu使用情况
     */
    private List<Double> cpuUsages = new ArrayList();
    /**
     * ram使用情况
     */
    private List<Double> ramUsages = new ArrayList();
    /**
     * jvm使用情况
     */
    private List<Double> jvmUsages = new ArrayList();

    /**
     * swap使用情况
     */
    private List<Double> swapUsages = new ArrayList();

    /**
     * 流量下行使用情况
     */
    private List<Double> niUsages = new ArrayList();

    /**
     * 流量上行使用情况
     */
    private List<Double> noUsages = new ArrayList();

    /**
     * 监听项目
     */
    private List<AlarmOption> alarmOptions = new ArrayList();

    /**
     * 是否开启监听
     */
    private boolean listenerStatus = true;

    /**
     * 缓存监控多少个最近使用情况
     */
    private int monitorCount = 25;

    /**
     * 缓存最近的一个状态
     */
    private HashMap usaGes = new HashMap();

    /**
     * 取得临时缓存的最近多个系统状态信息
     *
     * @return
     */
    public HashMap getMoreTempAll() {
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
     * 取得临时缓存的最近1个系统状态信息
     *
     * @return
     */
    public HashMap getOneTempAll() {
        return usaGes;
    }


    /**
     * 更新主机监控项目
     *
     * @return
     */
    public void setAlarmOptions(List<AlarmOption> alarmOptions) {
        this.alarmOptions = alarmOptions;
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
     * 插入告警表
     *
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
        alarm.setMsg(String.format("%s:当前 %s 使用率 %f %,高于预警值 %d", title, device, usage, alarmPoint));
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
            log.debug("执行系统性能曲线监测与收集任务");
            if (alarmOptions.size() == 0) {
                alarmOptions = dao.queryAll(AlarmOption.class);
                //如果还是为0,就不监控了
                if (alarmOptions.size() == 0) {
                    listenerStatus = false;
                }
            }
            if (listenerStatus) {
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
                        alarm(APMAlarm.Type.DISK, "磁盘警告", "DISK", disk.getStat().getUsePercent() * 100, PropertiesManager.getInt("disk.alarm.percent"));
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


                usaGes.put("CPU", decimalFormat.format(cpuUsage));
                usaGes.put("RAM", decimalFormat.format(ramUsage));
                usaGes.put("JVM", decimalFormat.format(jvmUsage));
                usaGes.put("SWAP", decimalFormat.format(swapUsage));
                double diskPercent = 0.00;
                for (DISKGather disk : disks) {
                    if (disk.getStat() != null) {
                        diskPercent += disk.getStat().getUsePercent();
                    }
                }
                usaGes.put("DISK", decimalFormat.format(diskPercent * 100));
                usaGes.put("NiNetWork",niUsage);
                usaGes.put("NoNetWork",noUsage);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
}
