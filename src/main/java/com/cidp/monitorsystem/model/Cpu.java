package com.cidp.monitorsystem.model;

import lombok.Data;

@Data
public class Cpu {
    private Integer id;
    private String ip;//所属设备
    private Double cpuRate;//cpu使用率
    //private Integer hrProcessorLoad;//CPU的当前负载，N个核就有N个负载
    private String time;

}
