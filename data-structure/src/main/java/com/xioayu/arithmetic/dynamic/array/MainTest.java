package com.xioayu.arithmetic.dynamic.array;

import com.xioayu.arithmetic.dynamic.array.single.SingleLinkedList2;

/**
 * Description main方法测试类
 *
 * @author 小雨
 * createTime 2022年09月18日 22:45:00
 */
public class MainTest {

    public static void main(String[] args) {
        List<String> linkedListDemo = new SingleLinkedList2<>();
        linkedListDemo.add("20");
        linkedListDemo.add(0, "10");
        linkedListDemo.add("30");
        linkedListDemo.add(linkedListDemo.size(), "40");
        linkedListDemo.remove(1);
        System.out.println(linkedListDemo);
    }

}
