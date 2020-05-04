package com.cidp.monitorsystem.handler;

import com.cidp.monitorsystem.config.TaskJobUtil;
import com.cidp.monitorsystem.service.CpuService;
import com.cidp.monitorsystem.service.InterFlowService;
import com.cidp.monitorsystem.service.MemoryService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskJob {
    CpuService cpuService = (CpuService) TaskJobUtil.getBeans("cpuService");
    InterFlowService interFlowService = (InterFlowService) TaskJobUtil.getBeans("interFlowService");
    MemoryService memoryService = (MemoryService) TaskJobUtil.getBeans("memoryService");
//    @Scheduled(cron = "0 0 0/4 * * ?")
    @Async
    public void getCpuInfo() throws Exception {
        cpuService.getAllDeviceCpu();
    }
//    @Scheduled(cron = "0 0 0/4 * * ?")
    public void getInterFlow() throws Exception {
        interFlowService.getFlow();
    }
//    @Scheduled(cron = "0 0 0/4 * * ?")
    public void getMemInfo() throws Exception {
        memoryService.getAllDeviceMem();
    }
}
