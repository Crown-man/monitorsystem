package com.cidp.monitorsystem.util.ip;

import com.cidp.monitorsystem.util.getSnmp.SNMPSessionUtil;
import org.snmp4j.PDU;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/411:59
 **/
public class Ping {
    /**
     * @param ipAddress
     * @return boolean
     * @Description 检查远端设备是否能够ping通
     * @Date 2020/4/4 11:59
     * 当返回值是true时，说明host是可用的，false则不可。
     **/
    public static boolean ping(String ipAddress) throws Exception {
        int timeOut = 3000;  //超时应该在3钞以上
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        return status;
    }

    /**
     * @param ipAddress
     * @return void
     * @Description 调用CMD的方式展示ping结构
     * @Date 2020/4/4 12:00
     **/
    public static void ping02(String ipAddress) throws Exception {
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(), Charset.forName("GBK")));
            while ((line = buf.readLine()) != null)
                System.out.println(line);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param ipAddress
     * @param pingTimes
     * @param timeOut
     * @return boolean
     * @Description //调用系统命令实现ping
     * @Date 2020/4/4 15:00
     **/
    public static boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        try {   // 执行命令并获取输出
//            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param line
     * @return int
     * @Description //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     * @Date 2020/4/4 14:59
     **/
    private static int getCheckResult(String line) {  // SystemInfo.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    /**
     * @param destHost 目标网段的任意ip
     * @param netmask  目标网段的掩码
     * @return java.util.List<java.lang.String>
     * @Description //得到能够ping通的ip
     * @Date 2020/4/4 14:58
     **/
    public static List<String> GetPingSuccess(String destHost, int netmask) throws Exception {
        List<String> strings = GetRangeIpUtil.GetIpListWithMask(destHost, netmask);
        ArrayList<String> list = new ArrayList<>();
        for (String ip : strings) {
            System.out.println("正在ping：" + ip);
            if (Ping.ping(ip)) {
                list.add(ip);
            }
        }
        return list;
    }

    public static boolean GetSnmpSuccess(String ip) throws Exception {
        SNMPSessionUtil snmpSessionUtil = new SNMPSessionUtil(ip, "161", "public", "2");
        System.out.println("正在尝试连接Snmp：" + ip);
        if ("success connection!".equals(snmpSessionUtil.getIsSnmpGet(PDU.GET, ".1.3.6.1.2.1.4.22.1.3.0").get(0))) {
            return true;
        } else {
            return false;
        }
    }

    public static String sping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        try {   // 执行命令并获取输出
//            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null) {
                return "";
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            String con ="";
            while ((line = in.readLine()) != null) {
                con+=line;
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return con;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return "异常Exception";
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
