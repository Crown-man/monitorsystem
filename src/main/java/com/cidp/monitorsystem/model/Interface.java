package com.cidp.monitorsystem.model;

import lombok.Data;



@Data
public class Interface {
    private String ip;//所属设备ip
    private String Ifindex;//接口索引
    private String IfDescr;//接口名称
    private String IfType;//网络接口类型
    private String IfOperStatus;//接口当前操作状态[up|down]
    private String IfAdminStatus;//接口期待的状态，此字段可以控制网络设备端口的开启或关闭
    private String IfSpeed;//接口带宽 单位比特 b
    private String IfMtu;//接口最大数据单元
}
