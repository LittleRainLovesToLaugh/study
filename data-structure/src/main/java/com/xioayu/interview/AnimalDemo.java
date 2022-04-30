package com.xioayu.interview;

import java.util.HashSet;
import java.util.Set;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月19日 17:11:00
 */
public class AnimalDemo {


    public static void main(String[] args) {
        Set<Animal> set = new HashSet<>();
        set.add(new Animal("dog", "yellow"));
        set.add(new Animal("cat", "black"));
        set.add(new Animal("bird", "white"));
        set.add(new Animal("cat", "black"));
        System.out.println(set.size());
        Set<String> stringSet = new HashSet<>();
        stringSet.add(new String("1"));
        stringSet.add(new String("2"));
        stringSet.add(new String("3"));
        stringSet.add(new String("1"));
        System.out.println(stringSet.size());
    }

    static class Animal {
        private String style;
        private String color;

        public Animal(String style, String color) {
            this.style = style;
            this.color = color;
        }
    }


}
