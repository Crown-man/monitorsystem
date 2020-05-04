package com.cidp.monitorsystem.model;

import lombok.Data;


/**
 * @description: 交换机MAC地址转发信息
 * @author: Zdde丶
 * @create: 2020/4/515:20
 **/
@Data
public class dot1dTpFdbTable {
    private String ip; //交换机 vlan ip
    private String dot1dTpFdbAddress; //转发的目的MAC
    private String dot1dTpFdbPort; //转发出去对应的端口
    /**
     * dot1dTpFdbStatus
     * 这个条目的状态。这些值的含义是:
     * 其他(1):没有下列任何一种。这将包括使用其他一些MIB对象(不是dot1dTpFdbPort的对应实例，也不是dot1dStaticTable中的一个条目)来确定帧是否被转发以及如何转发到对应的dot1dTpFdbAddress实例的值。
     * 无效(2):这个条目不再有效(例如，它已被学习但已经过时)，但尚未从表中清除。
     * learned(3):对应实例dot1dTpFdbPort的值已经学会，正在使用。
     * self(4): dot1dTpFdbAddress的对应实例的值表示桥的一个地址。对应的dot1dTpFdbPort实例表明桥的哪个端口有这个地址。
     * mgmt(5): dot1dTpFdbAddress的对应实例的值也是dot1dStaticAddress的现有实例的值。
     **/
    private String dot1dTpFdbStatus; //端口对应的状态 有1 2 3 4 5种情况

}
