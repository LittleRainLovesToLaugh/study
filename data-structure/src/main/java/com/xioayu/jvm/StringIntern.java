package com.xioayu.jvm;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月15日 16:59:00
 */
public class StringIntern {
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);


        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

    }
}
