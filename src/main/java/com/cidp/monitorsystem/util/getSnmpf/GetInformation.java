package com.cidp.monitorsystem.util.getSnmpf;

import org.snmp4j.*;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.List;

public class GetInformation {

    public static List<TableEvent> getCpuInfo(String ip) throws Exception {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String[] oids = {"1.3.6.1.2.1.25.3.3.1.2"};
//        String[] oids = {".1.3.6.1.2.1.1.3.0"};
            target = InitSnmp.Init(ip,"161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[oids.length];
            for (int i = 0; i < oids.length; i++)
                columns[i] = new OID(oids[i]);
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            return list;
//            if(list.size()==1 && list.get(0).getColumns()==null){
//                throw new  Exception("未获取到信息！");
//            }else{
//                int percentage = 0;
//                for(TableEvent event : list){
//                    VariableBinding[] values = event.getColumns();
//                    SystemInfo.out.println(values[0].getVariable());
//                    if(values != null)
//                        percentage += Integer.parseInt(values[0].getVariable().toString());
//                }
//                SystemInfo.out.println("CPU利用率为："+percentage/list.size()+"%");
//            }
    }
    public static void getMemoryInfo() throws IOException {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target =null ;
        String[] oids = {"1.3.6.1.2.1.25.2.3.1.2",  //type 存储单元类型
                "1.3.6.1.2.1.25.2.3.1.3",  //descr
                "1.3.6.1.2.1.25.2.3.1.4",  //unit 存储单元大小
                "1.3.6.1.2.1.25.2.3.1.5",  //size 总存储单元数
                "1.3.6.1.2.1.25.2.3.1.6"}; //used 使用存储单元数;
        String PHYSICAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.2";//物理存储
        String VIRTUAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.3"; //虚拟存储
        try {
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[oids.length];
            for (int i = 0; i < oids.length; i++)
                columns[i] = new OID(oids[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            if(list.size()==1 && list.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : list){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    int unit = Integer.parseInt(values[2].getVariable().toString());//unit 存储单元大小
                    int totalSize = Integer.parseInt(values[3].getVariable().toString());//size 总存储单元数
                    int usedSize = Integer.parseInt(values[4].getVariable().toString());//used  使用存储单元数
                    String oid = values[0].getVariable().toString();
                    if (PHYSICAL_MEMORY_OID.equals(oid)){
                        System.out.println("PHYSICAL_MEMORY----->物理内存大小："+(long)totalSize * unit/(1024*1024)+"M   内存使用率为："+(long)usedSize*100/totalSize+"%");
                    }else if (VIRTUAL_MEMORY_OID.equals(oid)&&unit!=0&&totalSize!=0) {
                        System.out.println("VIRTUAL_MEMORY----->虚拟内存大小："+(long)totalSize * unit/(1024*1024)+"M   内存使用率为："+(long)usedSize*100/totalSize+"%");
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(transport!=null)
                    transport.close();
                if(snmp!=null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //获取磁盘相关信息
    public static List<TableEvent> getDiskInfo() throws IOException {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String DISK_OID = "1.3.6.1.2.1.25.2.1.4";
        String[] oids = {"1.3.6.1.2.1.25.2.3.1.2",  //type 存储单元类型
                "1.3.6.1.2.1.25.2.3.1.3",  //descr
                "1.3.6.1.2.1.25.2.3.1.4",  //unit 存储单元大小
                "1.3.6.1.2.1.25.2.3.1.5",  //size 总存储单元数
                "1.3.6.1.2.1.25.2.3.1.6"}; //used 使用存储单元数;
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[oids.length];
            for (int i = 0; i < oids.length; i++)
                columns[i] = new OID(oids[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            return list;
    }

    //服务器进程集合信息
    public static void getProcess() {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String[] oids =
                {"1.3.6.1.2.1.25.4.2.1.1",  //index
                        "1.3.6.1.2.1.25.4.2.1.2",  //name
                        "1.3.6.1.2.1.25.4.2.1.4",  //run path
                        "1.3.6.1.2.1.25.4.2.1.6",  //type
                        "1.3.6.1.2.1.25.5.1.1.1",  //cpu
                        "1.3.6.1.2.1.25.5.1.1.2"}; //memory
        try {
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[oids.length];
            for (int i = 0; i < oids.length; i++)
                columns[i] = new OID(oids[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            if(list.size()==1 && list.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : list){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    String name = values[1].getVariable().toString();//name
                    String cpu = values[4].getVariable().toString();//cpu
                    String memory = values[5].getVariable().toString();//memory
                    String path = values[2].getVariable().toString();//path
                    System.out.println("name--->"+name+"  cpu--->"+cpu+"  memory--->"+memory+"  path--->"+path);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(transport!=null)
                    transport.close();
                if(snmp!=null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //服务器系统服务集合
    public static void getService() {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String[] oids = {"1.3.6.1.2.1.77.1.2.2.1"};
        try {
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[oids.length];
            for (int i = 0; i < oids.length; i++)
                columns[i] = new OID(oids[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            if(list.size()==1 && list.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : list){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    String name = values[0].getVariable().toString();//name
                    System.out.println("名称--->"+name);//中文乱码，需要转为utf-8编码
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(transport!=null)
                    transport.close();
                if(snmp!=null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //服务器接口集合
    public static void getInterface() {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String[] IF_OIDS =
                {"1.3.6.1.2.1.2.2.1.1",  //Index
                        "1.3.6.1.2.1.2.2.1.2",  //descr
                        "1.3.6.1.2.1.2.2.1.3",  //type
                        "1.3.6.1.2.1.2.2.1.5",  //speed
                        "1.3.6.1.2.1.2.2.1.6",  //mac
                        "1.3.6.1.2.1.2.2.1.7",  //adminStatus
                        "1.3.6.1.2.1.2.2.1.8",  //operStatus

                        "1.3.6.1.2.1.2.2.1.10", //inOctets
                        "1.3.6.1.2.1.2.2.1.16", //outOctets
                        "1.3.6.1.2.1.2.2.1.14", //inError
                        "1.3.6.1.2.1.2.2.1.20", //outError
                        "1.3.6.1.2.1.2.2.1.13", //inDiscard
                        "1.3.6.1.2.1.2.2.1.19", //outDiscard
                        "1.3.6.1.2.1.2.2.1.11", //inUcastPkts
                        "1.3.6.1.2.1.2.2.1.17", //outUcastPkts
                        "1.3.6.1.2.1.2.2.1.12", //inNUcastPkts
                        "1.3.6.1.2.1.2.2.1.18"};//outNUcastPkts
        String[] IP_OIDS =
                {"1.3.6.1.2.1.4.20.1.1", //ipAdEntAddr
                        "1.3.6.1.2.1.4.20.1.2", //ipAdEntIfIndex
                        "1.3.6.1.2.1.4.20.1.3"};//ipAdEntNetMask
        try {
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[IF_OIDS.length];
            for (int i = 0; i < IF_OIDS.length; i++)
                columns[i] = new OID(IF_OIDS[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            if(list.size()==1 && list.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : list){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    System.out.println("interface ---Index："+values[0].getVariable().toString()+"  descr："+values[1].getVariable().toString()+"  type："+values[2].getVariable().toString()+" speed："+values[3].getVariable().toString()+" mac："+values[4].getVariable().toString()+" adminStatus："+values[5].getVariable().toString()+"  operStatus："+values[6].getVariable().toString());
                }
            }
//获取ip
            OID[] ipcolumns = new OID[IP_OIDS.length];
            for (int i = 0; i < IP_OIDS.length; i++)
                ipcolumns[i] = new OID(IP_OIDS[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> iplist = tableUtils.getTable(target, ipcolumns, null, null);
            if(iplist.size()==1 && iplist.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : iplist){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    System.out.println(" IP--->ipAdEntAddr:"+values[0].getVariable().toString()+"   ipAdEntIfIndex:"+values[1].getVariable().toString()+"   ipAdEntNetMask:"+values[2].getVariable().toString());
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(transport!=null)
                    transport.close();
                if(snmp!=null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //服务器端口集合
    public static void getPort() {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String[] TCP_CONN = {"1.3.6.1.2.1.6.13.1.1", //status
                "1.3.6.1.2.1.6.13.1.3"}; //port

        String[] UDP_CONN = {"1.3.6.1.2.1.7.5.1.2"};
        try {
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            //获取TCP 端口
            OID[] columns = new OID[TCP_CONN.length];
            for (int i = 0; i < TCP_CONN.length; i++)
                columns[i] = new OID(TCP_CONN[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            if(list.size()==1 && list.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : list){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    int status = Integer.parseInt(values[0].getVariable().toString());
                    System.out.println("status--->"+status+"   TCP_port--->"+values[1].getVariable().toString());
                }
            }
//获取udp 端口
            OID[] udpcolumns = new OID[UDP_CONN.length];
            for (int i = 0; i < UDP_CONN.length; i++)
                udpcolumns[i] = new OID(UDP_CONN[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> udplist = tableUtils.getTable(target, udpcolumns, null, null);
            if(udplist.size()==1 && udplist.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : udplist){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    String name = values[0].getVariable().toString();//name
                    System.out.println("UDP_port--->"+name);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(transport!=null)
                    transport.close();
                if(snmp!=null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //服务器安装软件集合
    public static void getSoft() {
        TransportMapping transport = null ;
        Snmp snmp = null ;
        CommunityTarget target;
        String[] oids =
                {	"1.3.6.1.2.1.25.6.3.1.2",  //software
                        "1.3.6.1.2.1.25.6.3.1.4",  //type
                        "1.3.6.1.2.1.25.6.3.1.5"}; //install date
        try {
            target = InitSnmp.Init("123.56.16.15","161","public");//创建连接目标
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);//创建snmp
            snmp.listen();//监听消息
            TableUtils tableUtils = new TableUtils(snmp, new PDUFactory() {
                @Override
                public PDU createPDU(Target arg0) {
                    PDU request = new PDU();
                    request.setType(PDU.GET);
                    return request;
                }

                @Override
                public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                    return null;
                }
            });
            OID[] columns = new OID[oids.length];
            for (int i = 0; i < oids.length; i++)
                columns[i] = new OID(oids[i]);
            @SuppressWarnings("unchecked")
            List<TableEvent> list = tableUtils.getTable(target, columns, null, null);
            if(list.size()==1 && list.get(0).getColumns()==null){
                System.out.println(" null");
            }else{
                for(TableEvent event : list){
                    VariableBinding[] values = event.getColumns();
                    if(values == null) continue;
                    String software = values[0].getVariable().toString();//software
                    String type = values[1].getVariable().toString();//type
                    String date = values[2].getVariable().toString();//date
                    System.out.println("软件名称--->"+software+"  type--->"+type+"  安装时间--->"+hexToDateTime(date.replace("'", "")));
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(transport!=null)
                    transport.close();
                if(snmp!=null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取磁盘的中文名字
     * 解决snmp4j中文乱码问题
     */
    public static String getChinese(String octetString){
        if(octetString == null || "".equals(octetString)
                || "null".equalsIgnoreCase(octetString)) return "";
        try{
            String[] temps = octetString.split(":");
            byte[] bs = new byte[temps.length];
            for(int i=0;i<temps.length;i++)
                bs[i] = (byte)Integer.parseInt(temps[i],16);
            return new String(bs,"GB2312");
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 将16进制的时间转换成标准的时间格式
     */
    private static String hexToDateTime(String hexString) {
        if(hexString == null || "".equals(hexString))
            return "";
        String dateTime = "";
        try {
            byte[] values = OctetString.fromHexString(hexString).getValue();
            int year, month, day, hour, minute;

            year = values[0] * 256 + 256 + values[1];
            month = values[2];
            day = values[3];
            hour = values[4];
            minute = values[5];

            char format_str[] = new char[22];
            int index = 3;
            int temp = year;
            for (; index >= 0; index--) {
                format_str[index] = (char) (48 + (temp - temp / 10 * 10));
                temp /= 10;
            }
            format_str[4] = '-';
            index = 6;
            temp = month;
            for (; index >= 5; index--) {
                format_str[index] = (char) (48 + (temp - temp / 10 * 10));
                temp /= 10;
            }
            format_str[7] = '-';
            index = 9;
            temp = day;
            for (; index >= 8; index--) {
                format_str[index] = (char) (48 + (temp - temp / 10 * 10));
                temp /= 10;
            }
            format_str[10] = ' ';
            index = 12;
            temp = hour;
            for (; index >= 11; index--) {
                format_str[index] = (char) (48 + (temp - temp / 10 * 10));
                temp /= 10;
            }
            format_str[13] = ':';
            index = 15;
            temp = minute;
            for (; index >= 14; index--) {
                format_str[index] = (char) (48 + (temp - temp / 10 * 10));
                temp /= 10;
            }
            dateTime = new String(format_str,0,format_str.length).substring(0, 16);
        } catch (Exception e) {
//LogFactory.getLog(getClass()).error(e);
        }
        return dateTime;
    }
}
