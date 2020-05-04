package com.cidp.monitorsystem.service;

import com.cidp.monitorsystem.model.Disk;
import com.cidp.monitorsystem.util.TimeUtil;
import com.cidp.monitorsystem.util.getSnmpf.GetInformation;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.TableEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DiskService {
    public List<Disk> GetDiskInfo() throws IOException {
        List<Disk> disks = new ArrayList<>();
        Disk disk;
        String DISK_OID = "1.3.6.1.2.1.25.2.1.4";
        List<TableEvent> diskInfo = GetInformation.getDiskInfo();
        if(diskInfo.size()==1 && diskInfo.get(0).getColumns()==null){
            throw new NullPointerException("未收到查询信息！");
        }else{
            int i=0;
            for(TableEvent event : diskInfo){
                disk = new Disk();
                disk.setId("123.56.16.15");
                VariableBinding[] values = event.getColumns();
                if(values == null ||!DISK_OID.equals(values[0].getVariable().toString()))
                    continue;
                int unit = Integer.parseInt(values[2].getVariable().toString());//unit 存储单元大小
                int totalSize = Integer.parseInt(values[3].getVariable().toString());//size 总存储单元数
                int usedSize = Integer.parseInt(values[4].getVariable().toString());//used  使用存储单元数
                disk.setDiskName(values[1].getVariable().toString());
                disk.setDiskCapacity((long)totalSize*unit/(1024*1024));
                disk.setDiskRate((long)usedSize*100/totalSize);
                disk.setTime(TimeUtil.GetArrayTimeH(10, 5).get(i));
                disks.add(disk);
                i++;
            }
        }
        return disks;
    }
}
