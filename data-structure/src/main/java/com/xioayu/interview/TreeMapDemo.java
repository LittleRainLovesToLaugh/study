package com.xioayu.interview;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description  TreeMap 面试题
 *
 * @author XD
 * createTime 2022年04月19日 17:30:00
 */
public class TreeMapDemo {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 4, 1, 3, 2, 3);
        System.out.println(list);
        // 注意，TreeSet会进行排序，按数字从大到小排序
        Set<Integer> set = new TreeSet<>(list);
        System.out.println(set);
        // TreeMap按照key进行排序
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("ggg", "1");
        treeMap.put("aaa", "3");
        treeMap.put("fff", "2");
        treeMap.put("aaa", "2");
        for (String key : treeMap.keySet()) {
            System.out.println(treeMap.get(key));
        }
    }


}
