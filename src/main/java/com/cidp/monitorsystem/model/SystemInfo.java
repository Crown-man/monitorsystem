package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/6 17:31
 **/
@Data
public class SystemInfo {
    private Integer id;
    private String ip;
    private String sysDecsr;
    private String sysObjectID;
    private String sysUpTime;
    private String sysContact;
    private String sysName;
    private String sysLocation;
    private String sysService;
    private String time;
    private String selfDecs;//设备描述
    private String typeName;//设备类型
    private String name;//设备名称
    private String icon;//设备图标
}
