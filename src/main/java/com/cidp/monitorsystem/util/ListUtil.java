package com.cidp.monitorsystem.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Zdde丶
 * @create: 2020/4/610:37
 **/
public class ListUtil {

    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
    /*
     * List分割
     */
    public static List<List<String>> groupList(List<String> list) {
        List<List<String>> listGroup = new ArrayList<List<String>>();
        int listSize = list.size();
        //子集合的长度
        int toIndex = 2;
        for (int i = 0; i < list.size(); i += 2) {
            if (i + 2 > listSize) {
                toIndex = listSize - i;
            }
            List<String> newList = list.subList(i, i + toIndex);
            listGroup.add(newList);
        }
        return listGroup;
    }

    /**
     * 得到两集合不同的元素
     */
    public static List<String> getDiffrent(List<String> list1, List<String> list2){
        Map<String,Integer> map = new HashMap<String,Integer>(list1.size()+list2.size());
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if(list2.size()>list1.size()){
            maxList = list2;
            minList = list1;
        }

        for (String string : maxList){
            map.put(string, 1);
        }

        for (String string : minList){
            Integer cc = map.get(string);
            if(cc!=null){
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }

        for(Map.Entry<String, Integer> entry:map.entrySet()){
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }
}
