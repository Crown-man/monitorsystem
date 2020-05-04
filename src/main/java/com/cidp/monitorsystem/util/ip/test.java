package com.cidp.monitorsystem.util.ip;
import java.util.List;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/411:28
 **/
public class test {
    public static void main(String[] args) throws Exception {

        List<String> strings = Ping.GetPingSuccess("172.17.137.115", 25);
        for (String string : strings) {
            System.out.println(string);
        }

    }
}
