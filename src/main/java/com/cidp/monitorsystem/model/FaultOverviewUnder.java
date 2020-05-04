package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;

/**
 * @date 2020/5/2 -- 15:19
 **/
@Data
public class FaultOverviewUnder {
    private List<Equipment> equipmentList;
    private List<EquipmentFailuresNums> equipmentFailuresNumList;
    private List<SixTime> sixTimes;
}
