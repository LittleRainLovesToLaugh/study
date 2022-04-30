package com.xioayu.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月01日 19:46:00
 */
public class Test {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("wwww");
        stringList.add("wwww222");
        stringList.add("111");
        stringList.add("222");
        System.out.println(stringList);
        test(stringList);
        System.out.println(stringList);
    }

    public static void test(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.remove(0);
        }
    }


}
