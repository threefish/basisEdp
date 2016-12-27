package com.sgaop.common.util;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: 306955302@qq.com
 * Date: 2016/12/2 0002
 * To change this template use File | Settings | File Templates.
 */
public class InternetUtil {


    /**
     * @param ip      ip地址
     * @param timeOut 超时时间(毫秒)
     * @return 当返回值是true时，说明host是可用的，false则不可用
     * @throws Exception
     */
    public static boolean ping(String ip, int timeOut) {
        try {
            return InetAddress.getByName(ip).isReachable(timeOut);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param ip   ip地址
     * @param port 端口
     * @return 当返回值是true时，t是可用的，false则不可用
     */
    public static boolean connect(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
