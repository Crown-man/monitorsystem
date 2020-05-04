package com.cidp.monitorsystem.model;

import lombok.Data;

import java.util.List;

@Data
public class SixTime {
    List<EquipmentFailuresNums> one;//8:00~12：00
    List<EquipmentFailuresNums> two;//12:00~16:00
    List<EquipmentFailuresNums> three;//16:00~20:00
    List<EquipmentFailuresNums> four;//20:00~24:00
    List<EquipmentFailuresNums> five;//24:00~4:00
    List<EquipmentFailuresNums> six;//4:00~8:00
}
