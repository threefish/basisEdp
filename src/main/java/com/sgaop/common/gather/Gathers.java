package com.sgaop.common.gather;

import java.util.*;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * 系统信息全收集
 */
public class Gathers {

	public static HashMap all() throws SigarException {
        Sigar sigar = new Sigar();
		HashMap data = new HashMap();
		List<Date> timePoints = new ArrayList();
		CPUGather cpu = CPUGather.gather(sigar);
		data.put("cpu", cpu);
		data.put("cpuUsage", cpu.getPerc().getCombined() * 100);

		MemoryGather memory = MemoryGather.gather(sigar);
		data.put("memory", memory);
		data.put("ramUasge", memory.getMem().getUsedPercent());
		data.put("jvmUasge", memory.getJvm().getUsedPercent());
		if (memory.getSwap().getTotal() == 0) {
			data.put("swapUasge", 0);
		} else {
			data.put("swapUasge", memory.getSwap().getUsed() * 100 / memory.getSwap().getTotal());
		}

		List<DISKGather> disks = DISKGather.gather(sigar);
		data.put("disk", disks);
		long totle = 0, used = 0;
		for (DISKGather disk : disks) {
			if (disk.getStat() != null) {
				totle += disk.getStat().getTotal();
				used += disk.getStat().getUsed();
			}
		}
		data.put("diskUsage", used * 100 / totle);

		NetInterfaceGather ni = NetInterfaceGather.gather(sigar);
		data.put("network", ni);
		data.put("niUsage", ni.getRxbps() * 100 / ni.getStat().getSpeed());
		data.put("noUsage", ni.getTxbps() * 100 / ni.getStat().getSpeed());

		data.put("system", OSGather.init(sigar));
		return data;
	}
}