package com.xioayu.jvm;

import java.util.HashSet;
import java.util.Set;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月15日 16:05:00
 */
public class RuntimeConstantPoo100M {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        // 使用Set保持着常量池引用，避免Full GC回收常量池行为Set<String> set = new HashSet<String>() ;
        // 在short范围内足以让6MB的PermSize产生00M了
        // PermGen space
        short i = 0;
        System.out.println(new String("0").intern());
        while (true) {
            set.add(String.valueOf(i++).intern());
            if (i == 3) {
                break;
            }
        }
        System.out.println(set);
    }
}