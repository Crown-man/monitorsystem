package com.cidp.monitorsystem.model;

import lombok.Data;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/213:49
 **/
@Data
public class Arp {
    private Integer id;//本机ip
    private String localIp;
    private String ip;//arp 表中的目标ip
    private String mac;//arp 表中的目标mac
    private String time;
}
