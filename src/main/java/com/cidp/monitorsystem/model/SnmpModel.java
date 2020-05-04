package com.cidp.monitorsystem.model;

import lombok.Data;

@Data
public class SnmpModel {
    private String communityName;
    private String hostIp;
    private Integer port;
    private int version;
    private int async;//是否同步查询

    private String serverId; // 受监控服务器标识
    private String code; // 受监控服务器编码
    private String name; // 受监控服务器名称
    private String type; // 服务器的应用类型（如应用服务，数据库服务，前置机服务器），在维护时输入，在界面中相应字段呈现
    private String systemName; // 受监控服务器操作系统
    private String ip; // 受监控服务器IP地址
    private String address; // 受监控服务的存放地址
    private String statusid; // 状态(1为可用，0为不可用，默认为1)，用于是否对这个服务器进行监控
    private String remark; // 备注
    private String cpu;
    private String memory;
    private String time;
    private boolean ethernetConnection;

    // 服务service字段
    private String serviceId; // 受监控服务标识
    private String serviceName; // 受监控服务名称
    private String serverName; // 受监控服务所在服务器名称
    private String serverIp; // 受监控服务所在服务器IP
    private String processeName; // 受监控服务进行名称
    private String serviceStatus; // 状态（1为可用，0为禁用，默认值为1），用于是否对这个服务进程进行监控
    private String serviceRemark; // 备注
}
