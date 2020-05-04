package com.cidp.monitorsystem.model;
import lombok.Data;
@Data
public class BaseInfo {
    private Integer id;
    private String ip;//服务器ip
    private String sysUptime;//系统运行时长
    private String sysContact; //系统联系人
    private String SysName; //获取机器名
    private String SysBaseInfo; //服务器基本信息
}
