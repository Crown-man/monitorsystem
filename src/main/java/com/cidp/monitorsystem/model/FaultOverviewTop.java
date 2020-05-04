package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;

/**
 * @date 2020/5/2 -- 16:15
 **/
@Data
public class FaultOverviewTop {
    List<FaultNums> faultNums;
    List<IpFault> Top10IpFaults;
}
