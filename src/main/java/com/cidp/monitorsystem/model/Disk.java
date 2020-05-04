package com.cidp.monitorsystem.model;

import lombok.Data;
@Data
public class Disk {
    private String id; //所属服务器
    private Long diskCapacity;//磁盘大小
    private String diskName;//磁盘名称
    private Long diskRate;//磁盘使用率
    private String time;
}
