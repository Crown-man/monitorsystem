package com.cidp.monitorsystem.controller;

import com.cidp.monitorsystem.model.Diagnosis;
import com.cidp.monitorsystem.model.ExportDiagnosis;
import com.cidp.monitorsystem.model.RespBean;
import com.cidp.monitorsystem.service.dispservice.DiagnosisService;
import com.cidp.monitorsystem.util.ExcellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {
    @Autowired
    DiagnosisService diagnosisService;
    @GetMapping("/")
    public List<Diagnosis> getAllInfo(){
        return diagnosisService.getAllInfo();
    }
    @PostMapping("/")
    public RespBean remark(@RequestParam String desc,@RequestParam String id){
        return diagnosisService.updateRemark(desc,id)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    @PutMapping("/check/remark")
    public RespBean updateSelectRemark(@RequestParam String desc,@RequestParam String[] idsOne){
       return diagnosisService.updateSelectRemark(desc,idsOne)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }
    @GetMapping("/remark/{id}")
    public String getRemark(@PathVariable String id){
        return diagnosisService.getRemark(id);
    }
    //处理选择的故障
    @PostMapping("/check/handle")
    public RespBean checkRemark(@RequestParam String desc,@RequestParam String[] ids){
        return diagnosisService.updateCheckremark(desc,ids)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //单个故障忽略
    @PostMapping("/ignore")
    public RespBean ignore(@RequestParam String id){
        return diagnosisService.updateIgnore(id)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //选择的故障忽略
    @PostMapping("/check/ignore")
    public RespBean checkIgnore(@RequestParam String[] ids){
        return diagnosisService.updateCheckIgnore(ids)==1 ? RespBean.ok("更新成功!") :RespBean.error("更新失败!");
    }

    //单个删除
    @DeleteMapping("/delete")
    public RespBean delete(@RequestParam String id){
        return diagnosisService.deleteById(id)==1 ? RespBean.ok("删除成功!") :RespBean.error("删除失败!");
    }
    //选中删除
    @DeleteMapping("/check/delete")
    public RespBean checkDelete(@RequestParam String[] ids){
        return diagnosisService.checkDeleteById(ids)==1 ? RespBean.ok("删除成功!") :RespBean.error("删除失败!");
    }
    //导出未处理警告
    @GetMapping("/export/unhandledwarnings")
    public void exportUnhandledWarnings(HttpServletResponse response){
        List<ExportDiagnosis>  diagnosisList= diagnosisService.selectUnhandledDiagnosisInfo();
        com.alibaba.fastjson.JSONArray ja= (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(diagnosisList);
        //LinkedHashMap保留插入的顺序（key,value）
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ip","结点");
        headMap.put("zhtype","检测点");
        headMap.put("time","开始时间");
        headMap.put("cause","详细信息");
        String title = "未处理警告";
        ExcellUtil.downloadExcelFile(title,headMap,ja,response);
    }
    //导出正在处理警告
    @GetMapping("/export/processedwarnings")
    public void exportProcessedWarnings(HttpServletResponse response){
        List<ExportDiagnosis>  diagnosisList= diagnosisService.selectProcessedDiagnosisInfo();
        com.alibaba.fastjson.JSONArray ja= (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(diagnosisList);
        //LinkedHashMap保留插入的顺序（key,value）
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ip","结点");
        headMap.put("zhtype","检测点");
        headMap.put("time","开始时间");
        headMap.put("cause","详细信息");
        String title = "正在处理处理警告";
        ExcellUtil.downloadExcelFile(title,headMap,ja,response);
    }

    //导出已已忽略警告
    @GetMapping("/export/ignorewarnings")
    public void exportIgnoreWarnings(HttpServletResponse response){
        List<ExportDiagnosis>  diagnosisList= diagnosisService.selectIgnoreDiagnosisInfo();
        com.alibaba.fastjson.JSONArray ja= (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(diagnosisList);
        //LinkedHashMap保留插入的顺序（key,value）
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("ip","结点");
        headMap.put("zhtype","检测点");
        headMap.put("time","开始时间");
        headMap.put("cause","详细信息");
        String title = "已忽略警告";
        ExcellUtil.downloadExcelFile(title,headMap,ja,response);
    }

}
