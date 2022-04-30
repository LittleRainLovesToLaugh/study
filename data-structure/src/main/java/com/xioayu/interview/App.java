package com.xioayu.interview;

/**
 * Description
 *
 * @author XD
 * createTime 2022年04月19日 17:40:00
 */
public class App {

    public static void main(String[] args) {
        PerSon perSon = new Manager();
        whoAreYou(perSon);
    }

    public static void whoAreYou(PerSon perSon) {
        perSon.say();
    }

}
