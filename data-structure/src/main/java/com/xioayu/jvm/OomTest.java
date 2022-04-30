package com.xioayu.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月14日 21:49:00
 */
public class OomTest {
    public static void main(String[] args) {
        List<HeapOom> list = new ArrayList<>();
        while (true) {
            list.add(new HeapOom());
        }
    }

    static class HeapOom{

    }

}
