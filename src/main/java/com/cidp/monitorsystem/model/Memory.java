package com.cidp.monitorsystem.model;
import lombok.Data;


@Data
public class Memory {
    private Integer id;
    private String ip;//所属设备
    private Double memTotalReal;//总的物理内存kb
    private Double memAvailReal;//可以使用的内存kb
    private Double memRate;//内存使用率百分比%
    private String time;
}
