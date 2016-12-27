package com.sgaop.common.gather;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 网卡信息收集器
 */
public class NetInterfaceGather {

    private static String ip;

    private static String hostName;


    static {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private NetInterfaceConfig config;
    private NetInterfaceStat stat;
    private long rxbps;
    private long txbps;

    private static long rTemp = 0;

    private static long tTemp = 0;

    private static String activeCard = null;

    private static Date last;

    public NetInterfaceGather() {
    }

    public void populate(Sigar sigar, String name) throws SigarException {

        config = sigar.getNetInterfaceConfig(name);

        try {

            NetInterfaceStat statStart = sigar.getNetInterfaceStat(name);
            if (rTemp == 0 || tTemp == 0) {
                rTemp = statStart.getRxBytes();
                tTemp = statStart.getTxBytes();
                last = new Date();
            } else {
                long rt = statStart.getRxBytes();
                long tt = statStart.getTxBytes();
                Date now = new Date();

                rxbps = (rt - rTemp) * 1000 * 1024 / (now.getTime() - last.getTime());
                txbps = (tt - tTemp) * 1000 * 1024 / (now.getTime() - last.getTime());

                rTemp = rt;
                tTemp = tt;
                last = now;
            }

            stat = sigar.getNetInterfaceStat(name);
        } catch (SigarException e) {

        } catch (Exception e) {

        }
    }

    public static NetInterfaceGather gather(Sigar sigar) throws SigarException {
        return NetInterfaceGather.gather(sigar, fetActiveNetInterfaceName(sigar));
    }

    private static String fetActiveNetInterfaceName(final Sigar sigar) throws SigarException {

        sw:
        for (String name : sigar.getNetInterfaceList()) {
            if (sigar.getNetInterfaceConfig(name).getAddress().equals(ip)) {
                activeCard = name;
                break sw;
            }
        }
        return activeCard;
    }

    public static NetInterfaceGather gather(Sigar sigar, String name) throws SigarException {
        NetInterfaceGather data = new NetInterfaceGather();
        data.populate(sigar, name);
        return data;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @return the config
     */
    public NetInterfaceConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(NetInterfaceConfig config) {
        this.config = config;
    }

    /**
     * @return the stat
     */
    public NetInterfaceStat getStat() {
        return stat;
    }

    /**
     * @param stat the stat to set
     */
    public void setStat(NetInterfaceStat stat) {
        this.stat = stat;
    }

    /**
     * @return the rxbps
     */
    public long getRxbps() {
        return rxbps;
    }

    /**
     * @param rxbps the rxbps to set
     */
    public void setRxbps(long rxbps) {
        this.rxbps = rxbps;
    }

    /**
     * @return the txbps
     */
    public long getTxbps() {
        return txbps;
    }

    /**
     * @param txbps the txbps to set
     */
    public void setTxbps(long txbps) {
        this.txbps = txbps;
    }

}