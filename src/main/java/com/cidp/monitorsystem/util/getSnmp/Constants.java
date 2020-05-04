package com.cidp.monitorsystem.util.getSnmp;

// 将用到的oid放在这个类中
public class Constants {
    /**
     * CPU负载 相关数据
     */
    //用户CPU百分比 请求方式GET
    public static final String ssCpuUser = ".1.3.6.1.4.1.2021.11.9.0";
    //系统CPU百分比 请求方式GET
    public static final String ssCpuSystem = ".1.3.6.1.4.1.2021.11.10.0";
    //空闲CPU百分比 请求方式GET
    public static final String ssCpuIdle = ".1.3.6.1.4.1.2021.11.11.0";
    //原始用户CPU使用时间 请求方式GET
    public static final String ssCpuRawUser = ".1.3.6.1.4.1.2021.11.50.0";
    //	原始nice占用时间 请求方式GET
    public static final String ssCpuRawNice = ".1.3.6.1.4.1.2021.11.51.0";
    //原始系统CPU使用时间 请求方式GET
    public static final String ssCpuRawSystem = ".1.3.6.1.4.1.2021.11.52.0";
    //原始CPU空闲时间 请求方式GET
    public static final String ssCpuRawIdle = ".1.3.6.1.4.1.2021.11.53.0";
    //CPU的当前负载，N个核就有N个负载 请求方式GETNEXT
    public static final String hrProcessorLoad = ".1.3.6.1.2.1.25.3.3.1.2";
    //用户会话数
    public static final String NumberofuserSessions = ".1.3.6.1.2.1.25.1.5.0";
    //进程数量
    public static final String ProcessQuantity = ".1.3.6.1.2.1.25.1.6.0";
    //网络下载速度
    public static final String NetworkDownloadSpeed = ".1.3.6.1.2.1.31.1.1.1.6";
    //获取系统基本信息
    public static final String GetBaseInfo = ".1.3.6.1.2.1.1.1.0";
    //获取系统名
    public static final String GetSysName = ".1.3.6.1.2.1.1.5.0";
    //获取机器坐标
    public static final String GetSysLocation = ".1.3.6.1.2.1.1.6.0";





    //接口当前带宽[bps]
    public static final String GetInterfacebps = ".1.3.6.1.2.1.2.2.1.5";
    //接口发送的字节数
    public static final String GetIfOutOctet = ".1.3.6.1.2.1.2.2.1.16";






    public static final String[] ifOids=new String[]{
            ".1.3.6.1.2.1.2.2.1.1",
            ".1.3.6.1.2.1.2.2.1.2",
            ".1.3.6.1.2.1.2.2.1.3",
            ".1.3.6.1.2.1.2.2.1.4",
            ".1.3.6.1.2.1.2.2.1.5",
            ".1.3.6.1.2.1.2.2.1.6",
            ".1.3.6.1.2.1.2.2.1.7",
            ".1.3.6.1.2.1.2.2.1.8",
            ".1.3.6.1.2.1.2.2.1.9",
            ".1.3.6.1.2.1.2.2.1.10",
            ".1.3.6.1.2.1.2.2.1.11",
            ".1.3.6.1.2.1.2.2.1.12",
            ".1.3.6.1.2.1.2.2.1.13",
            ".1.3.6.1.2.1.2.2.1.14",
            ".1.3.6.1.2.1.2.2.1.15",
            ".1.3.6.1.2.1.2.2.1.16",
            ".1.3.6.1.2.1.2.2.1.17",
            ".1.3.6.1.2.1.2.2.1.18",
            ".1.3.6.1.2.1.2.2.1.19",
            ".1.3.6.1.2.1.2.2.1.20",
            ".1.3.6.1.2.1.2.2.1.21",
            ".1.3.6.1.2.1.2.2.1.22"
    };
}