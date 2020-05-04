package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * @description: 接口流量监控
 * @author: Zdde丶
 * @create: 2020/4/813:40
 **/
@Data
public class InterFlow {
    private String ip;//所属设备
    private String inFlow;//接收流量
    private String outFlow;//发送流量
    private String ifindex;//端口索引
    private String interDescr;//端口描述
    private String inLoss;//接收丢包率
    private String outLoss;//发送丢包率
    private String inRate;//接收率
    private String outRate;//发送率
    private String inErrorRate;//接收错误率
    private String outErrorRate;//发送错误率
    private String time;//监控时间

}
