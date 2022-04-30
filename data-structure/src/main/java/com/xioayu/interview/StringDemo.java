package com.xioayu.interview;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月19日 17:20:00
 */
public class StringDemo {

    public static void main(String[] args) {
        String x = "string";
        String y = "string";
        String z = new String("string");
        System.out.println(x==y);
        System.out.println(x==z);
        System.out.println(x.equals(y));
        System.out.println(x.equals(z));


        Integer[] integers = new Integer[2];
        int[] arr = new int[10];
        System.out.println(integers[0]);
        System.out.println(arr[0]);
        System.out.println(Byte.MIN_VALUE);
        System.out.println(Byte.MAX_VALUE);
    }
}
