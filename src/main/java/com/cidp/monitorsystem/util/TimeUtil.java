package com.cidp.monitorsystem.util;


import com.cidp.monitorsystem.model.Disk;
import com.cidp.monitorsystem.model.Memory;
import com.cidp.monitorsystem.service.DiskService;
import com.cidp.monitorsystem.service.MemoryService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeUtil {
    /**
     * 获取num个 sec 秒之前的时间
     * @param sec
     * @param num
     * @return 返回的时间格式为 HH:mm:ss
     */
    public static List<String> GetArrayTimeH(int sec,int num){
        Calendar c;
        Date date ;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        List<String> dates = new ArrayList<>();
        for (int i =0;i<num;i++){
            date = new Date();
            c = new GregorianCalendar();
            c.setTime(date);//设置参数时间
            c.add(Calendar.SECOND,-sec*i);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
            date=c.getTime();
            dates.add(df.format(date));
        }
        return dates;
    }

    /**
     * 获取num个 sec 秒之前的时间
     * @param sec
     * @param num
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static List<String> GetArrayTimeD(int sec,int num){
        Calendar c;
        Date date ;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> dates = new ArrayList<>();
        for (int i =0;i<5;i++){
            date = new Date();
            c = new GregorianCalendar();
            c.setTime(date);//设置参数时间
            c.add(Calendar.SECOND,-30*i);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
            date=c.getTime();
            dates.add(df.format(date));
        }
        return dates;
    }

    /**
     * 得到num个Memory类型的list集合
     * @param num
     * @return
     * @throws IOException
     */
    public static List<Memory> GetArrayMemory(int num) throws Exception {
        MemoryService memoryService = new MemoryService();
        List<Memory> list = new ArrayList<>();
        for (int i=0;i<num;i++){
            list.add(i,memoryService.GetInfo("123.56.16.15"));
        }
        return list;
    }

}
